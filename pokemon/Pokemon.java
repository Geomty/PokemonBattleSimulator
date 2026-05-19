package pokemon;

/**
 * Pokemon class containing base stats
 */

public class Pokemon {
	// The name of the Pokemon
	private String name;
	// The pokedex ID of the Pokemon
	private int pokedex;
	// The primary elemental type of the Pokemon
	private Type type1;
	// The secondary type of the Pokemon
	private Type type2;
	// Determines how much damage a Pokemon can receive before fainting
	private int hp;
	// Partly determines how much damage a Pokemon deals when using a physical move
	private int atk;
	// Partly determines how much damage a Pokemon receives when hit with a physical move
	private int def;
	// Partly determines how much damage a Pokemon deals when using a special move
	private int spA;
	// Partly determines how much damage a Pokemon receives when hit with a special move
	private int spD;
	// Determines the order of Pokemon that can act in battle (speed)
	private int spe;
	
	/**
	 * Initializes a Pokemon object of a Pokemon with a secondary type
	 * @param name: The name of the pokemon
	 * @param pokedex: The pokedex ID of the Pokemon
	 * @param type1: The primary elemental type of the Pokemon
	 * @param type2: The secondary type of the Pokemon
	 * @param hp: Determines how much damage a Pokemon can receive before fainting
	 * @param atk: Partly determines how much damage a Pokemon deals when using a physical move
	 * @param def: Partly determines how much damage a Pokemon receives when hit with a physical move
	 * @param spA: Partly determines how much damage a Pokemon deals when using a special move
	 * @param spD: Partly determines how much damage a Pokemon receives when hit with a special move
	 * @param spe: Determines the order of Pokemon that can act in battle (speed)
	 */
	public Pokemon(String name, int pokedex, Type type1, Type type2, int hp, int atk, int def, int spA, int spD, int spe) {
		// Set each attribute of the Pokemon object using the setter functions
		this.setName(name);
		this.setPokedex(pokedex);
		this.setType1(type1);
		this.setType2(type2);
		this.setHp(hp);
		this.setAtk(atk);
		this.setDef(def);
		this.setSpA(spA);
		this.setSpD(spD);
		this.setSpe(spe);
	}
	
	/**
	 * Initializes a Pokemon object of a Pokemon without a secondary type
	 * @param name: The name of the pokemon
	 * @param pokedex: The pokedex ID of the Pokemon
	 * @param type1: The primary elemental type of the Pokemon
	 * @param hp: Determines how much damage a Pokemon can receive before fainting
	 * @param atk: Partly determines how much damage a Pokemon deals when using a physical move
	 * @param def: Partly determines how much damage a Pokemon receives when hit with a physical move
	 * @param spA: Partly determines how much damage a Pokemon deals when using a special move
	 * @param spD: Partly determines how much damage a Pokemon receives when hit with a special move
	 * @param spe: Determines the order of Pokemon that can act in battle (speed)
	 */
	public Pokemon(String name, int pokedex, Type type1, int hp, int atk, int def, int spA, int spD, int spe) {
		// Set each attribute of the Pokemon object using the setter functions
		this.setName(name);
		this.setPokedex(pokedex);
		this.setType1(type1);
		// Secondary type is automatically set to "None"
		this.setType2(Type.NONE);
		this.setHp(hp);
		this.setAtk(atk);
		this.setDef(def);
		this.setSpA(spA);
		this.setSpD(spD);
		this.setSpe(spe);
	}
	
	/**
	 * Getter function that returns the name of the Pokemon
	 * @return the name of the Pokemon
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter function that returns the pokedex ID of the Pokemon
	 * @return the pokedex ID of the Pokemon
	 */
	public int getPokedex() {
		return this.pokedex;
	}
	
	/**
	 * Getter function that returns the primary elemental type of the Pokemon
	 * @return the primary elemental type of the Pokemon
	 */
	public Type getType1() {
		return this.type1;
	}
	
	/**
	 * Getter function that returns the secondary type of the Pokemon
	 * @return the secondary type of the Pokemon
	 */
	public Type getType2() {
		return this.type2;
	}
	
