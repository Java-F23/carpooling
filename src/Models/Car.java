package Models;

public class Car extends BaseEntity{
    private String plate;
    private String color;
    private CarType carType;



    public Car(int id, String plate, CarType carType, String color) {
        super(id);
        this.plate = plate;
        this.carType = carType;
        this.color = color;
    }


    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }


    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }



    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
