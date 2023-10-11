package Applications;

import Filters.RideFilters;
import Models.*;
import Services.RideService;
import Services.RiderService;

import java.util.ArrayList;

public class RiderApplication {
    private RideService rideService;
    private RiderService riderService;
    private int currentRiderId;

    public RiderApplication(RideService rideService, RiderService rrs, int currentRiderId) {
        this.rideService = rideService;
        this.riderService = rrs;
        this.currentRiderId = currentRiderId;
    }

    public ArrayList<Ride> getRidesBookedByRider(int riderId) {
        Rider rider = riderService.getRiderById(riderId); // Assuming you have a method to get rider by ID
        ArrayList<Ride> ridesBookedByRider = new ArrayList<>();
        if (rider != null) {
            for (Ride ride : rideService.getAllRides()) {
                if (ride.getRiders().contains(rider)) {
                    ridesBookedByRider.add(ride);
                }
            }
        }
        return ridesBookedByRider;
    }

    public void cancelRide(int rideId) {
        rideService.removeRiderFromRide(rideId, currentRiderId);
    }

    public void viewAvailableRides() {
        ArrayList<Ride> availableRides = rideService.getAllRides();
        for (Ride ride : availableRides) {
            int availableSeats = ride.getCaptain().getCar().getCarType().getNumberOfseats() - ride.getRiders().size();
            System.out.println("Ride ID: " + ride.getId());
            System.out.println("Start Time: " + ride.getStartTime());
            System.out.println("Available Seats: " + availableSeats);
            System.out.println("--------");
        }
    }
    public void bookRide(int rideId, Rider rider) {
        Ride ride = rideService.getRideById(rideId);
        if (ride != null) {
            int availableSeats = ride.getCaptain().getCar().getCarType().getNumberOfseats() - ride.getRiders().size();
            if (availableSeats > 0) {
                rideService.addRiderToRide(rideId, rider);
                System.out.println("Ride booked successfully!");
            } else {
                System.out.println("Sorry, the ride is full. Cannot book the ride.");
            }
        } else {
            System.out.println("Ride not found.");
        }
    }

    public void searchRidesByFilter(RideFilters filters) {
        ArrayList<Ride> filteredRides = rideService.filterRides(filters);
        if (filteredRides.isEmpty()) {
            System.out.println("No rides found matching the specified filters.");
        } else {
            System.out.println("Rides matching the specified filters:");
            for (Ride ride : filteredRides) {
                int availableSeats = ride.getCaptain().getCar().getCarType().getNumberOfseats() - ride.getRiders().size();
                System.out.println("Ride ID: " + ride.getId());
                System.out.println("Start Time: " + ride.getStartTime());
                System.out.println("Available Seats: " + availableSeats);
                System.out.println("--------");
            }
        }
    }

    public void viewRideInformation(int rideId) {
        Ride ride = rideService.getRideById(rideId);
        if (ride != null) {
            System.out.println("Ride ID: " + ride.getId());
            System.out.println("Start Time: " + ride.getStartTime());
            System.out.println("Route:");
            ArrayList<Location> route = ride.getRoute();
            for (Location location : route) {
                System.out.println("   Lat: " + location.getLat() + ", Lng: " + location.getLng() + ", Region: " + location.getRegion());
            }
            System.out.println("Car Information:");
            Car car = ride.getCaptain().getCar();
            System.out.println("   Plate: " + car.getPlate());
            System.out.println("   Color: " + car.getColor());
            System.out.println("   Car Type: " + car.getCarType().getBrand() + ", Seats: " + car.getCarType().getNumberOfseats());
            System.out.println("Captain Information:");
            Captain captain = ride.getCaptain();
            System.out.println("   Name: " + captain.getName());
            System.out.println("   Email: " + captain.getEmail());
            System.out.println("   Phone Number: " + captain.getPhoneNumber());
        } else {
            System.out.println("Ride not found.");
        }
    }
}