package Services;

import Filters.RideFilters;
import Models.Captain;
import Models.Location;
import Models.Ride;
import Models.Rider;
import Repositories.RideRepository;

import java.util.ArrayList;
import java.util.List;

public class RideService {
    private RideRepository rideRepository;
    private RiderService riderService;

    public RideService(RiderService rs, RideRepository rideRepo) {
        this.rideRepository = rideRepo;
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

    public ArrayList<Ride> getRidesByCaptain(Captain captain) {
        ArrayList<Ride> ridesByCaptain = new ArrayList<>();
        for (Ride ride : rideRepository.getAll()) {
            if (ride.getCaptain().equals(captain)) {
                ridesByCaptain.add(ride);
            }
        }
        return ridesByCaptain;
    }

    public List<String> getUsersWhoPaid(int rideId) {
        Ride ride = rideRepository.getById(rideId);
        List<String> paidUsers = new ArrayList<>();

        if (ride != null) {
            for (int i = 0; i < ride.getRiders().size(); i++) {
                if (ride.getPayments().get(i)) {
                    Rider rider = ride.getRiders().get(i);
                    paidUsers.add(rider.getName());
                }
            }
        }

        return paidUsers;
    }

    public List<String> getUsersWhoDidNotPay(int rideId) {
        Ride ride = rideRepository.getById(rideId);
        List<String> unpaidUsers = new ArrayList<>();

        if (ride != null) {
            for (int i = 0; i < ride.getRiders().size(); i++) {
                if (!ride.getPayments().get(i)) {
                    Rider rider = ride.getRiders().get(i);
                    unpaidUsers.add(rider.getName());
                }
            }
        }

        return unpaidUsers;
    }

    public int getTotalNumberOfUsers(int rideId) {
        Ride ride = rideRepository.getById(rideId);

        if (ride != null) {
            return ride.getRiders().size();
        }

        return 0;
    }

}
