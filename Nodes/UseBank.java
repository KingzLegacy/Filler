package Filler.Nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Timer;

import Filler.LegacyFiller;

public class UseBank extends Node
{
	@Override
	public boolean activate() 
	{
		return (!Inventory.contains(LegacyFiller.EID) && Widgets.get(762).validate());
	}

	@Override
	public void execute() 
	{
		LegacyFiller.Status = "Using Bank";
		if (!Inventory.containsAll(LegacyFiller.EID) && Inventory.getCount() > 0)
		{
			int x = Inventory.getCount(LegacyFiller.FID);
			Widgets.get(762, 34).click(true);
			
			Timer t = new Timer(1250);
			while (t.isRunning() && Inventory.contains(LegacyFiller.FID))
				sleep(50);
			if (!Inventory.contains(LegacyFiller.FID))
				LegacyFiller.Filled += x;
			else
				return;
		}
		if (Bank.getItem(LegacyFiller.EID) != null && Inventory.getCount() <= 0)
		{
			Bank.getItem(LegacyFiller.EID).getWidgetChild().interact("Withdraw-All");
			
			Timer t = new Timer(1500);
			while (t.isRunning() && !Inventory.contains(LegacyFiller.EID))
				sleep(50);
		}
		if (Bank.getItem(LegacyFiller.EID) == null)
			LegacyFiller.NoMore = true;
	}
}
