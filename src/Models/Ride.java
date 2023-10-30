package Models;

import java.util.ArrayList;

public class Ride extends BaseEntity{
    private Captain captain;
    private ArrayList<Rider> riders;
    private static int numberOfRiders;
    private ArrayList<Location> route;
    private int startTime;
    private float price;
    private ArrayList<Boolean> payments; // ArrayList indicating if the user paid (true) or not (false)
    private int rating; // Rating from 1 to 5




    public Ride(int id, Location startRoute, Captain captain, int startTime, float price) {
        super(id);
        this.route = new ArrayList<>(captain.getCar().getCarType().getNumberOfseats() + 2);
        this.riders = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.price = price;
        this.route.add(startRoute);
        this.captain = captain;
        this.startTime = startTime;
    }

    public ArrayList<Boolean> getPayments() {
        return payments;
    }
    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        } else {
            System.out.println("Invalid rating. Please enter a rating from 1 to 5.");
        }
    }
    // Constructor and other methods remain unchanged

    public int getRating() {
        return rating;
    }
    public void setPayments(ArrayList<Boolean> payments) {
        this.payments = payments;
    }
    public Captain getCaptain() {
        return captain;
    }

    public void setCaptain(Captain captain) {
        this.captain = captain;
    }

    public ArrayList<Rider> getRiders() {
        return riders;
    }

    public void setRiders(ArrayList<Rider> riders) {
        this.riders = riders;
    }

    public static int getNumberOfRiders() {
        return numberOfRiders;
    }

    public static void setNumberOfRiders(int numberOfRiders) {
        Ride.numberOfRiders = numberOfRiders;
    }

    public ArrayList<Location> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Location> route) {
        this.route = route;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
