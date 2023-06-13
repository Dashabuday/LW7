package org.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

/**
 * Class for validating and storing coordinates
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "coordinates")
public class Coordinates implements ModelHasId {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private Integer x;
    private Integer y;

    public Coordinates(Integer x, Integer y) {
        checkInputData(x, y);
        this.x = x;
        this.y = y;
    }

    public void setX(Integer x) {
        if (x.compareTo(-576) <= 0) {
            throw new IllegalArgumentException("Expected x > -576, but x = " + x);
        }

        this.x = x;
    }

    public void setY(Integer y) {
        if (y.compareTo(-286) <= 0) {
            throw new IllegalArgumentException("Expected y > -286, but y = " + y);
        }

        this.y = y;
    }

    /**
     * Method to check input data
     * @param x coordinate x
     * @param y coordinate y
     */
    private void checkInputData(Integer x, Integer y) {
        if (x.compareTo(-576) <= 0) {
            throw new IllegalArgumentException("Expected x > -576, but x = " + x);
        }

        if (y.compareTo(-286) <= 0) {
            throw new IllegalArgumentException("Expected y > -286, but y = " + y);
        }
    }
}