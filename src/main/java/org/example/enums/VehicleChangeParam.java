package org.example.enums;

/**
 * Enum class of vehicle's parameters, which can be changed
 */
public enum VehicleChangeParam {
    NAME("имя"),
    COORDINATES("координаты"),
    ENGINE_POWER("мощность двигателя"),
    VEHICLE_TYPE("тип транспортного средства"),
    FUEL_TYPE("тип топлива");

    private final String param;
    VehicleChangeParam(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}
