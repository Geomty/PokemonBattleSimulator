package moves;

import pokemon.Stat;
import pokemon.StatusCondition;
import pokemon.Type;

/**
 * Status move class which inherits the Move class
 */

public class StatusMove extends Move {
	public StatusMove(String name, Type type, int accuracy, StatusCondition statusEffect, double statusChance, boolean affectsUser) {
		super(name, type, 0, accuracy, statusEffect, statusChance, Stat.NONE, 0, affectsUser);
	}
	
	public StatusMove(String name, Type type, int accuracy, Stat statAffected, int statStages, boolean affectsUser) {
		super(name, type, 0, accuracy, StatusCondition.NONE, 0.0, statAffected, statStages, affectsUser);
	}
	
	@Override
	public String getCategory() {
		return "Status";
	}
}
