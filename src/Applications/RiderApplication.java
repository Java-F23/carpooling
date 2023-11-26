package Applications;

import Filters.RideFilters;
import Models.Ride;
import Models.Rider;
import Services.PaymentService;
import Services.RideService;
import Services.RiderService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Ride> getRidesBookedByRider(int riderId) {
        Rider rider = riderService.getRiderById(riderId);
        return rideService.getAllRides().stream()
                .filter(ride -> ride.getRiders().contains(rider))
                .collect(Collectors.toList());
    }

    public List<Ride> getAvailableRidesForRider() {
        Rider rider = riderService.getRiderById(currentRiderId);
        return rideService.getAllRides().stream()
                .filter(ride -> !ride.getRiders().contains(rider) &&
                        ride.getCaptain().getCar().getCarType().getNumberOfseats() - ride.getRiders().size() > 0)
                .collect(Collectors.toList());
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

    public List<Ride> searchAvailableRidesByFilter(RideFilters filters) {
        return rideService.filterRides(filters).stream()
                .filter(ride -> {
                    int availableSeats = ride.getCaptain().getCar().getCarType().getNumberOfseats() - ride.getRiders().size();
                    return availableSeats > 0;
                })
                .collect(Collectors.toList());
    }

    public boolean hasRiderPaidForRide(int rideId) {
        Ride ride = rideService.getRideById(rideId);
        if (ride != null) {
            int riderIndex = ride.getRiders().indexOf(riderService.getRiderById(currentRiderId));
            return riderIndex != -1 && ride.getPayments().get(riderIndex);
        }
        return false;
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
}
