package Filler.Nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;

import Filler.LegacyFiller;

public class UseWidget extends Node
{
	private int[] EIDs = { 229, 1923, 1935, 3727, 434 };
	private int[] CWID = { 2 /*Vial*/, 6 /*Jug*/, 10 /*Bucket*/, 14 /*Bowl*/, 18 /*Waterskin*/}; //1370, 44
	
	@Override
	public boolean activate() 
	{
		return (Inventory.contains(LegacyFiller.EID) && Widgets.get(1370).validate());
	}

	@Override
	public void execute() 
	{
		if (ItemSelected())
		{
			LegacyFiller.Status = "Starting To Fill";
			Widgets.get(1370, 38).click(true);
			
			Timer t = new Timer(2000);
			while (t.isRunning() && Players.getLocal().getAnimation() != 832)
				sleep(10);
			if (Players.getLocal().getAnimation() == 832)
			{
				Timer fs = new Timer(5000);
				while (fs.isRunning() && Inventory.contains(LegacyFiller.EID))
				{
					LegacyFiller.Status = "Filling " + Inventory.getItem(LegacyFiller.EID).getName() + "s";
					if (Players.getLocal().getAnimation() == 832)
						fs.reset();
					if (Inventory.getCount(LegacyFiller.EID) < 5 && Mouse.getLocation().distance(new Tile(2945, 3370, 0).getLocation().randomize(1, 1).getMapPoint()) > 10)
					{
						Camera.turnTo(SceneEntities.getNearest(11758));
						if (Random.nextInt(1, 4) > 1)
							Mouse.move(new Tile(2945, 3370, 0).getLocation().randomize(1, 1).getMapPoint());
					}
					sleep(50);
				}
			}
			return;
		}
		else if (!ItemSelected())
		{
			LegacyFiller.Status = "Choosing Item";
			Widgets.get(1371, 44).getChild(LegacyFiller.CWID).click(true);
			sleep(500);
		}
	}
	
	private boolean ItemSelected()
	{
		if (Widgets.get(1370).validate())
			if (Widgets.get(1370, 37).getTooltip().contains("" + Inventory.getCount(LegacyFiller.EID)))
				return true;
		return false;
	}
}
