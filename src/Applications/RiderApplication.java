package Applications;

import Filters.RideFilters;
import Models.*;
import Services.PaymentService;
import Services.RideService;
import Services.RiderService;

import java.util.ArrayList;

public class RiderApplication {
    private RideService rideService;
    private RiderService riderService;
    private int currentRiderId;
    private PaymentService paymentService;

    public RiderApplication(RideService rideService, RiderService rrs, int currentRiderId, PaymentService paymentService) {
        this.rideService = rideService;
        this.riderService = rrs;
        this.currentRiderId = currentRiderId;
        this.paymentService = paymentService;
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


    public ArrayList<Ride> getAvailableRidesForRider() {
        Rider rider = riderService.getRiderById(currentRiderId); // Get the current rider
        ArrayList<Ride> availableRides = new ArrayList<>();
        ArrayList<Ride> rides = rideService.getAllRides();

        for (Ride ride : rides) {
            // Check if the rider is not part of the ride and there are available seats
            if (!ride.getRiders().contains(rider)) {
                int availableSeats = ride.getCaptain().getCar().getCarType().getNumberOfseats() - ride.getRiders().size();
                if (availableSeats > 0) {
                    availableRides.add(ride);
                }
            }
        }
        return availableRides;
    }

    public void cancelRide(int rideId) {
        Ride r = rideService.getRideById(rideId);
        if(r!=null){
            rideService.cancelReservation(currentRiderId, r);
        }else{
            System.out.println("Ride Not found");
        }
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
    public void bookRide(int rideId) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = riderService.getRiderById(currentRiderId);
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

    public ArrayList<Ride> searchAvailableRidesByFilter(RideFilters filters) {
        ArrayList<Ride> filteredRides = rideService.filterRides(filters);
        ArrayList<Ride> availableRides = new ArrayList<>();

        for (Ride ride : filteredRides) {
            int availableSeats = ride.getCaptain().getCar().getCarType().getNumberOfseats() - ride.getRiders().size();
            if (availableSeats > 0) {
                availableRides.add(ride);
            }
        }
        return availableRides;
    }

    public boolean hasRiderPaidForRide(int rideId) {
        Ride ride = rideService.getRideById(rideId);
        if (ride != null) {
            // Get the index of the rider in the ride's riders list
            int riderIndex = ride.getRiders().indexOf(riderService.getRiderById(currentRiderId));
            if (riderIndex != -1) {
                // Check if the corresponding payment flag is set to true
                return ride.getPayments().get(riderIndex);
            }
        }
        // If the ride or rider is not found, or the payment flag is false, return false
        return false;
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

    public void makePayment(int rideId, String paymentType) {
        Ride ride = rideService.getRideById(rideId);
        if (ride != null && ride.getRiders().contains(riderService.getRiderById(currentRiderId))) {
            if (ride.getPayments().get(ride.getRiders().indexOf(riderService.getRiderById(currentRiderId)))) {
                System.out.println("Payment already made for this ride.");
            } else {
                boolean paymentStatus = paymentService.pay(rideId, currentRiderId, paymentType);
                if (paymentStatus) {
                    System.out.println("Payment successful! Enjoy your ride!");
                } else {
                    System.out.println("Payment failed. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid ride ID or you are not a part of this ride.");
        }
    }

    public void viewRides(){
        Rider rider = riderService.getRiderById(currentRiderId);
        rideService.printRidesByRider(rider);
    }

    public void rateRide(int rideId, int rating) {
        Ride ride = rideService.getRideById(rideId);
        if (ride != null) {
            rideService.rateRide(rideId, currentRiderId, rating);
        } else {
            System.out.println("Ride not found.");
        }
    }
    public void viewRiderRidesWithPayment() {
        rideService.viewRiderRidesWithPayment(currentRiderId);
    }
}