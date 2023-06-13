package org.example.enums;

/**
 * Enum class of vehicle's types
 */
public enum VehicleType {
    PLANE("PLANE"),
    BOAT("BOAT"),
    SHIP("SHIP"),
    BICYCLE("BICYCLE"),
    SPACESHIP("SPACESHIP");

    private final String typeName;

    VehicleType(String description) {
        this.typeName = description;
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * Method for creating enum by short command
     * @param code code of enum
     * @return enum
     */
    public static VehicleType getEnum(String code) {
        return switch (code) {
            case "1" -> PLANE;
            case "2" -> BOAT;
            case "3" -> SHIP;
            case "4" -> BICYCLE;
            case "5" -> SPACESHIP;
            default -> null;
        };
    }
}
