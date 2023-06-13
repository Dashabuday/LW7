package org.example.enums;

/**
 * Enum class of fuel's types
 */
public enum FuelType implements Comparable<FuelType> {
    GASOLINE("GASOLINE"),
    KEROSENE("KEROSENE"),
    MANPOWER("MANPOWER"),
    PLASMA("PLASMA");

    private final String typeName;

    FuelType(String description) {
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
    public static FuelType getEnum(String code) {
        return switch (code) {
            case "1" -> GASOLINE;
            case "2" -> KEROSENE;
            case "3" -> MANPOWER;
            case "4" -> PLASMA;
            default -> null;
        };
    }
}
