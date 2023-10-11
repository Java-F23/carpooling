import Applications.Admin;
import Models.*;
import Services.CaptainService;
import Services.CarService;
import Services.RideService;
import Services.RiderService;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        RideService rideService = new RideService();
        CaptainService captainService = new CaptainService();
        RiderService riderService = new RiderService();
        Admin adminApp = new Admin(rideService, captainService, riderService);

        // Example usage
        adminApp.addRider(1, "John Doe", "john@example.com", "1234567890");
        adminApp.addCaptain(1, "Captain Smith", "captain@example.com", "0987654321", new Car(1, "ABC123", new CarType(1, 4, "Toyota"), "Red"));

        Rider rider = adminApp.getRiderById(1);
        Captain captain = adminApp.getCaptainById(1);

        adminApp.createRide(1, new Location(0, 0, "City A"), captain, 123456789);
        adminApp.addRiderToRide(1, rider);
        adminApp.manageRideReservations(1);
    }
}