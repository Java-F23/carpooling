package Models;

public class Location {
    private int lng;
    private int lat;
    private String region;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Location(int lng, int lat, String region) {
        this.lng = lng;
        this.lat = lat;
        this.region = region;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }
}
