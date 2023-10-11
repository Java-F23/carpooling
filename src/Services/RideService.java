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

    public RideService() {
        this.rideRepository = new RideRepository();
        this.riderService = new RiderService();
    }

    public void createRide(int id, Location startRoute, Captain captain, int startTime) {
        Ride ride = new Ride(id, startRoute, captain, startTime);
        rideRepository.addData(ride);
    }

    public void addRiderToRide(int rideId, Rider rider) {
        Ride ride = rideRepository.getById(rideId);
        if (ride != null && ride.getRiders().size() < ride.getCaptain().getCar().getCarType().getNumberOfseats()) {
            ride.getRiders().add(rider);
            Ride.setNumberOfRiders(ride.getRiders().size());
            System.out.println("Rider added to the ride successfully.");
        } else if (ride != null) {
            System.out.println("The ride is already full. Rider cannot be added.");
        } else {
            System.out.println("Ride not found.");
        }
    }

    public ArrayList<Ride> filterRides(RideFilters filters) {
        return rideRepository.getByFilter(filters);
    }



    public void removeRiderFromRide(int rideId, int riderId) {
        Ride ride = getRideById(rideId);
        Rider rider = riderService.getRiderById(riderId);

        if (ride != null && rider != null && ride.getRiders().contains(rider)) {
            ride.getRiders().remove(rider);
            System.out.println("Rider removed from the ride successfully.");
        } else {
            System.out.println("Ride not found or rider not booked for this ride.");
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
