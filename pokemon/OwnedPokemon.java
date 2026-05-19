package pokemon;

import battle.BattleUtils;
import moves.Move;

/**
 * Owned Pokemon class containing level-specific stats
 */

public class OwnedPokemon implements Printable {
	// The Pokemon object containing base stats
	private Pokemon base;
	// The Pokemon's current level
	private int level;
	// The Move array containing the Pokemon's moves
	private Move[] moves;
	// The maximum HP computed from base HP and level
	private int maxHp;
	// The current HP in battle
	private int currentHp;
	// The current attack stat computed from base and level
	private int currentAtk;
	// The current defense stat computed from base and level
	private int currentDef;
	// The current special attack stat computed from base and level
	private int currentSpA;
	// The current special defense stat computed from base and level
	private int currentSpD;
	// The current speed stat computed from base and level
	private int currentSpe;
	// The current status condition
	private StatusCondition status;
	// The number of turns remaining while asleep
	private int sleepCounter;
	// The attack stat stage modifier
	private int atkStage;
	// The defense stat stage modifier
	private int defStage;
	// The speed stat stage modifier
	private int speStage;
	
	/**
	 * Initializes an Owned Pokemon object with a specific level
	 * @param base: The Pokemon object containing base stats
	 * @param level: The Pokemon's current level
	 * @param moves: The Move array containing the Pokemon's moves
	 */
	public OwnedPokemon(Pokemon base, int level, Move[] moves) {
		this.setBase(base);
		this.setLevel(level);
		this.setMoves(moves);
		
		// Calculate initial stats
		this.recalculateStats();
		
		// Initialize stage modifiers to 0 and status to NONE
		this.resetBattleStats();
	}
	
	/**
	 * Initializes an Owned Pokemon object with a default level of 50
	 * @param base: The Pokemon object containing base stats
	 * @param moves: The Move array containing the Pokemon's moves
	 */
	public OwnedPokemon(Pokemon base, Move[] moves) {
		this(base, 50, moves);
	}
	
	/**
	 * Getter function that returns the base Pokemon object
	 * @return the base Pokemon object
	 */
	public Pokemon getBase() {
		return this.base;
	}
	
	/**
	 * Getter function that returns the name of the base Pokemon object
	 * @return the name of the base Pokemon object
	 */
	public String getName() {
		return this.base.getName();
	}
	
	/**
	 * Getter function that returns the level of the Pokemon
	 * @return the level of the Pokemon
	 */
	public int getLevel() {
		return this.level;
	}
	
	/**
	 * Getter function that returns the Move array of the Pokemon
	 * @return the Move array of the Pokemon
	 */
	public Move[] getMoves() {
		return this.moves;
	}
	
	/**
	 * Getter function that returns the maximum HP of the Pokemon
	 * @return the maximum HP of the Pokemon
	 */
	public int getMaxHp() {
		return this.maxHp;
	}
	
	/**
	 * Getter function that returns the current HP of the Pokemon
	 * @return the current HP of the Pokemon
	 */
	public int getCurrentHp() {
		return this.currentHp;
	}
	
	/**
	 * Getter function that returns the current attack stat of the Pokemon
	 * @return the current attack stat of the Pokemon
	 */
	public int getCurrentAtk() {
		return this.currentAtk;
	}
	
	/**
	 * Getter function that returns the current defense stat of the Pokemon
	 * @return the current defense stat of the Pokemon
	 */
	public int getCurrentDef() {
		return this.currentDef;
	}
	
	/**
	 * Getter function that returns the current special attack stat of the Pokemon
	 * @return the current special attack stat of the Pokemon
	 */
	public int getCurrentSpA() {
		return this.currentSpA;
	}
	
	/**
	 * Getter function that returns the current special defense stat of the Pokemon
	 * @return the current special defense stat of the Pokemon
	 */
	public int getCurrentSpD() {
		return this.currentSpD;
	}
	
	/**
	 * Getter function that returns the current speed stat of the Pokemon
	 * @return the current speed stat of the Pokemon
	 */
	public int getCurrentSpe() {
		return this.currentSpe;
	}
	
	/**
	 * Getter function that returns the current status condition
	 * @return the current status condition
	 */
	public StatusCondition getStatus() {
		return this.status;
	}
	
