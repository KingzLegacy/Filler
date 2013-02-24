package Filler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.SwingUtilities;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import Filler.Nodes.Antiban;
import Filler.Nodes.OpenBank;
import Filler.Nodes.UseBank;
import Filler.Nodes.UsePump;
import Filler.Nodes.UseWidget;

@Manifest(name = "LegacyFiller",
version = 0.05,
description = "Fills vials in Falador",
authors = { "KingzLegacy" })
public class LegacyFiller extends ActiveScript implements PaintListener
{
	public static int EID;
	public static int FID;
	public static int CWID;
	
	public static int Price = 0;
	public static int Filled = 0;
	public static String Status = "";
	public static boolean GUIDone = false;
	public static boolean isFilling = false;
	public static boolean NoMore = false;
	
	private Node[] NodeList = new Node[] { new OpenBank(), new UseBank(), new UsePump(), new UseWidget() };
	
	public void onStart()
	{
		Status = "Starting";
		StartGUI();
		while (!GUIDone)
			sleep(100);
		
		Price = GetPrice(FID);
		Mouse.setSpeed(Mouse.Speed.FAST);
		getContainer().submit(new Antiban());
	}
	
	@Override
	public int loop() 
	{
		if (!LoggedIn())
		{
			if (Widgets.get(596, 7).validate() && Widgets.get(596, 7).visible())
			{
				Timer t = new Timer(360000);
				while (t.isRunning() && !Game.isLoggedIn())
					sleep(500);
			}
			if (Widgets.get(906, 199).validate())
			{
				Widgets.get(906, 199).click(true);
				
				Timer t = new Timer(1500);
				while (t.isRunning() && Widgets.get(906, 199).validate() && !Widgets.get(906, 199).getText().contains("Entering"))
					sleep(100);
				if (Widgets.get(906, 199).validate() && Widgets.get(906, 199).getText().contains("Entering"))
					sleep(5000);
			}
		}
		if (LoggedIn() && Players.getLocal() != null && GUIDone)
		{
			if (NoMore)
				StopScript();
			if (AbilityOpen())
				Widgets.get(640, 30).click(true);
			for (Node n : NodeList)
				if (!n.isAlive() && n.activate())
					n.execute();
		}
		return 10;
	}
	
	public boolean LoggedIn()
	{
		return (Game.getClientState() != Game.INDEX_LOBBY_SCREEN && Game.getClientState() != Game.INDEX_LOGIN_SCREEN);
	}
	
	public void StopScript()
	{
		log.info("Script ran for " + RunTime.getElapsed());
		log.info("You filled " + Filled + " items.");
		log.info("You made a profit of " + (Filled * Price) + "gp.");
		stop();
	}
	/***************************************/
	private Timer RunTime = new Timer(0);

    public void onRepaint(Graphics g1)
    {
    	final Color color1 = new Color(0, 0, 0, 229);
        final Color color2 = new Color(0, 0, 0);
        final Color color3 = new Color(255, 0, 0, 216);

        final BasicStroke stroke1 = new BasicStroke(1);

        final Font font1 = new Font("Papyrus", 1, 13);
        final Font font2 = new Font("Verdana", 0, 11);
        
    	//Everything y + 50
        Graphics2D g = (Graphics2D)g1;
        g.setColor(color1);
        g.fillRect(1, 389, 147, 134);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRect(1, 389, 147, 134);
        g.setFont(font1);
        g.setColor(color3);
        g.drawString("LegacyFiller", 39, 405);
        g.setFont(font2);
        g.drawString("Status: " + Status, 8, 420);
        g.drawString("Run Time: " + RunTime.toElapsedString(), 8, 435);
        g.drawString("Total Fills: " + Filled, 8, 450);
        g.drawString("Fills P/H: " + (int) ((Filled * 3600000D) / RunTime.getElapsed()), 8, 465);
        g.drawString("Profit: " + (Filled * Price), 8, 480);
        g.drawString("Profit P/H: " + (int) (((Filled * Price) * 3600000D) / RunTime.getElapsed()), 8, 495);
        g.setFont(font1);
        g.drawString("KingzLegacy", 8, 519);

        PaintMouse(g);
    }

    public void PaintMouse(Graphics g)
    {
    	int x = Mouse.getX();
    	int y = Mouse.getY();
    	g.setColor(Color.ORANGE);

    	g.drawLine(x-5, y-1, x+5, y-1);
    	g.drawLine(x-6, y, x+6, y);
    	g.drawLine(x-5, y+1, x+5, y+1);

    	g.drawLine(x-1, y-5, x-1, y+5);
    	g.drawLine(x, y-6, x, y+6);
    	g.drawLine(x+1, y-5, x+1, y+5);
    }
    /***************************************/
    public boolean AbilityOpen()
    {
    	if (Widgets.get(640).validate())
    		if (Widgets.get(640, 4).visible())
    			return true;
    	return false;
    }
    
    public static void StartGUI() 
    {
		try 
		{
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					final gui Gui = new gui();
					Gui.setVisible(true);
				}

			});
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		while(!LegacyFiller.GUIDone)
			Task.sleep(100);
	}
    
    private final int GetPrice(final int itemID) {
		try {
			URL url = new URL(
					"http://services.runescape.com/m=itemdb_rs/g=runescape/viewitem.ws?obj="
							+ itemID);
			BufferedReader x = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String inputLine;
			while ((inputLine = x.readLine()) != null) {
				if (inputLine
						.contains("<th scope=\"row\">Current guide price:</th>")) {
					return (int) parsePrice(x.readLine());
				}
			}
			x.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private double parsePrice(String str) {
		if (str != null && !str.isEmpty()) {
			str = stripFormatting(str);
			str = str.substring(str.indexOf(58) + 1, str.length());
			str = str.replace(",", "");
			str = str.trim();
			if (!str.endsWith("%")) {
				if (!str.endsWith("k") && !str.endsWith("m")
						&& !str.endsWith("b")) {
					return Double.parseDouble(str);
				}
				return Double.parseDouble(str.substring(0, str.length() - 1))
						* (str.endsWith("b") ? 1000000000
								: str.endsWith("m") ? 1000000 : 1000);
			}
			final int k = str.startsWith("+") ? 1 : -1;
			str = str.substring(0);
			return Double.parseDouble(str.substring(0, str.length() - 1)) * k;
		}
		return -1D;
	}

	private String stripFormatting(final String str) {
		if (str != null && !str.isEmpty()) {
			return str.replaceAll("(^[^<]+>|<[^>]+>|<[^>]+$)", "");
		}
		return "";
	}
}
