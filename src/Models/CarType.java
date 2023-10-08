package Models;

public class CarType extends BaseEntity {
    private int numberOfseats;
    private String brand;

    public CarType(int id, int numberOfseats, String brand) {
        super(id);
        this.numberOfseats = numberOfseats;
        this.brand = brand;
    }

    public int getNumberOfseats() {
        return numberOfseats;
    }

    public void setNumberOfseats(int numberOfseats) {
        this.numberOfseats = numberOfseats;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
