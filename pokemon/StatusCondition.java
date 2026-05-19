package pokemon;

/**
 * Enumerates the major status conditions used in battle
 */

public enum StatusCondition {
    NONE("OK"),
    PARALYZED("Paralyzed"),
    BURNED("Burned"),
    POISONED("Poisoned"),
    ASLEEP("Asleep");

    private final String displayName;

    // Store the user-facing label for the status
    StatusCondition(String displayName) {
        this.displayName = displayName;
    }

    // Return the readable label for this status
    public String getDisplayName() {
        return displayName;
    }
}