package Seeders;

import Models.*;
import Repositories.GenericRepository;
import Repositories.RideRepository;

import java.util.Random;

public class Seeder {

    public static void seedRiders(GenericRepository<Rider> riderRepo, int numberOfRiders) {
        for (int i = 1; i <= numberOfRiders; i++) {
            String name = "Rider" + i;
            String email = "rider" + i + "@example.com";
            String phoneNumber = generateRandomPhoneNumber();
            String password = "12345678";
            Location location = new Location(generateRandomNumber(30, 40), generateRandomNumber(20, 30), "Region" + i);
            Rider rider = new Rider(i, name, email, phoneNumber, location, password);
            riderRepo.addData(rider);
        }
    }

    public static void seedCaptains(GenericRepository<Captain> captainRepo, int numberOfCaptains) {
        for (int i = 1; i <= numberOfCaptains; i++) {
            String name = "Captain" + i;
            String email = "captain" + i + "@example.com";
            String phoneNumber = generateRandomPhoneNumber();
            String password = "12345678";
            CarType carType = new CarType(i, generateRandomNumber(4, 8), "Brand" + i);
            Car car = new Car(i, "Plate" + i, carType, "Color" + i);
            Captain captain = new Captain(i, name, email, phoneNumber, car, password);
            captainRepo.addData(captain);
        }
    }

    public static void seedRides(RideRepository rideRepo, int numberOfRides,
                                 GenericRepository<Rider> riderRepo, GenericRepository<Captain> captainRepo) {
        Random random = new Random();
        for (int i = 1; i <= numberOfRides; i++) {
            Location location = new Location(generateRandomNumber(30, 40), generateRandomNumber(20, 30), "Region" + i);
            int startTime = generateRandomNumber(1, 24); // Assuming rides can start at any hour
            float price = generateRandomNumber(10, 50); // Assuming random price between 10 and 50

            Captain captain = captainRepo.getAll().get(random.nextInt(captainRepo.getAll().size()));
            Ride ride = new Ride(i, location, captain, startTime, price);
            int numberOfRiders = random.nextInt(ride.getCaptain().getCar().getCarType().getNumberOfseats()) + 1;
            for (int j = 0; j < numberOfRiders; j++) {
                Rider rider = riderRepo.getAll().get(random.nextInt(riderRepo.getAll().size()));
                ride.getRiders().add(rider);
                ride.getPayments().add(random.nextBoolean());
            }

            rideRepo.addData(ride);
        }
    }

    private static String generateRandomPhoneNumber() {
        Random random = new Random();
        StringBuilder phoneNumber = new StringBuilder("01");
        for (int i = 0; i < 8; i++) {
            phoneNumber.append(random.nextInt(10));
        }
        return phoneNumber.toString();
    }

    private static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
