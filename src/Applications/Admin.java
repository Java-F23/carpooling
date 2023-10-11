package Applications;

import Models.*;
import Services.CaptainService;
import Services.RideService;
import Services.RiderService;

import java.util.ArrayList;

public class Admin {
    private RiderService riderService;
    private CaptainService captainService;
    private RideService rideService;

    public Admin(RideService rs, CaptainService cs, RiderService rrs) {
        this.riderService = new RiderService();
        this.captainService = new CaptainService();
        this.rideService = new RideService();
    }
    public Rider getRiderById(int id) {
        return riderService.getRiderById(id);
    }

    public Captain getCaptainById(int id) {
        return captainService.getCaptainById(id);
    }

    public Ride getRideById(int id) {
        return rideService.getRideById(id);
    }

    public void addRider(int id, String name, String email, String phoneNumber) {
        Rider rider = new Rider(id, name, email, phoneNumber);
        riderService.addRiders(rider);
        System.out.println("Rider added successfully.");
    }

    public void updateRider(int id, Rider updatedRider) {
        riderService.updateRider(id, updatedRider);
        System.out.println("Rider updated successfully.");
    }

    public void addCaptain(int id, String name, String email, String phoneNumber, Car car) {
        Captain captain = new Captain(id, name, email, phoneNumber, car);
        captainService.addCaptain(id, name, email, phoneNumber, car);
        System.out.println("Captain added successfully.");
    }

    public void updateCaptain(int id, Captain updatedCaptain) {
        captainService.updateCaptain(id, updatedCaptain);
        System.out.println("Captain updated successfully.");
    }

    public void createRide(int rideId, Location startRoute, Captain captain, int startTime) {
        rideService.createRide(rideId, startRoute, captain, startTime);
        System.out.println("Ride created successfully.");
    }

    public void addRiderToRide(int rideId, Rider rider) {
        rideService.addRiderToRide(rideId, rider);
        System.out.println("Rider added to the ride successfully.");
    }

    public void manageRideReservations(int rideId) {
        Ride ride = rideService.getRideById(rideId);
        if (ride != null) {
            ArrayList<Rider> riders = ride.getRiders();
            System.out.println("Riders on the ride:");
            for (Rider rider : riders) {
                System.out.println(rider.getName() + " - " + rider.getEmail());
            }
        } else {
            System.out.println("Ride not found.");
        }
    }


}

