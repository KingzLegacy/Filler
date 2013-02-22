package Filler.Nodes;

import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import Filler.LegacyFiller;

public class Antiban extends LoopTask
{
	@Override
	public int loop() 
	{
		LegacyFiller.Status = "Antiban Activated";
		
		int Randy = Random.nextInt(0, 5);
		switch (Randy)
		{
		case 0:
			if (Players.getLocal().getInteracting() != null)
				Camera.turnTo(Players.getLocal().getInteracting());
			if (Camera.getPitch() > 50)
				Camera.setPitch(Random.nextInt(30, 50));
			break;
		case 1:
			Camera.setAngle(Random.nextInt(0, 359));
			break;
		case 2:
			Camera.setPitch(Random.nextInt(40, 60));
			break;
		default:
			Camera.setAngle(Camera.getX() +- Random.nextInt(5, 15));
			
		}
		return Random.nextInt(30000, 180000);
	}
}
