package org.example.repositories;

import org.example.models.Vehicle;

/**
 * Vehicle's repository with Hibernate
 */
public class VehicleRepository extends HibernateRepository<Vehicle> {
    public VehicleRepository() {
        tableName = "vehicle_models";
        aClass = Vehicle.class;
        getSessionFactory();
    }
}
