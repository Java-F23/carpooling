package Models;

import java.util.ArrayList;

public class Ride {
    private Captain captain;
    private ArrayList<Rider> riders;
    private static int numberOfRiders;
    private ArrayList<Location> route;
    private int startTime;

    public Ride(Location startRoute, Captain captain) {
        this.route = new ArrayList<Location>(captain.getCar().getSeats());
        this.route.add(startRoute);
        this.captain = captain;
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
}
