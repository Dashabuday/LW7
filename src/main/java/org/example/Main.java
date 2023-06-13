package org.example;

import com.google.common.hash.Hashing;
import org.example.console.Register;
import org.example.enums.FuelType;
import org.example.enums.VehicleType;
import org.example.models.Coordinates;
import org.example.models.User;
import org.example.models.Vehicle;
import org.example.repositories.CoordinatesRepository;
import org.example.repositories.UserRepository;
import org.example.repositories.VehicleRepository;
import org.example.services.VehicleService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * Main application class
 */
public class Main {
    public static void main(String[] args) throws IOException {
        VehicleService service = new VehicleService();
        Register register = new Register(service);
        register.run();

//        testCrud();
    }

    private static void testCrud() {
        Random random = new Random();
        int cntSavedModels = 5;

        CoordinatesRepository cRepository = new CoordinatesRepository();

        System.out.println("Add :");

        Coordinates savedCoordinates = null;
        for (int i = 0; i < cntSavedModels; i++) {
            Coordinates coordinates = new Coordinates();
            coordinates.setX(random.nextInt(1000));
            coordinates.setY(random.nextInt(1000));
            savedCoordinates = cRepository.save(coordinates);
        }

        int lastSavedIdOfCoordinate = savedCoordinates.getId();

        cRepository.getAll().forEach(System.out::println);
        System.out.println("===========================");

        System.out.println("Print by id :");
        System.out.println(cRepository.getById(lastSavedIdOfCoordinate));
        System.out.println("===========================");

        System.out.println("Update :");
        Coordinates coordinatesForUpdate = cRepository.getById(lastSavedIdOfCoordinate);
        coordinatesForUpdate.setX(123);
        coordinatesForUpdate.setY(321);
        cRepository.update(coordinatesForUpdate);
        System.out.println(cRepository.getById(savedCoordinates.getId()));
        System.out.println("===========================");

        System.out.println("Add :");
        Coordinates coordinates1 = new Coordinates();
        coordinates1.setX(66);
        coordinates1.setY(99);
        savedCoordinates = cRepository.save(coordinates1);
        System.out.println(savedCoordinates);
        System.out.println("===========================");

        System.out.println("Delete by id :");
        cRepository.deleteById(savedCoordinates.getId());
        cRepository.getAll().forEach(System.out::println);
        System.out.println("===========================");

        System.out.println("===========================");
        System.out.println("===========================");

        VehicleRepository vRepository = new VehicleRepository();
        List<Coordinates> coordinates = cRepository.getAll();

        UserRepository uRepository = new UserRepository();
        User user = new User();
        user.setLogin("dog@gav.ru");
        user.setPassword("I love cats");
        uRepository.save(user);

        System.out.println("Add :");

        Vehicle savedVehicle = null;
        for (int i = 0; i < cntSavedModels; i++) {
            Vehicle vehicleToSave = new Vehicle();
            vehicleToSave.setName("My vehicle number " + i);
            vehicleToSave.setCoordinates(coordinates.get(i));
            vehicleToSave.setCreationDate(LocalDate.now());
            vehicleToSave.setEnginePower(random.nextInt(1000));
            vehicleToSave.setType(VehicleType.BOAT);
            vehicleToSave.setFuelType(FuelType.MANPOWER);
            vehicleToSave.setCreator(user);
            savedVehicle = vRepository.save(vehicleToSave);
        }

        vRepository.getAll().forEach(System.out::println);
        int lastSavedIdOfVehicle = savedVehicle.getId();
        System.out.println("===========================");

        System.out.println("Print by id :");
        System.out.println(vRepository.getById(lastSavedIdOfVehicle));
        System.out.println("===========================");

        System.out.println("Update :");
        Vehicle vehicleForUpdate = vRepository.getById(lastSavedIdOfVehicle);
        vehicleForUpdate.setName("My Updated Plane");
        vRepository.update(vehicleForUpdate);
        System.out.println(vRepository.getById(lastSavedIdOfVehicle));
        System.out.println("===========================");

        System.out.println("Add :");
        Vehicle vehicleToSave = new Vehicle();
        vehicleToSave.setName("My boat");
        vehicleToSave.setCoordinates(coordinates.get(0));
        vehicleToSave.setCreationDate(LocalDate.now());
        vehicleToSave.setEnginePower(random.nextInt(1000));
        vehicleToSave.setType(VehicleType.BOAT);
        vehicleToSave.setFuelType(FuelType.MANPOWER);
        vehicleToSave.setCreator(user);
        savedVehicle = vRepository.save(vehicleToSave);
        System.out.println(savedVehicle);
        System.out.println("===========================");

        System.out.println("Delete by entity :");
        System.out.println(savedVehicle.getId());
        vRepository.deleteByEntity(savedVehicle);
        vRepository.getAll().forEach(System.out::println);
        System.out.println("===========================");

        System.out.println("===========================");
        System.out.println("===========================");

        System.out.println("Delete all vehicles :");
        vRepository.deleteAll();
        vRepository.getAll().forEach(System.out::println);
        System.out.println("===========================");

        System.out.println("Delete all coordinates :");
        cRepository.deleteAll();
        cRepository.getAll().forEach(System.out::println);
        System.out.println("===========================");

        uRepository.deleteByEntity(user);
    }
}