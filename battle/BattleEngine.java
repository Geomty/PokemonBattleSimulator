package battle;

import java.util.Scanner;

import moves.Move;
import pokemon.OwnedPokemon;
import pokemon.Stat;
import pokemon.StatusCondition;
import pokemon.Type;

/**
 * BattleEngine class that handles the logic for the overall game loop
 */

public class BattleEngine {
	// The human player's Trainer
	private Trainer player;
	// The computer's Trainer
	private Trainer computer;
	// The scanner for reading player input
	private static Scanner scanner = new Scanner(System.in);
	
	/**
	 * Initializes a BattleEngine object
	 * @param player: The human player's Trainer
	 * @param computer: The computer's Trainer
	 */
	public BattleEngine(Trainer player, Trainer computer) {
		this.setPlayer(player);
		this.setComputer(computer);
	}
	
	/**
	 * Getter function that returns the human player's Trainer
	 * @return the name of the trainer
	 */
	private Trainer getPlayer() {
		return this.player;
	}
	
	/**
	 * Getter function that returns the computer's Trainer
	 * @return the computer's Trainer
	 */
	private Trainer getComputer() {
		return this.computer;
	}
	
	/**
	 * Getter function that returns the scanner
	 * @return the scanner
	 */
	public static Scanner getScanner() {
		return scanner;
	}
	
	/**
	 * Setter function that sets the human player's Trainer
	 * @param player: The new human player's Trainer
	 */
	private void setPlayer(Trainer player) {
		this.player = player;
	}
	
	/**
	 * Setter function that sets the computer's Trainer
	 * @param computer: The new computer's Trainer
	 */
	private void setComputer(Trainer computer) {
		this.computer = computer;
	}
	
	/**
	 * Calculate the damage that a specific move inflicts
	 * @param attacker: The attacking Pokemon
	 * @param move: The move being performed
	 * @param defender: The defending Pokemon
	 * @return the damage inflicted
	 */
	private int calculateDamage(OwnedPokemon attacker, Move move, OwnedPokemon defender) {
		int atk = move.getCategory() == "Physical" ? attacker.getEffectiveAtk() : attacker.getCurrentSpA();
		int def = move.getCategory() == "Physical" ? defender.getEffectiveDef() : defender.getCurrentSpD();
		
		return (((2 * attacker.getLevel() / 5 + 2) * move.getPower() * atk / def) / 50 + 2) * (int) Type.getEffectiveness(attacker.getBase().getType1(), defender.getBase().getType1());
	}
	
	/**
	 * Execute an move
	 * @param attacker: The attacking Pokemon
	 * @param move: The move being performed
	 * @param defender: The defending Pokemon
	 */
	private void executeAttack(OwnedPokemon attacker, Move move, OwnedPokemon defender) {
		this.announce(attacker.getName() + " uses " + move.getName() + "!");
		// Cannot attack if asleep
		if (attacker.getStatus() != StatusCondition.ASLEEP) {
			// If the attacker is paralyzed, there is a 25% to fail
			if (attacker.getStatus() != StatusCondition.PARALYZED || Math.random() < 0.75) {
				// Check for the move accuracy
				if (Math.random() < move.getAccuracy() / 100.0) {
					// Check if the move affects the user
					if (move.getStatAffectsUser()) {
						// Apply status move effects
						this.applyMoveEffect(move, attacker);
					} else {
						this.applyMoveEffect(move, defender);
					}
					
					// Apply the damage, if applicable
					if (move.getPower() > 0) {
						int damage = this.calculateDamage(attacker, move, defender);
						defender.setCurrentHp(defender.getCurrentHp() - damage);
						this.announce(attacker.getName() + " inflicts " + damage + " damage! " + defender.getName() + " is now at " + defender.getCurrentHp() + " HP.");
					}
				} else this.announce(attacker.getName() + " misses!");
			} else this.announce(attacker.getName() + " is paralyzed! Move fails.");
		} else this.announce(attacker.getName() + " is asleep! Move skipped.");
	}
	
