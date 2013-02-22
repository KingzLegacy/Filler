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
import org.powerbot.game.api.wrappers.Tile;
import Filler.LegacyFiller;

public class OpenBank extends Node
{
	Tile bank = new Tile(2946, 3369, 0);
	
	@Override
	public boolean activate() 
	{
		return (!Inventory.contains(LegacyFiller.EID) && !Widgets.get(762).validate());
	}

	@Override
	public void execute() 
	{
		if (bank.distanceTo() > 7)
		{
			LegacyFiller.Status = "Going To Bank";
			Walking.walk(bank.randomize(1, 1));
			if (!Walking.isRunEnabled() && Walking.getEnergy() > 50)
				Walking.setRun(true);
			if (Random.nextInt(1, 4) > 1)
				Camera.turnTo(bank);
			
			Timer t = new Timer(1000);
			while (t.isRunning() && SceneEntities.getNearest(11758).getLocation().distanceTo() > 7)
				if (Players.getLocal().isMoving())
					t.reset();
			return;
		}
		else
		{
			LegacyFiller.Status = "Opening Bank";
			if (!SceneEntities.getNearest(11758).isOnScreen())
				Camera.turnTo(SceneEntities.getNearest(11758));
			SceneEntities.getNearest(11758).interact("Bank");
			
			Timer t = new Timer(1000);
			while (t.isRunning() && !Widgets.get(762).validate())
				if (Players.getLocal().isMoving())
					t.reset();
		}
	}
}