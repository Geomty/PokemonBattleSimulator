package moves;

import pokemon.Stat;
import pokemon.StatusCondition;
import pokemon.Type;

/**
 * Physical move class which inherits the Move class
 */

public class PhysicalMove extends Move {
	public PhysicalMove(String name, Type type, int power, int accuracy) {
		super(name, type, power, accuracy, StatusCondition.NONE, 0.0, Stat.NONE, 0, false);
	}
	
	@Override
	public String getCategory() {
		return "Physical";
	}
}
