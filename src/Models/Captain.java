package Models;

public class Captain extends BaseUser{
    private Car car;

    public Captain(int id, String name, String email, String phoneNumber, Car car) {
        super(id, name, email, phoneNumber);
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
