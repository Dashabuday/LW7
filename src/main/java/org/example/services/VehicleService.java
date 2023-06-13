package org.example.services;

import org.example.enums.FuelType;
import org.example.models.Coordinates;
import org.example.models.User;
import org.example.models.Vehicle;
import org.example.repositories.CoordinatesRepository;
import org.example.repositories.UserRepository;
import org.example.repositories.VehicleRepository;
import org.example.repositories.mybatis.implementations.CoordinatesMyBatisRepositoryImpl;
import org.example.repositories.mybatis.implementations.UserMyBatisRepositoryImpl;
import org.example.repositories.mybatis.implementations.VehicleMyBatisRepositoryImpl;
import org.example.repositories.mybatis.interfaces.CoordinatesMyBatisRepository;
import org.example.repositories.mybatis.interfaces.UserMyBatisRepository;
import org.example.repositories.mybatis.interfaces.VehicleMyBatisRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for working with vehicles
 */
public class VehicleService {
    private final ArrayDeque<Vehicle> collection;
    private final VehicleRepository vehicleRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final UserRepository userRepository;
//    private final VehicleMyBatisRepositoryImpl vehicleRepository;
//    private final CoordinatesMyBatisRepositoryImpl coordinatesRepository;
//    private final UserMyBatisRepositoryImpl userRepository;
    private final LocalDate initializedDate;

    public VehicleService() throws IOException {
        this.vehicleRepository = new VehicleRepository();
        this.coordinatesRepository = new CoordinatesRepository();
        this.userRepository = new UserRepository();
//        this.vehicleRepository = new VehicleMyBatisRepositoryImpl();
//        this.coordinatesRepository = new CoordinatesMyBatisRepositoryImpl();
//        this.userRepository = new UserMyBatisRepositoryImpl();
        this.initializedDate = LocalDate.now();
        this.collection = new ArrayDeque<>(vehicleRepository.getAll());

        int maxInd = 0;
        for (Vehicle vehicle : collection) {
            maxInd = Math.max(maxInd, vehicle.getId() + 1);
        }
    }

    /**
     * Method to add new vehicle to collection
     *
     * @param vehicle is new element
     */
    public Vehicle add(Vehicle vehicle) {
        vehicle.setCoordinates(saveCoordinates(vehicle.getCoordinates()));
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        collection.add(savedVehicle);
        return savedVehicle;
    }

    public Vehicle update(Vehicle vehicle) {
        vehicle.setCoordinates(saveCoordinates(vehicle.getCoordinates()));
        return vehicleRepository.update(vehicle);
    }

    /**
     * Method to remove vehicle from collection by id
     *
     * @param id is id of vehicle to remove
     */
    public void removeById(Integer id, User user) {
        Vehicle vehicleToDelete = getById(id);
        if (!vehicleToDelete.getCreator().equals(user)) {
            throw new IllegalArgumentException("Нет прав для удаления");
        }

        vehicleRepository.deleteById(id);
        collection.remove(vehicleToDelete);
    }

    /**
     * Method to clean collection
     */
    public void clear(User user) {
        for (Vehicle vehicle : collection) {
            if (vehicle.getCreator().equals(user)) {
                vehicleRepository.deleteByEntity(vehicle);
                collection.remove(vehicle);
            }
        }
    }

    /**
     * Method to remove vehicles, which are greater than input vehicle
     *
     * @param vehicle is inout element
     */
    public void removeGreater(Vehicle vehicle, User user) {
        List<Vehicle> vehiclesToRemove = new ArrayList<>();
        for (Vehicle currVehicle : collection) {
            if (currVehicle.compareTo(vehicle) > 0) {
                vehiclesToRemove.add(currVehicle);
            }
        }

        for (Vehicle vehicleToRemove : vehiclesToRemove) {
            if (vehicleToRemove.getCreator().equals(user)) {
                vehicleRepository.deleteByEntity(vehicleToRemove);
                collection.remove(vehicleToRemove);
            }
        }

        vehiclesToRemove.clear();
    }

    /**
     * Method to remove vehicles, which are lower than input vehicle
     *
     * @param vehicle is inout element
     */
    public void removeLower(Vehicle vehicle, User user) {
        List<Vehicle> vehiclesToRemove = new ArrayList<>();
        for (Vehicle currVehicle : collection) {
            if (currVehicle.compareTo(vehicle) < 0) {
                vehiclesToRemove.add(currVehicle);
            }
        }

        for (Vehicle vehicleToRemove : vehiclesToRemove) {
            if (vehicleToRemove.getCreator().equals(user)) {
                vehicleRepository.deleteByEntity(vehicleToRemove);
                collection.remove(vehicleToRemove);
            }
        }

        vehiclesToRemove.clear();
    }

    /**
     * Method to get average value of engine powers
     *
     * @return average value
     */
    public double averageOfEnginePower() {
        int sumEnginePower = 0;
        for (Vehicle vehicle : collection) {
            sumEnginePower += vehicle.getEnginePower();
        }

        return (double) sumEnginePower / collection.size();
    }

    /**
     * Method to get collection of vehicles, which have fuel's type, which is less than input
     *
     * @param fuelType is input fuel's type
     * @return filtered collection
     */
    public List<Vehicle> filterLessThanFuelType(FuelType fuelType) {
        List<Vehicle> filteredCollection = new ArrayList<>();
        for (Vehicle vehicle : collection) {
            if (vehicle.compareByFuelTypeTo(fuelType) < 0) {
                filteredCollection.add(vehicle);
            }
        }

        return filteredCollection;
    }

    /**
     * Method to return vehicle from collection by id
     *
     * @param id is vehicle's id
     * @return vehicle
     */
    public Vehicle getById(Integer id) {
        for (Vehicle vehicle : collection) {
            if (vehicle.getId().equals(id)) {
                return vehicle;
            }
        }

        throw new IllegalArgumentException("Не существует транспорта с id = " + id);
    }

    public List<Vehicle> getCollection() {
        return new ArrayList<>(collection);
    }

    public LocalDate getInitializedDate() {
        return initializedDate;
    }

    public User getUserByLogin(String login) {
        return userRepository.getByLogin(login);
    }

    public User saveUser(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return userRepository.save(user);
    }

    private Coordinates saveCoordinates(Coordinates coordinates) {
        if (coordinates.getId() != null && coordinatesRepository.getById(coordinates.getId()) != null) {
            return coordinates;
        }

        return coordinatesRepository.save(coordinates);
    }
}