	/**
	 * Getter function that returns the number of turns remaining while asleep
	 * @return the number of turns remaining while asleep
	 */
	private int getSleepCounter() {
		return this.sleepCounter;
	}
	
	/**
	 * Getter function that returns the attack stat stage modifier
	 * @return the attack stat stage modifier
	 */
	private int getAtkStage() {
		return this.atkStage;
	}
	
	/**
	 * Getter function that returns the defense stat stage modifier
	 * @return the defense stat stage modifier
	 */
	private int getDefStage() {
		return this.defStage;
	}
	
	/**
	 * Getter function that returns the speed stat stage modifier
	 * @return the speed stat stage modifier
	 */
	private int getSpeStage() {
		return this.speStage;
	}
	
	/**
	 * Calculates the effective attack stat based on the stage multiplier
	 * @return the effective attack stat
	 */
	public int getEffectiveAtk() {
		// Calculate the multiplier depending on whether the stage is positive or negative
		double multiplier = 1.0;
		if (this.getAtkStage() >= 0) {
			multiplier = (2 + this.getAtkStage()) / 2.0;
		} else {
			multiplier = 2.0 / (2 - this.getAtkStage());
		}
		
		// Halve the result if the status condition is BURNED
		return (int)(this.getCurrentAtk() * multiplier * (this.getStatus() == StatusCondition.BURNED ? 0.5 : 1));
	}
	
	/**
	 * Calculates the effective defense stat based on the stage multiplier
	 * @return the effective defense stat
	 */
	public int getEffectiveDef() {
		// Calculate the multiplier depending on whether the stage is positive or negative
		double multiplier = 1.0;
		if (this.getDefStage() >= 0) {
			multiplier = (2 + this.getDefStage()) / 2.0;
		} else {
			multiplier = 2.0 / (2 - this.getDefStage());
		}
		
		return (int)(this.getCurrentDef() * multiplier);
	}
	
	/**
	 * Calculates the effective speed stat based on the stage multiplier
	 * @return the effective speed stat
	 */
	public int getEffectiveSpe() {
		// Calculate the multiplier depending on whether the stage is positive or negative
		double multiplier = 1.0;
		if (this.getSpeStage() >= 0) {
			multiplier = (2 + this.getSpeStage()) / 2.0;
		} else {
			multiplier = 2.0 / (2 - this.getSpeStage());
		}
		
		// Multiply the result by 0.25 if the status condition is PARALYZED
		return (int)(this.getCurrentSpe() * multiplier * (this.getStatus() == StatusCondition.PARALYZED ? 0.25 : 1));
	}
	
	/**
	 * Setter function that sets the base Pokemon object
	 * @param base: The new base Pokemon object
	 */
	private void setBase(Pokemon base) {
		this.base = base;
	}
	
	/**
	 * Setter function that sets the level of the Pokemon
	 * @param level: The new level of the Pokemon
	 */
	public void setLevel(int level) {
		// Only accept power values that are between 1 and 100 inclusive
		if (level < 1 || level > 100) {
			this.level = 1;
			System.out.println("Error: Invalid value entered for attribute level. Defaulted to 1.");
		} else {
			this.level = level;
		}
		
		// Recalculate stats
		this.recalculateStats();
	}
	
	/**
	 * Setter function that sets the Move array of the Pokemon
	 * @param moves: The new Move array of the Pokemon
	 */
	private void setMoves(Move[] moves) {
		// Only accept Move arrays that contain 4 elements
		if (moves.length != 4) {
			this.moves = new Move[4];
			System.out.println("Error: Invalid length of array for attribute moves. Defaulted to an empty array of size 4.");
		} else {
			this.moves = moves;
		}
	}
	
	/**
	 * Setter function that sets the maximum HP of the Pokemon
	 * @param name: The new maximum HP of the Pokemon
	 */
	private void setMaxHp(int maxHp) {
		// Only accept HP values that are greater than 0
		if (maxHp < 1) {
			this.maxHp = 1;
			System.out.println("Error: Invalid value entered for attribute maxHp. Defaulted to 1.");
		} else {
			this.maxHp = maxHp;
		}
	}
	
