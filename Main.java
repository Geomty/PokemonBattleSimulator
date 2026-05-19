import battle.*;
import moves.*;
import pokemon.*;

/**
 * Main class that tests the Pokemon and Move classes
 */

public class Main {
	public static void main(String[] args) {
		// Initialize the available Pokemon
		OwnedPokemon[] availablePokemon = new OwnedPokemon[] {
			new OwnedPokemon(
				new Pokemon("Pikachu", 25, Type.ELECTRIC, 35, 55, 40, 50, 50, 90),
				new Move[] {
					new StatusMove("Growl", Type.NORMAL, 100, Stat.ATK, -1, false),
					new PhysicalMove("Nuzzle", Type.ELECTRIC, 20, 100),
					new SpecialMove("Thunder Shock", Type.ELECTRIC, 40, 100),
					new SpecialMove("Thunder", Type.ELECTRIC, 110, 70)
				}
			),
			new OwnedPokemon(
				new Pokemon("Charizard", 6, Type.FIRE, Type.FLYING, 78, 84, 78, 109, 85, 100),
				30,
				new Move[] {
					new SpecialMove("Air Slash", Type.FLYING, 75, 95),
					new PhysicalMove("Dragon Claw", Type.DRAGON, 80, 100),
					new StatusMove("Scary Face", Type.NORMAL, 100, Stat.SPE, -2, false),
					new StatusMove("Will-O-Wisp", Type.FIRE, 85, StatusCondition.BURNED, 0.85, false)
				}
			),
			new OwnedPokemon(
				new Pokemon("Bulbasaur", 1, Type.GRASS, Type.POISON, 45, 49, 49, 65, 65, 45),
				45,
				new Move[] {
					new PhysicalMove("Tackle", Type.NORMAL, 40, 100),
					new StatusMove("Poison Powder", Type.POISON, 75, StatusCondition.POISONED, 0.75, false),
					new SpecialMove("Solar Beam", Type.GRASS, 120, 100),
					new PhysicalMove("Seed Bomb", Type.GRASS, 80, 100)
				}
			),
			new OwnedPokemon(
				new Pokemon("Chansey", 113, Type.NORMAL, 250, 5, 5, 35, 105, 50),
				40,
				new Move[] {
					new StatusMove("Sing", Type.NORMAL, 55, StatusCondition.ASLEEP, 0.55, false),
					new PhysicalMove("Covet", Type.NORMAL, 60, 100),
					new SpecialMove("Echoed Voice", Type.NORMAL, 40, 100),
					new StatusMove("Thunder Wave", Type.ELECTRIC, 90, StatusCondition.PARALYZED, 0.90, false)
				}
			),
			new OwnedPokemon(
				new Pokemon("Meowth", 52, Type.NORMAL, 40, 45, 35, 40, 40, 90),
				new Move[] {
					new PhysicalMove("Fake Out", Type.NORMAL, 40, 100),
					new SpecialMove("Power Gem", Type.ROCK, 80, 100),
					new StatusMove("Screech", Type.NORMAL, 85, Stat.DEF, -2, false),
					new PhysicalMove("Slash", Type.NORMAL, 70, 100)
				}
			),
			new OwnedPokemon(
				new Pokemon("Voltorb", 100, Type.ELECTRIC, 40, 30, 50, 55, 55, 100),
				55,
				new Move[] {
					new StatusMove("Agility", Type.PSYCHIC, 100, Stat.SPE, 2, true),
					new PhysicalMove("Spark", Type.ELECTRIC, 65, 100),
					new SpecialMove("Charge Beam", Type.ELECTRIC, 50, 90),
					new SpecialMove("Thunderbolt", Type.ELECTRIC, 90, 100)
				}
			)
		};
		
		OwnedPokemon[] playerPokemon = new OwnedPokemon[3];
		OwnedPokemon[] computerPokemon = new OwnedPokemon[3];
		

		System.out.print("Welcome to Pokemon Battle Simulator! (press enter to continue)");
		BattleEngine.getScanner().nextLine();
		System.out.println("In this game, you and your team of three Pokemon will battle against an AI using your choice of moves. The team with the last Pokemon standing wins!");
		System.out.print("First, what is your name? ");
		String name = "";
		// Keep asking the user for input as long as the input is invalid
		while (true) {
			name = BattleEngine.getScanner().nextLine();
			if (name.length() == 0) {
				System.out.print("Name cannot be empty. Please try again: ");
			} else break;
		}
		System.out.println("Press enter to view the available Pokemon: ");
		BattleEngine.getScanner().nextLine();
		for (OwnedPokemon pokemon : availablePokemon) {
			pokemon.printInfo();
			System.out.println();
		}
		System.out.println("Now you must pick your Pokemon!");
		System.out.println("Please enter a 3 digit number where each digit corresponds to a Pokemon choice");
		System.out.print("(Example: 253 selects the second, fifth, and third Pokemon from the order that they were printed in): ");
		// Keep asking the user for input as long as the input is invalid
		while (true) {
			String command = BattleEngine.getScanner().nextLine();
			if (command.length() == 3 && BattleUtils.isNumber(command)) {
				int num = Integer.parseInt(command);
				// Parse the input into the three Pokemon choices
				int first = num / 100;
				int second = num / 10 - 10 * first;
				int third = num % 10;
				if (first >= 1 && second >= 1 && third >= 1 && first <= 6 && second <= 6 && third <= 6 && first != second && first != third && second != third) {
					// Player chooses Pokemon
					playerPokemon[0] = availablePokemon[first - 1];
					playerPokemon[1] = availablePokemon[second - 1];
					playerPokemon[2] = availablePokemon[third - 1];
					
					// Computer chooses Pokemon
					String computerSelection = "123456";
					for (int i : new int[] {first, second, third}) {
						// Exclude the Pokemon that the player chose from the possibilities
						computerSelection = computerSelection.substring(0, computerSelection.indexOf(i + "")) + computerSelection.substring(computerSelection.indexOf(i + "") + 1, computerSelection.length());
					}
					for (int i = 0; i < 3; i++) {
						// Order of computer's Pokemon is random
						int index = (int)(Math.random() * computerSelection.length());
						computerPokemon[i] = availablePokemon[Integer.parseInt(computerSelection.substring(index, index + 1)) - 1];
						computerSelection = computerSelection.substring(0, index) + computerSelection.substring(index + 1, computerSelection.length());
					}
					
					break;
				} else {
					System.out.print("Invalid input. Please try again: ");
				}
			} else {
				System.out.print("Invalid input. Please try again: ");
			}
		}
		
		// Print user's Pokemon
		System.out.print(name + "'s Pokemon: ");
		for (int i = 0; i < 3; i++) {
			if (i != 0) {
				System.out.print(", ");
			}
			System.out.print(playerPokemon[i].getName());
		}
		System.out.println();
		
		// Print computer's Pokemon
		System.out.print("AI's Pokemon: ");
		for (int i = 0; i < 3; i++) {
			if (i != 0) {
				System.out.print(", ");
			}
			System.out.print(computerPokemon[i].getName());
		}
		System.out.println();		
		
		BattleEngine battleEngine = new BattleEngine(
			new Trainer("George", playerPokemon),
			new Trainer("AI", computerPokemon)
		);

		System.out.print("Would you like to view the command menu before we begin? (Press enter for no and type anything for yes): ");
		if (BattleEngine.getScanner().nextLine().length() > 0) {
			System.out.println();
			battleEngine.printCommandMenu();
			System.out.println();
		}
		
		battleEngine.startBattle();
		
		System.out.println("Thank you for playing!");
	}
}
