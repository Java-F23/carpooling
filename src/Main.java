
import Applications.Admin;
import Applications.RiderApplication;
import Filters.RideFilters;
import Models.*;
import Services.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CaptainService captainService = new CaptainService();
        RiderService riderService = new RiderService();
        RideService rideService = new RideService(riderService);
        PaymentService paymentService = new PaymentService(rideService, riderService);
        Admin adminApp = new Admin(rideService, captainService, riderService);

        // Story 1
        System.out.println("-------- Story 1 --------");
        Location userLocation = new Location(10, 10, "C");
        adminApp.addRider(1, "John Doe", "john@example.com", "1234567890", userLocation);
        adminApp.addRider(3, "Emma White", "emma@example.com", "3334445555", new Location(25, 25, "F"));
        adminApp.addRider(4, "Liam Brown", "liam@example.com", "4445556666", new Location(30, 30, "G"));
        adminApp.addRider(5, "Olivia Davis", "olivia@example.com", "5556667777", new Location(35, 35, "H"));

        adminApp.addCaptain(1, "Captain Smith", "captain@example.com", "0987654321", new Car(1, "ABC123", new CarType(1, 4, "Toyota"), "Red"));
        adminApp.addCaptain(3, "Captain Johnson", "johnson@example.com", "9876543210", new Car(3, "JKL321", new CarType(3, 6, "Chevrolet"), "Silver"));
        adminApp.addCaptain(4, "Captain Lee", "lee@example.com", "8765432109", new Car(4, "MNO654", new CarType(4, 4, "Honda"), "Black"));
        adminApp.addCaptain(5, "Captain Garcia", "garcia@example.com", "7654321098", new Car(5, "PQR987", new CarType(5, 5, "Mazda"), "White"));
        adminApp.printRiders();
        System.out.println("------------------------");

        // Story 2
        System.out.println("-------- Story 2 --------");
        Captain captain = adminApp.getCaptainById(1);
        adminApp.createRide(1, new Location(0, 0, "City A"), captain, 123456789, 20.0f);
        adminApp.createRide(5, new Location(15, 15, "City D"), captain, 987654321, 18.0f);
        RiderApplication riderApp = new RiderApplication(rideService, riderService, 1, paymentService);
        riderApp.viewAvailableRides();
        System.out.println("------------------------");

        // Story 3
        System.out.println("-------- Story 3 --------");
        Rider rider = adminApp.getRiderById(1);
        riderApp.searchRidesByFilter(new RideFilters("City A"));
        System.out.println("------------------------");

        // Story 4
        System.out.println("-------- Story 4 --------");
        adminApp.updateRider(1, new Rider(1, "Seifeldin Elsayed", "seif@example.com", "1234567890", userLocation));
        adminApp.printRiders();
        System.out.println("------------------------");

        // Story 5
        System.out.println("-------- Story 5 --------");
        riderApp.viewRideInformation(1);
        System.out.println("------------------------");

        // Story 6
        System.out.println("-------- Story 6 --------");
        Captain captain1 = adminApp.getCaptainById(3);
        Captain captain4 = adminApp.getCaptainById(4);
        Captain captain5 = adminApp.getCaptainById(5);

        adminApp.createRide(2, new Location(0, 0, "City A"), captain1, 123456789, 20.0f);
        adminApp.createRide(6, new Location(20, 20, "City E"), captain4, 876543210, 22.5f);
        adminApp.createRide(7, new Location(25, 25, "City F"), captain5, 765432109, 20.0f);
        riderApp.viewAvailableRides();
        adminApp.RemoveRide(2);
        riderApp.viewAvailableRides();
        System.out.println("------------------------");

        // Story 7
        System.out.println("-------- Story 7 --------");
        riderApp.bookRide(1);
        System.out.println("------------------------");

        // Story 8
        System.out.println("-------- Story 8 --------");
        riderApp.viewRides();
        System.out.println("------------------------");

        // Story 10
        System.out.println("-------- Story 10 --------");
        riderApp.makePayment(1, "visa");
        System.out.println("------------------------");

        // Story 11
        System.out.println("-------- Story 11 --------");
        adminApp.manageRideReservations(1);
        System.out.println("------------------------");

        // Story 12
        System.out.println("-------- Story 12 --------");
        riderApp.rateRide(1, 3);
        System.out.println("------------------------");

        // Story 13
        System.out.println("-------- Story 13 --------");
        riderApp.viewRiderRidesWithPayment();
        System.out.println("------------------------");
        // Story 14
        System.out.println("-------- Story 13 --------");
        adminApp.printRideStatistics();
        System.out.println("------------------------");
    }
}
