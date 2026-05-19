package battle;

import pokemon.OwnedPokemon;

/**
 * Trainer class representing a trainer
 */

public class Trainer {
	// The name of the trainer
	private String name;
	// The team of three Pokemon that the trainer uses
	private OwnedPokemon[] team;
	// The index of the currently active Pokemon
	private int activePokemonIndex;
	
	/**
	 * Initializes a Trainer object
	 * @param name: The name of the trainer
	 * @param team: The team of three Pokemon that the trainer uses
	 */
	public Trainer(String name, OwnedPokemon[] team) {
		this.setName(name);
		this.setTeam(team);
		this.setActivePokemonIndex(0);
	}
	
	/**
	 * Getter function that returns the name of the trainer
	 * @return the name of the trainer
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter function that returns the team of Pokemon
	 * @return the team of Pokemon
	 */
	public OwnedPokemon[] getTeam() {
		return this.team;
	}
	
	/**
	 * Getter function that returns the index of the currently active Pokemon
	 * @return the index of the currently active Pokemon
	 */
	public int getActivePokemonIndex() {
		return this.activePokemonIndex;
	}
	
	/**
	 * Setter function that sets the name of the trainer
	 * @param name: The new name of the trainer
	 */
	private void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Setter function that sets the team of Pokemon
	 * @param team: The new team of Pokemon
	 */
	private void setTeam(OwnedPokemon[] team) {
		// Only accept OwnedPokemon arrays that contain 3 elements
		if (team.length != 3) {
			this.team = new OwnedPokemon[3];
			System.out.println("Error: Invalid length of array for attribute team. Defaulted to an empty array of size 3.");
		} else {
			this.team = team;
		}
	}
	
	/**
	 * Setter function that sets the index of the currently active Pokemon
	 * @param activePokemonIndex: The new index of the currently active Pokemon
	 */
	private void setActivePokemonIndex(int activePokemonIndex) {
		// Only accept indexes that are in the array
		if (activePokemonIndex < 0 || activePokemonIndex >= this.getTeam().length) {
			this.activePokemonIndex = 0;
			System.out.println("Error: Invalid value entered for attribute activePokemonIndex. Defaulted to 0.");
		} else {
			this.activePokemonIndex = activePokemonIndex;
		}
	}
	
	/**
	 * Determines if all three Pokemon have fainted
	 */
	public boolean hasLost() {
		// Default to false
		boolean result = false;
		
		// Loop through the team array
		for (OwnedPokemon pokemon : this.getTeam()) {
			result = pokemon.isFainted();
		}
		
		// Return the result
		return result;
	}
	
	/**
	 * Automatically switches to the next non-fainted Pokemon
	 */
	public int switchToNext() {
		int index = this.getActivePokemonIndex();
		do {
			index++;
			// Loop back to the front of the array
			if (index == 3) index = 0;
		} while (this.getTeam()[index].isFainted() && index != this.getActivePokemonIndex());
		this.setActivePokemonIndex(index);
		return index;
	}
	
	/**
	 * Prints each Pokemon and their current HP and status
	 */
	public void printTeam() {
		// Print a separator which centers a title in the middle of it
		String infoTitle = "Team Information";
		BattleUtils.printRepeatedChar('-', (BattleUtils.ROW_WIDTH - infoTitle.length() - 2) / 2, false);
		System.out.print(" " + infoTitle + " ");
		BattleUtils.printRepeatedChar('-', (BattleUtils.ROW_WIDTH - infoTitle.length() - 2) / 2, true);
		
		BattleUtils.printInfoLine("Name: ", this.getName());
		// Loop through each Pokemon
		for (OwnedPokemon pokemon : this.getTeam()) {
			BattleUtils.printInfoLine(pokemon.getName(), pokemon.getCurrentHp() + " / " + pokemon.getMaxHp() + " HP, " + pokemon.getStatus().getDisplayName());
		}
	
		// Print another separator
		BattleUtils.printRepeatedChar('-', BattleUtils.ROW_WIDTH, true);
	}
}