	/**
	 * Setter function that sets the current HP of the Pokemon
	 * @param currentHp: The new current HP of the Pokemon
	 */
	public void setCurrentHp(int currentHp) {
		// Only accept current HP values that are between 0 and maxHp inclusive
		if (currentHp > this.getMaxHp()) {
			this.currentHp = 1;
			System.out.println("Error: Invalid value entered for attribute currentHp. Defaulted to 1.");
		} else {
			this.currentHp = Math.max(0, currentHp);
		}
	}
	
	/**
	 * Setter function that sets the current attack stat of the Pokemon
	 * @param currentAtk: The new current attack stat of the Pokemon
	 */
	private void setCurrentAtk(int currentAtk) {
		this.currentAtk = currentAtk;
	}
	
	/**
	 * Setter function that sets the current defense stat of the Pokemon
	 * @param currentDef: The new current defense stat of the Pokemon
	 */
	private void setCurrentDef(int currentDef) {
		this.currentDef = currentDef;
	}
	
	/**
	 * Setter function that sets the current special attack stat of the Pokemon
	 * @param currentSpA: The new current special attack stat of the Pokemon
	 */
	private void setCurrentSpA(int currentSpA) {
		this.currentSpA = currentSpA;
	}
	
	/**
	 * Setter function that sets the current special defense stat of the Pokemon
	 * @param currentSpD: The new current special defense stat of the Pokemon
	 */
	private void setCurrentSpD(int currentSpD) {
		this.currentSpD = currentSpD;
	}
	
	/**
	 * Setter function that sets the current speed stat of the Pokemon
	 * @param currentSpe: The new current speed stat of the Pokemon
	 */
	private void setCurrentSpe(int currentSpe) {
		this.currentSpe = currentSpe;
	}
	
	/**
	 * Setter function that sets the current status condition
	 * @param status: The new current status condition
	 */
	private void setStatus(StatusCondition status) {
		this.status = status;
	}
	
	/**
	 * Setter function that sets the number of turns remaining while asleep
	 * @param sleepCounter: The new number of turns remaining while asleep
	 */
	private void setSleepCounter(int sleepCounter) {
		this.sleepCounter = sleepCounter;
	}
	
	/**
	 * Setter function that sets the attack stat stage modifier
	 * @param atkStage: The new attack stat stage modifier
	 */
	private void setAtkStage(int atkStage) {
		this.atkStage = atkStage;
	}
	
	/**
	 * Setter function that sets the defense stat stage modifier
	 * @param defStage: The new defense stat stage modifier
	 */
	private void setDefStage(int defStage) {
		this.defStage = defStage;
	}
	
	/**
	 * Setter function that sets the speed stat stage modifier
	 * @param speStage: The new speed stat stage modifier
	 */
	private void setSpeStage(int speStage) {
		this.speStage = speStage;
	}
	
	/**
	 * Applies a status only if the current status is NONE
	 * @param s: The new status condition
	 */
	public void applyStatus(StatusCondition s) {
		// Check if the current status is NONE
		if (this.getStatus() == StatusCondition.NONE) {
			this.setStatus(s);
			
			// Randomly set the sleep counter
			if (s == StatusCondition.ASLEEP) {
				this.setSleepCounter((int)(Math.random() * 3 + 1));
			}
		}
	}
	
	/**
	 * Changes the appropriate stage field
	 * @param stat: The stat to change
	 * @param stages: The number of stages to change
	 */
	public void applyStatChange(Stat stat, int stages) {
		// Check which stat is being changed
		switch (stat) {
			case ATK:
				this.setAtkStage(this.getAtkStage() + stages);
				// Keep the stages value between -6 and 6 inclusive
				if (this.getAtkStage() < -6) this.setAtkStage(-6);
				if (this.getAtkStage() > 6) this.setAtkStage(6);
				break;
			case DEF:
				this.setDefStage(this.getDefStage() + stages);
				if (this.getDefStage() < -6) this.setDefStage(-6);
				if (this.getDefStage() > 6) this.setDefStage(6);
				break;
			case SPE:
				this.setSpeStage(this.getSpeStage() + stages);
				if (this.getSpeStage() < -6) this.setSpeStage(-6);
				if (this.getSpeStage() > 6) this.setSpeStage(6);
				break;
			default:
				break;
		}
	}
	
