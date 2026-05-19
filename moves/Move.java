package moves;

import battle.BattleUtils;
import pokemon.Printable;
import pokemon.Stat;
import pokemon.StatusCondition;
import pokemon.Type;

/**
 * Move class representing attacks used in battle
 */

public abstract class Move implements Printable {
	// The name of the move
	private String name;
	// The elementary type of the move
	private Type type;
	// Determines how much damage the move deals (0 for status move)
	private int power;
	// Determines how likely a move is to hit
	private int accuracy;
	// The status condition that this move inflicts
	private StatusCondition statusEffect;
	// The probability from 0.0 to 1.0 of inflicting the status
	private double statusChance;
	// The stat that this move changes
	private Stat statAffected;
	// The number of stages to change (negative lowers, positive raises)
	private int statStages;
	// Whether the move affects the user
	private boolean statAffectsUser;
	
	/**
	 * Initializes a Move object
	 * @param name: The name of the move
	 * @param type: The elementary type of the move
	 * @param power: Determines how much damage the move deals (0 for status move)
	 * @param accuracy: Determines how likely a move is to hit as a percentage
	 * @param statusEffect: The status condition that this move inflicts
	 * @param statusChance: The probability from 0.0 to 1.0 of inflicting the status
	 * @param statAffected: The stat that this move changes
	 * @param statStages: The number of stages to change
	 * @param statAffectsUser: Whether the move affects the user
	 */
	public Move(String name, Type type, int power, int accuracy,
			StatusCondition statusEffect, double statusChance, Stat statAffected, int statStages, boolean statAffectsUser) {
		// Set each attribute of the Move object using the setter functions
		this.setName(name);
		this.setType(type);
		this.setPower(power);
		this.setAccuracy(accuracy);
		this.setStatusEffect(statusEffect);
		this.setStatusChance(statusChance);
		this.setStatAffected(statAffected);
		this.setStatStages(statStages);
		this.setStatAffectsUser(statAffectsUser);
	}
	
	/**
	 * Getter function that returns the name of the move
	 * @return the name of the move
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter function that returns the type of the move
	 * @return the type of the move
	 */
	public Type getType() {
		return this.type;
	}
	
	/**
	 * Getter function that returns the power of the move
	 * @return the power of the move
	 */
	public int getPower() {
		return this.power;
	}
	
	/**
	 * Getter function that returns the accuracy of the move
	 * @return the accuracy of the move
	 */
	public int getAccuracy() {
		return this.accuracy;
	}
	
	/**
	 * Getter function that returns the status condition that the move inflicts
	 * @return the status condition that the move inflicts
	 */
	public StatusCondition getStatusEffect() {
		return this.statusEffect;
	}
	
	/**
	 * Getter function that returns the probability of inflicting the status
	 * @return the probability of inflicting the status
	 */
	public double getStatusChance() {
		return this.statusChance;
	}
	
	/**
	 * Getter function that returns the stat that this move changes
	 * @return the stat that this move changes
	 */
	public Stat getStatAffected() {
		return this.statAffected;
	}
	
	/**
	 * Getter function that returns the number of stages to change
	 * @return the number of the stages to change
	 */
	public int getStatStages() {
		return this.statStages;
	}
	
	/**
	 * Getter function that returns whether the move affects the user
	 * @return whether the move affects the user
	 */
	public boolean getStatAffectsUser() {
		return this.statAffectsUser;
	}
	
	/**
	 * Abstract getter function that returns the category of the move
	 * @return the category of the move
	 */
	public abstract String getCategory();
	
	/**
	 * Setter function that sets the name of the move
	 * @param name: The new name of the move
	 */
	private void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Setter function that sets the type of the move
	 * @param type: The new type of the move
	 */
	private void setType(Type type) {
		this.type = type;
	}
	
	/**
	 * Setter function that sets the power of the move
	 * @param power: The new power of the move
	 */
	private void setPower(int power) {
		// Only accept power values that are 0 or above
		if (power < 0) {
			this.power = 0;
			System.out.println("Error: Invalid value entered for attribute power. Defaulted to 0.");
		} else {
			this.power = power;
		}
	}
	
	/**
	 * Setter function that sets the accuracy of the move
	 * @param accuracy: The new accuracy of the move
	 */
	private void setAccuracy(int accuracy) {
		// Only accept power values that are between 0 and 100 inclusive
		if (accuracy < 0 || accuracy > 100) {
			this.accuracy = 0;
			System.out.println("Error: Invalid value entered for attribute accuracy. Defaulted to 0.");
		} else {
			this.accuracy = accuracy;
		}
	}
	
	/**
	 * Setter function that sets the status condition that the move inflicts
	 * @param statusEffect: The status condition that the move inflicts
	 */
	private void setStatusEffect(StatusCondition statusEffect) {
		this.statusEffect = statusEffect;
	}
	
	/**
	 * Setter function that sets the probability of inflicting the status
	 * @param statusChance: The probability of inflicting the status
	 */
	private void setStatusChance(double statusChance) {
		this.statusChance = statusChance;
	}
	
	/**
	 * Setter function that sets the stat that this move changes
	 * @param statAffected: The stat that this move changes
	 */
	private void setStatAffected(Stat statAffected) {
		this.statAffected = statAffected;
	}
	
	/**
	 * Setter function that sets the number of stages to change
	 * @param statStages: The number of stages to change
	 */
	private void setStatStages(int statStages) {
		this.statStages = statStages;
	}
	
	/**
	 * Setter function that sets whether the move affects the user
	 * @param statAffectsUser: Whether the move affects the user
	 */
	private void setStatAffectsUser(boolean statAffectsUser) {
		this.statAffectsUser = statAffectsUser;
	}
	
	/**
	 * Prints all attributes of the Move object in a formatted table
	 */
	@Override
	public void printInfo() {
		// Print a separator which centers a title in the middle of it
		String infoTitle = "Move Information";
		BattleUtils.printRepeatedChar('-', (BattleUtils.ROW_WIDTH - infoTitle.length() - 2) / 2, false);
		System.out.print(" " + infoTitle + " ");
		BattleUtils.printRepeatedChar('-', (BattleUtils.ROW_WIDTH - infoTitle.length() - 2) / 2, true);
		
		// Print each attribute using the printInfoLine utility function
		BattleUtils.printInfoLine("Name: ", this.getName());
		BattleUtils.printInfoLine("Type: ", this.getType().getDisplayName());
		BattleUtils.printInfoLine("Category: ", this.getCategory());
		BattleUtils.printInfoLine("Power: ", this.getPower());
		BattleUtils.printInfoLine("Accuracy: ", this.getAccuracy());
		BattleUtils.printInfoLine("Status effect: ", this.getStatusEffect().getDisplayName() == "OK" ? "None" : this.getStatusEffect().getDisplayName());
		BattleUtils.printInfoLine("Status chance: ", (int) this.getStatusChance() * 100);
		BattleUtils.printInfoLine("Stat affected: ", this.getStatAffected().getDisplayName());
		BattleUtils.printInfoLine("Stat stages: ", this.getStatStages());
		BattleUtils.printInfoLine("Stat affects user: ", this.getStatAffectsUser() ? "Yes" : "No");
		
		// Print another separator
		BattleUtils.printRepeatedChar('-', BattleUtils.ROW_WIDTH, true);
	}
}
