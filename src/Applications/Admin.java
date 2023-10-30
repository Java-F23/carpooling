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
        this.riderService = rrs;
        this.captainService = cs;
        this.rideService = rs;
    }
    public Rider getRiderById(int id) {
        return riderService.getRiderById(id);
    }
    public void printRiders(){
        ArrayList<Rider> riders= riderService.getAllRiders();
        for(Rider r : riders){
            System.out.println(r.getName() + " " + r.getId());
        }
    }

    public void printRideStatistics() {
        ArrayList<Ride> rides = rideService.getAllRides();
        for (Ride ride : rides) {
            int totalSeats = ride.getCaptain().getCar().getCarType().getNumberOfseats();
            int bookedSeats = ride.getRiders().size();
            int availableSeats = totalSeats - bookedSeats;
            int paidSeats = 0;

            for (Boolean paymentStatus : ride.getPayments()) {
                if (paymentStatus) {
                    paidSeats++;
                }
            }

            System.out.println("Ride ID: " + ride.getId());
            System.out.println("Total Seats: " + totalSeats);
            System.out.println("Booked Seats: " + bookedSeats);
            System.out.println("Available Seats: " + availableSeats);
            System.out.println("Paid Seats: " + paidSeats);
            System.out.println("--------------------------");
        }
    }
    public void RemoveRide(int rideId){
        rideService.removeRideById(rideId);
    }

    public Captain getCaptainById(int id) {
        return captainService.getCaptainById(id);
    }

    public Ride getRideById(int id) {
        return rideService.getRideById(id);
    }

    public void addRider(int id, String name, String email, String phoneNumber, Location riderLoc, String password) {
        Rider rider = new Rider(id, name, email, phoneNumber, riderLoc, password);
        riderService.addRiders(rider);
        System.out.println("Rider added successfully.");
    }

    public void updateRider(int id, Rider updatedRider) {
        riderService.updateRider(id, updatedRider);
        System.out.println("Rider updated successfully.");
    }

    public void addCaptain(int id, String name, String email, String phoneNumber, Car car, String password) {
        Captain captain = new Captain(id, name, email, phoneNumber, car, password);
        captainService.addCaptain(id, name, email, phoneNumber, car, password);
        System.out.println("Captain added successfully.");
    }

    public void updateCaptain(int id, Captain updatedCaptain) {
        captainService.updateCaptain(id, updatedCaptain);
        System.out.println("Captain updated successfully.");
    }

    public void createRide(int rideId, Location startRoute, Captain captain, int startTime, float price) {
        if(captain!=null) {
            rideService.createRide(rideId, startRoute, captain, startTime, price);
        }
        else{
            System.out.println("Captain does not exist");
        }

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

