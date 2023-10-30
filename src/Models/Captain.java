package Models;

public class Captain extends BaseUser{
    private Car car;

    public Captain(int id, String name, String email, String phoneNumber, Car car, String password) {
        super(id, name, email, phoneNumber, password);
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
