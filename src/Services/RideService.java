package Services;

import Filters.RideFilters;
import Models.Captain;
import Models.Location;
import Models.Ride;
import Models.Rider;
import Repositories.RideRepository;

import java.util.ArrayList;

public class RideService {
    private RideRepository rideRepository;
    private RiderService riderService;

    public RideService(RiderService rs) {
        this.rideRepository = new RideRepository();
        this.riderService = rs;
    }

    public void createRide(int id, Location startRoute, Captain captain, int startTime, float price) {
        Ride ride = new Ride(id, startRoute, captain, startTime, price);
        rideRepository.addData(ride);
    }

    public void addRiderToRide(int rideId, Rider rider) {
        Ride ride = rideRepository.getById(rideId);
        if (ride != null && ride.getRiders().size() < ride.getCaptain().getCar().getCarType().getNumberOfseats()) {
            ride.getRiders().add(rider);
            ride.getPayments().add(false);
            Ride.setNumberOfRiders(ride.getRiders().size());
            System.out.println("Rider added to the ride successfully.");
        } else if (ride != null) {
            System.out.println("The ride is already full. Rider cannot be added.");
        } else {
            System.out.println("Ride not found.");
        }
    }

    public void viewRiderRidesWithPayment(int riderId) {
        Rider rider = riderService.getRiderById(riderId);
        if (rider != null) {
            ArrayList<Ride> riderRides = new ArrayList<>();
            for (Ride ride : getAllRides()) {
                if (ride.getRiders().contains(rider)) {
                    riderRides.add(ride);
                }
            }

            if (!riderRides.isEmpty()) {
                System.out.println("Rides booked by rider with ID " + riderId + ":");
                for (Ride ride : riderRides) {
                    boolean hasPaid = ride.getPayments().get(ride.getRiders().indexOf(rider));
                    System.out.println("Ride ID: " + ride.getId() + ", Start Time: " + ride.getStartTime() +
                            ", Paid: " + (hasPaid ? "Yes" : "No"));
                }
            } else {
                System.out.println("Rider with ID " + riderId + " has not booked any rides.");
            }
        } else {
            System.out.println("Rider not found.");
        }
    }

    public void rateRide(int rideId, int riderId, int rating) {
        Ride ride = getRideById(rideId);
        Rider rider = riderService.getRiderById(riderId);

        if (ride != null && rider != null && ride.getRiders().contains(rider)) {
            ride.setRating(rating);
            System.out.println("Ride rated successfully.");
        } else {
            System.out.println("Ride not found or rider not on this ride.");
        }
    }

    public ArrayList<Ride> filterRides(RideFilters filters) {
        return rideRepository.getByFilter(filters);
    }



    public void printRidesByRider(Rider rider) {
        if (rider != null) {
            ArrayList<Ride> ridesByRider = new ArrayList<>();
            for (Ride ride : rideRepository.getAll()) {
                if (ride.getRiders().contains(rider)) {
                    ridesByRider.add(ride);
                }
            }

            if (!ridesByRider.isEmpty()) {
                System.out.println("Rides booked by rider with ID " + rider.getId() + ":");
                for (Ride ride : ridesByRider) {
                    System.out.println("Ride ID: " + ride.getId() + ", Start Time: " + ride.getStartTime());
                }
            } else {
                System.out.println("Rider with ID " + rider.getId() + " has not booked any rides.");
            }
        } else {
            System.out.println("Rider not found.");
        }
    }

    public void cancelReservation(int riderId, Ride ride) {

        Rider rider = riderService.getRiderById(riderId);
        if (rider != null && ride.getRiders().contains(rider)) {
            int riderIndex = ride.getRiders().indexOf(rider);
            ride.getRiders().remove(riderIndex);
            ride.getPayments().remove(riderIndex); // Remove corresponding payment status
            System.out.println("Reservation canceled successfully.");
        } else {
            System.out.println("Rider not found or not booked for this ride.");
        }
    }
    public Ride getRideById(int rideId) {
        return rideRepository.getById(rideId);
    }

    public ArrayList<Ride> getAllRides() {
        return rideRepository.getAll();
    }

    public void updateRide(int rideId, Ride updatedRide) {
        rideRepository.update(rideId, updatedRide);
    }

    public void removeRideById(int rideId) {
        rideRepository.removeById(rideId);
    }
}