	/**
	 * Apply status move effects
	 * @param move: The move being performed
	 * @param target: The Pokemon being affected
	 */
	private void applyMoveEffect(Move move, OwnedPokemon target) {
		// Apply the status effect, if applicable
		if (move.getStatusEffect() != StatusCondition.NONE) {
			// Check for the status chance
			if (Math.random() < move.getStatusChance()) {
				// Check if the current status is NONE
				if (target.getStatus() == StatusCondition.NONE) {
					target.applyStatus(move.getStatusEffect());
					this.announce(target.getName() + " is now " + move.getStatusEffect().getDisplayName().toLowerCase() + "!");
				} else {
					this.announce(target.getName() + " is already " + target.getStatus().getDisplayName().toLowerCase() + "!");
				}
			} else {
				this.announce("The move fails!");
			}
		}
		
		// Apply the stat stages, if applicable
		if (move.getStatAffected() != Stat.NONE) {
			target.applyStatChange(move.getStatAffected(), move.getStatStages());
			this.announce(target.getName() + "'s " + move.getStatAffected().getDisplayName() + " " + (move.getStatStages() > 0 ? "increases" : "decreases") + "!");
		}
	}
	
	/**
	 * Execute a turn
	 * @param playerMove: The player's move
	 * @param computerMove: The computer's move
	 * @return the winner of the game, or null if the game is still ongoing
	 */
	private Trainer executeTurn(Move playerMove, Move computerMove) {
		Trainer firstTrainer = this.getPlayer();
		Trainer secondTrainer = this.getComputer();
		OwnedPokemon firstPokemon = firstTrainer.getTeam()[firstTrainer.getActivePokemonIndex()];
		OwnedPokemon secondPokemon = secondTrainer.getTeam()[secondTrainer.getActivePokemonIndex()];
		Move firstMove = playerMove;
		Move secondMove = computerMove;
		
		// Determine which Pokemon goes first
		if (firstPokemon.getEffectiveSpe() < secondPokemon.getEffectiveSpe()
				|| (firstPokemon.getEffectiveSpe() == secondPokemon.getEffectiveSpe() && Math.random() < 0.5)) {
			OwnedPokemon temp = firstPokemon;
			firstPokemon = secondPokemon;
			secondPokemon = temp;
			
			Trainer temp2 = firstTrainer;
			firstTrainer = secondTrainer;
			secondTrainer = temp2;
			
			Move temp3 = firstMove;
			firstMove = secondMove;
			secondMove = temp3;
		}
		
		this.announce(firstPokemon.getName() + " goes first!");
		
		// Handle the first attack
		this.executeAttack(firstPokemon, firstMove, secondPokemon);
		
		// Check if the defending Pokemon has fainted
		if (secondPokemon.isFainted()) {
			this.announce(secondPokemon.getName() + " has fainted!");
			// If the trainer has no available Pokemon left, the game ends
			if (secondTrainer.getActivePokemonIndex() == secondTrainer.switchToNext()) {
				return firstTrainer;
			} else {
				this.announce(secondTrainer.getName() + " switches to " + secondTrainer.getTeam()[secondTrainer.getActivePokemonIndex()].getName() + "!");
				return null;
			}
		}
		
		this.announce(secondPokemon.getName() + " goes second!");
		
		// Handle the second attack
		this.executeAttack(secondPokemon, secondMove, firstPokemon);
		
		// Check if the defending Pokemon has fainted
		if (firstPokemon.isFainted()) {
			this.announce(firstPokemon.getName() + " has fainted!");
			if (firstTrainer.getActivePokemonIndex() == firstTrainer.switchToNext()) {
				return secondTrainer;
			} else {
				this.announce(firstTrainer.getName() + " switches to " + firstTrainer.getTeam()[firstTrainer.getActivePokemonIndex()].getName() + "!");
				return null;
			}
		}
		
		// Apply end of turn effects
		String firstOutput = firstPokemon.applyEndOfTurnEffects();
		if (firstOutput.length() > 0) this.announce(firstOutput);
		String secondOutput = secondPokemon.applyEndOfTurnEffects();
		if (secondOutput.length() > 0) this.announce(secondOutput);
		
		// Check if the defending Pokemon has fainted
		if (secondPokemon.isFainted()) {
			this.announce(secondPokemon.getName() + " has fainted!");
			// If the trainer has no available Pokemon left, the game ends
			if (secondTrainer.getActivePokemonIndex() == secondTrainer.switchToNext()) {
				return firstTrainer;
			} else {
				this.announce(secondTrainer.getName() + " switches to " + secondTrainer.getTeam()[secondTrainer.getActivePokemonIndex()].getName() + "!");
				return null;
			}
		}
		
		// Check if the defending Pokemon has fainted
		if (firstPokemon.isFainted()) {
			this.announce(firstPokemon.getName() + " has fainted!");
			if (firstTrainer.getActivePokemonIndex() == firstTrainer.switchToNext()) {
				return secondTrainer;
			} else {
				this.announce(firstTrainer.getName() + " switches to " + firstTrainer.getTeam()[firstTrainer.getActivePokemonIndex()].getName() + "!");
				return null;
			}
		}
		
		return null;
	}
	
