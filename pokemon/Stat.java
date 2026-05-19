package pokemon;

/**
 * Enumerates the different stats a Pokemon can have
 */

public enum Stat {
    NONE("None"),
    ATK("Attack"),
    DEF("Defense"),
    SPE("Speed");

    private final String displayName;

    // Store the user-facing label for the status
    Stat(String displayName) {
        this.displayName = displayName;
    }

    // Return the readable label for this status
    public String getDisplayName() {
        return displayName;
    }
}