	/**
	 * Getter function that returns the HP of the Pokemon
	 * @return the secondary type of the Pokemon
	 */
	public int getHp() {
		return this.hp;
	}
	
	/**
	 * Getter function that returns the attack value of the Pokemon
	 * @return the attack value of the Pokemon
	 */
	public int getAtk() {
		return this.atk;
	}
	
	/**
	 * Getter function that returns the defense value of the Pokemon
	 * @return the defense value of the Pokemon
	 */
	public int getDef() {
		return this.def;
	}
	
	/**
	 * Getter function that returns the special attack value of the Pokemon
	 * @return the special attack value of the Pokemon
	 */
	public int getSpA() {
		return this.spA;
	}
	
	/**
	 * Getter function that returns the special defense value of the Pokemon
	 * @return the special defense value of the Pokemon
	 */
	public int getSpD() {
		return this.spD;
	}
	
	/**
	 * Getter function that returns the speed of the Pokemon
	 * @return the speed of the Pokemon
	 */
	public int getSpe() {
		return this.spe;
	}
	
	/**
	 * Setter function that sets the name of the Pokemon
	 * @param name: The new name of the Pokemon
	 */
	private void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Setter function that sets the pokedex ID of the Pokemon
	 * @param pokedex: The new pokedex ID of the Pokemon
	 */
	private void setPokedex(int pokedex) {
		this.pokedex = pokedex;
	}
	
	/**
	 * Setter function that sets the primary elemental type of the Pokemon
	 * @param type1: The new primary elemental type of the Pokemon
	 */
	private void setType1(Type type1) {
		this.type1 = type1;
	}
	
	/**
	 * Setter function that sets the secondary type of the Pokemon
	 * @param type2: The new secondary type of the Pokemon
	 */
	private void setType2(Type type2) {
		this.type2 = type2;
	}
	
	/**
	 * Setter function that sets the HP of the Pokemon
	 * @param hp: The new HP of the Pokemon
	 */
	private void setHp(int hp) {
		// Only accept HP values that are 0 or above
		if (hp < 0) {
			this.hp = 0;
			System.out.println("Error: Invalid value entered for attribute hp. Defaulted to 0.");
		} else {
			this.hp = hp;
		}
	}
	
	/**
	 * Setter function that sets the attack value of the Pokemon
	 * @param atk: The new attack value of the Pokemon
	 */
	private void setAtk(int atk) {
		// Only accept attack values that are 0 or above
		if (atk < 0) {
			this.atk = 0;
			System.out.println("Error: Invalid value entered for attribute atk. Defaulted to 0.");
		} else {
			this.atk = atk;
		}
	}
	
	/**
	 * Setter function that sets the defense value of the Pokemon
	 * @param def: The new defense value of the Pokemon
	 */
	private void setDef(int def) {
		// Only accept defense values that are 0 or above
		if (def < 0) {
			this.def = 0;
			System.out.println("Error: Invalid value entered for attribute def. Defaulted to 0.");
		} else {
			this.def = def;
		}
	}
	
	/**
	 * Setter function that sets the special attack value of the Pokemon
	 * @param spA: The new special attack value of the Pokemon
	 */
	private void setSpA(int spA) {
		// Only accept special attack values that are 0 or above
		if (spA < 0) {
			this.spA = 0;
			System.out.println("Error: Invalid value entered for attribute spA. Defaulted to 0.");
		} else {
			this.spA = spA;
		}
	}
	
	/**
	 * Setter function that sets the special defense value of the Pokemon
	 * @param spD: The new special defense value of the Pokemon
	 */
	private void setSpD(int spD) {
		// Only accept special defense values that are 0 or above
		if (spD < 0) {
			this.spD = 0;
			System.out.println("Error: Invalid value entered for attribute spD. Defaulted to 0.");
		} else {
			this.spD = spD;
		}
	}
	
	/**
	 * Setter function that sets the speed of the Pokemon
	 * @param spe: The new speed of the Pokemon
	 */
	private void setSpe(int spe) {
		// Only accept speed values that are 0 or above
		if (spe < 0) {
			this.spe = 0;
			System.out.println("Error: Invalid value entered for attribute spe. Defaulted to 0.");
		} else {
			this.spe = spe;
		}
	}
}