	/**
	 * Start the battle
	 */
	public void startBattle() {
		// Keep track of the number of rounds so far
		int rounds = 0;
		Trainer winner = null;
		while (winner == null) {
			rounds++;
			this.announce("---------- ROUND " + rounds + " ----------");
			// Keep asking the user for input as long as the input is invalid
			int move = 0;
			System.out.print("Please enter your move choice for " + this.getPlayer().getTeam()[this.getPlayer().getActivePokemonIndex()].getName() + " from 1 to 4: ");
			while (true) {
				String command = getScanner().nextLine();
				if (command.length() == 1 && BattleUtils.isNumber(command)) {
					int num = Integer.parseInt(command);
					if (num >= 1 && num <= 4) {
						move = num;
						break;
					} else {
						System.out.print("Invalid move choice. Please try again: ");
					}
				} else {
					System.out.print("Invalid move choice. Please try again: ");
				}
			}
			winner = executeTurn(
				this.getPlayer().getTeam()[this.getPlayer().getActivePokemonIndex()].getMoves()[move - 1],
				this.getComputer().getTeam()[this.getComputer().getActivePokemonIndex()].getMoves()[(int)(Math.random() * 3)]
			);
		}
		this.announce("---------- " + winner.getName() + " WINS THE BATTLE! ----------");
	}
	
	/**
	 * Announces some information about the game, while also providing the opportunity to print detailed information
	 * @param text: The text to announce
	 */
	public void announce(String text) {
		// Announce the text
		System.out.print("* " + text + " * ");
		
		// Get input from the user
		String commandString = getScanner().nextLine();
		// Check if the user actually inputted something
		while (commandString.length() > 0) {
			// Spaces separate commands
			String[] commands = commandString.split(" ");
			for (String command : commands) {
				System.out.println();
				if (BattleUtils.isNumber(command)) {
					int num = Integer.parseInt(command);
					int first = num / 10;
					int second = num % 10;
					if (first >= 1 && first <= 6) {
						// Determine the Pokemon that the user selected
						OwnedPokemon selectedPokemon;
						if (first >= 1 && first <= 3) {
							selectedPokemon = this.getPlayer().getTeam()[first - 1];
						} else {
							selectedPokemon = this.getComputer().getTeam()[first - 4];
						}
						
						// Print the requested information about the Pokemon
						if (second >= 1 && second <= 4) {
							selectedPokemon.getMoves()[second - 1].printInfo();
						} else if (second == 0) {
							selectedPokemon.printInfo();
						} else {
							System.out.println("Invalid command " + command + ".");
						}
					} else if (first == 7 && second == 0) {
						this.getPlayer().printTeam();
					} else if (first == 8 && second == 0) {
						this.getComputer().printTeam();
					} else if (first == 9 && second == 0) {
						this.printCommandMenu();
					} else {
						System.out.println("Invalid command " + command + ".");
					}
				} else {
					System.out.println("Invalid command " + command + ".");
				}
			}
			
			// Get more input from the user
			commandString = getScanner().nextLine();
		}
	}
	
	/**
	 * Prints the command menu for viewing Pokemon information
	 */
	public void printCommandMenu() {
		// Print a separator which centers a title in the middle of it
		String infoTitle = "Command Menu";
		BattleUtils.printRepeatedChar('-', (BattleUtils.ROW_WIDTH - infoTitle.length() - 2) / 2, false);
		System.out.print(" "  + infoTitle + " ");
		BattleUtils.printRepeatedChar('-', (BattleUtils.ROW_WIDTH - infoTitle.length() - 2) / 2, true);
		
		System.out.println("Commands can be inputted after every game announcement (surrounded by *), as well as after any information printed by a command.");
		System.out.println("Commands are space separated and each command contains two digits.");
		System.out.println("The first digit selects the Pokemon, with 1, 2, 3 corresponding to the player's Pokemon and 4, 5, 6 corresponding to the computer's Pokemon.");
		System.out.println("The second digit selects which of the Pokemon's moves to print information for, from 1 to 4. 0 prints the Pokemon's general information.");
		System.out.println("(Example: 43 prints the information for the third move belonging to the computer's first Pokemon)");
		System.out.println("Additional commands: 70 to print player info, 80 to print computer info, 90 to print this menu.");
		System.out.println("Pressing enter after a game announcement or a command will continue to the next game announcement.");
		
		// Print another separator
		BattleUtils.printRepeatedChar('-', BattleUtils.ROW_WIDTH, true);
	}
}
