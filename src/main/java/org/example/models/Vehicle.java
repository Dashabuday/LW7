package org.example.models;

import lombok.Data;
import lombok.NonNull;
import lombok.Getter;
import lombok.ToString;
import org.example.enums.FuelType;
import org.example.enums.VehicleType;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Class for validating and storing vehicle's parameters
 */
@Data
@Entity
@Table(name = "vehicle_models")
public class Vehicle implements Comparable<Vehicle>, ModelHasId {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "coordinate_id", referencedColumnName = "id")
    private Coordinates coordinates;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "engine_power")
    private int enginePower;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @Column(name = "fuel_type")
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;

    public Vehicle() {
        this.creationDate = LocalDate.now();
    }

    public void setName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(
                    "Argument name can't be empty or contain only white spaces"
            );
        }

        this.name = name;
    }

    public void setCoordinates(@NonNull Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(@NonNull LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setEnginePower(int enginePower) {
        if (enginePower <= 0) {
            throw new IllegalArgumentException(
                    "Expected enginePower > 0, but enginePower = " + enginePower
            );
        }

        this.enginePower = enginePower;
    }

    public void setFuelType(@NonNull FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public void setCreator(@NonNull User creator) {
        this.creator = creator;
    }

    @Override
    public int compareTo(Vehicle o) {
        int fuelTypeComparing = compareByFuelTypeTo(o.fuelType);
        if (fuelTypeComparing == 0) {
            if (id == null) {
                return 1;
            } else if (o.id == null) {
                return -1;
            }

            return Integer.compare(id, o.id);
        }

        return fuelTypeComparing;
    }

    /**
     * Method to compare by fuel's types
     *
     * @param o is object for comparing
     * @return answer
     */
    public int compareByFuelTypeTo(FuelType o) {
        return fuelType.getTypeName().compareTo(o.getTypeName()) * -1;
    }
}