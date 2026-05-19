package battle;

/**
 * Utility class containing useful attributes and methods
 */

public abstract class BattleUtils {	
	// Info line row width
	public static final int ROW_WIDTH = 48;
	
	/**
	 * Searches for a target element in a sorted string array using binary search
	 * @param array: The array being searched
	 * @param target: The element to search for
	 * @return the index of the target element or -1 if it was not found
	 */	
	public static int findElementInArray(String[] array, String target) {
		// Initialize low, high, mid variables
		int low = 0;
		int high = array.length - 1;
		int mid = (low + high) / 2;
		
		// Keep searching while the bounds haven't closed in on each other
		while (low <= high) {
			if (array[mid].compareTo(target) == 0) {
				// Target has been found
				return mid;
			} else if (array[mid].compareTo(target) < 0) {
				// Middle string is lexicographically less than the target string
				low = mid + 1;
			} else {
				// Middle string is lexicographically greater than the target string
				high = mid - 1;
			}
			
			// Update middle index
			mid = (low + high) / 2;
		}
		
		return -1;
	}
	
	/**
	 * Prints a character a specified number of times
	 * @param character: The character to print
	 * @param times: The number of times to print the character
	 * @param newLine: Whether to print a new line at the end of the output
	 */
	public static void printRepeatedChar(char character, int times, boolean newLine) {
		// Loop through the number of times
		for (int i = 0; i < times; i++) {
			// Print the character for each iteration of the loop
			System.out.print(character);
		}
		
		// Print a new line afterwards if requested
		if (newLine) System.out.println();
	}
	
	/**
	 * Prints a formatted string of two columns, each with a fixed length
	 * @param left: The left-justified string content in the left column, which takes up 1/3 of the row width
	 * @param right: The right-justified string content in the right column, which takes up 2/3 of the row width
	 */
	public static void printInfoLine(String left, String right) {
		System.out.println(String.format("%-" + BattleUtils.ROW_WIDTH / 2 + "s%" + BattleUtils.ROW_WIDTH / 2 + "s", left, right));
	}
	
	/**
	 * Prints a formatted string of two columns, each with a fixed length
	 * @param left: The left-justified string content in the left column, which takes up 1/3 of the row width
	 * @param right: The right-justified integer content in the right column, which takes up 2/3 of the row width
	 */
	public static void printInfoLine(String left, int right) {
		System.out.println(String.format("%-" + BattleUtils.ROW_WIDTH / 2 + "s%" + BattleUtils.ROW_WIDTH / 2 + "s", left, right));
	}
	
	/**
	 * Checks if a string can be converted to an integer
	 * @param number: The string to check
	 * @return whether the string can be converted to an integer
	 */
	public static boolean isNumber(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
