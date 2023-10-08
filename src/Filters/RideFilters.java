package Filters;

public class RideFilters {
    public String Region;
    public int startTime;

    public RideFilters(String region, int startTime) {
        Region = region;
        this.startTime = startTime;
    }

    public RideFilters(String region) {
        Region = region;
    }

    public RideFilters(int startTime) {
        this.startTime = startTime;
    }
}
