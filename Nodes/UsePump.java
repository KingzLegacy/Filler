package Filler.Nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.SceneObject;

import Filler.LegacyFiller;

public class UsePump extends Node
{
	@Override
	public boolean activate() 
	{
		return (Inventory.contains(LegacyFiller.EID) && !Widgets.get(1370).validate());
	}

	@Override
	public void execute() 
	{
		SceneObject Pump = SceneEntities.getNearest(11661);
		if (!Pump.isOnScreen() || Pump.getLocation().distanceTo() > 6)
		{
			LegacyFiller.Status = "Going To Pump";
			Walking.walk(Walking.getClosestOnMap(Pump.getLocation()));
			if (!Walking.isRunEnabled() && Walking.getEnergy() > 50)
				Walking.setRun(true);
			if (Random.nextInt(1, 4) > 1)
				Camera.turnTo(Pump);
			
			Timer t = new Timer(1000);
			while (t.isRunning() && Pump.getLocation().distanceTo() > 6)
			{
				if (Players.getLocal().isMoving())
				{
					t.reset();
					if (!Inventory.isItemSelected() && Inventory.contains(LegacyFiller.EID))
						Inventory.getItem(LegacyFiller.EID).getWidgetChild().click(true);
				}
			}
			if (!Pump.isOnScreen())
				return;
		}
		if (Pump.isOnScreen() && Pump.getLocation().distanceTo() <= 5)
		{
			LegacyFiller.Status = "Using Pump";
			if (!Inventory.isItemSelected())
			{
				Inventory.selectItem(LegacyFiller.EID);
				
				Timer t = new Timer(750);
				while (t.isRunning() && !Inventory.isItemSelected())
					sleep(20);
				if (!Inventory.isItemSelected())
					return;
			}
			if (Inventory.isItemSelected())
			{
				Pump.click(true);
				
				Timer t = new Timer(1000);
				while (t.isRunning() && !Widgets.get(1370).validate())
				{
					if (Players.getLocal().isMoving() && Pump.getLocation().distanceTo() >= 2)
						t.reset();
					sleep(50);
				}
				if (!Widgets.get(1370).validate())
					return;
			}
		}
	}
}
