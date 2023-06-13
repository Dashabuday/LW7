package org.example.repositories;

import org.example.models.Coordinates;

/**
 * Coordinate's repository with Hibernate
 */
public class CoordinatesRepository extends HibernateRepository<Coordinates> {
    public CoordinatesRepository() {
        tableName = "coordinates";
        aClass = Coordinates.class;
        getSessionFactory();
    }
}