	/**
	 * Applies various end of turn effects
	 * @return the output to print
	 */
	public String applyEndOfTurnEffects() {
		if (this.getStatus() == StatusCondition.BURNED || this.getStatus() == StatusCondition.POISONED) {
			// If BURNED or POISONED, subtract 1/16 of maxHp from currentHp (minimum 0)
			int oldHp = this.getCurrentHp();
			this.setCurrentHp(Math.max(oldHp - this.getMaxHp() / 16, 0));
			String output = this.getName() + " loses " + (oldHp - this.getCurrentHp()) + " HP from being " + this.getStatus().getDisplayName().toLowerCase() + "! "
					+ this.getName() + " is now at " + this.getCurrentHp() + " HP.";
			return output;
		} else if (this.getStatus() == StatusCondition.ASLEEP) {
			// If ASLEEP, decrement the sleep counter
			this.setSleepCounter(this.getSleepCounter() - 1);
			if (this.getSleepCounter() == 0) {
				// If the sleep counter reaches 0, set status condition to NONE
				this.setStatus(StatusCondition.NONE);
				return this.getName() + " has woken up!";
			}
		}
		return "";
	}
	
	/**
	 * Resets all stage modifiers to 0 and status condition to NONE
	 */
	public void resetBattleStats() {
		this.setStatus(StatusCondition.NONE);
		this.setSleepCounter(0);
		this.setAtkStage(0);
		this.setDefStage(0);
		this.setSpeStage(0);
	}
	
	/**
	 * Compute current HP, maximum HP, attack, defense, special attack, special defense, and speed,
	 * based on the current level
	 */
	private void recalculateStats() {
		// Current HP and maximum HP
		this.setMaxHp(this.base.getHp() * 2 * this.getLevel() / 100 + this.getLevel() + 10);
		this.setCurrentHp(this.getMaxHp());
		
		// Attack and defense
		this.setCurrentAtk(this.base.getAtk() * 2 * this.getLevel() / 100 + 5);
		this.setCurrentDef(this.base.getDef() * 2 * this.getLevel() / 100 + 5);
		
		// Special attack and special defense
		this.setCurrentSpA(this.base.getSpA() * 2 * this.getLevel() / 100 + 5);
		this.setCurrentSpD(this.base.getSpD() * 2 * this.getLevel() / 100 + 5);
		
		// Speed
		this.setCurrentSpe(this.base.getSpe() * 2 * this.getLevel() / 100 + 5);
	}
	
	/**
	 * Determine whether the Pokemon has fainted based on the current HP
	 * @return whether the Pokemon has fainted
	 */
	public boolean isFainted() {
		return this.getCurrentHp() == 0;
	}
	
	/**
	 * Prints all attributes of the Pokemon object in a formatted table
	 */
	@Override
	public void printInfo() {
		// Print a separator which centers a title in the middle of it
		String infoTitle = "Pokemon Info";
		BattleUtils.printRepeatedChar('-', (BattleUtils.ROW_WIDTH - infoTitle.length() - 2) / 2, false);
		System.out.print(" "  + infoTitle + " ");
		BattleUtils.printRepeatedChar('-', (BattleUtils.ROW_WIDTH - infoTitle.length() - 2) / 2, true);
		
		// Print each attribute using the printInfoLine utility function
		BattleUtils.printInfoLine("Name: ", this.base.getName());
		BattleUtils.printInfoLine("Level: ", this.getLevel());
		BattleUtils.printInfoLine("Primary type: ", this.base.getType1().getDisplayName());
		BattleUtils.printInfoLine("Secondary type: ", this.base.getType2().getDisplayName());
		for (int i = 0; i < this.getMoves().length; i++) {
			BattleUtils.printInfoLine("Move " + (i + 1) + ": ", this.getMoves()[i].getName());
		}
		BattleUtils.printInfoLine("HP: ", this.getCurrentHp() + " / " + this.getMaxHp());
		BattleUtils.printInfoLine("Attack: ", this.getEffectiveAtk());
		BattleUtils.printInfoLine("Defense: ", this.getEffectiveDef());
		BattleUtils.printInfoLine("Special attack: ", this.getCurrentSpA());
		BattleUtils.printInfoLine("Special defense: ", this.getCurrentSpD());
		BattleUtils.printInfoLine("Speed: ", this.getEffectiveSpe());
		BattleUtils.printInfoLine("Status: ", this.getStatus().getDisplayName());
		
		// Print another separator
		BattleUtils.printRepeatedChar('-', BattleUtils.ROW_WIDTH, true);
	}

}
