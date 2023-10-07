package Models;

public class Location {
    private int lng;
    private int lat;

    public Location(int lng, int lat) {
        this.lng = lng;
        this.lat = lat;
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
