package moves;

import pokemon.Stat;
import pokemon.StatusCondition;
import pokemon.Type;

/**
 * Special move class which inherits the Move class
 */

public class SpecialMove extends Move {
	public SpecialMove(String name, Type type, int power, int accuracy) {
		super(name, type, power, accuracy, StatusCondition.NONE, 0.0, Stat.NONE, 0, false);
	}
	
	@Override
	public String getCategory() {
		return "Special";
	}
}
