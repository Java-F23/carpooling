package Services;

import Models.Car;
import Repositories.GenericRepository;

import java.util.ArrayList;

public class CarService {
    private GenericRepository<Car> carRepository;

    public CarService() {
        this.carRepository = new GenericRepository<Car>();
    }

    public void addCar(Car car) {
        carRepository.addData(car);
    }

    public void removeCarById(int id) {
        carRepository.removeById(id);
    }

    public void updateCar(int id, Car updatedCar) {
        carRepository.update(id, updatedCar);
    }

    public Car getCarById(int id) {
        return carRepository.getAll()
                .stream()
                .filter(car -> car.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public ArrayList<Car> getAllCars() {
        return carRepository.getAll();
    }

    public ArrayList<Car> getCarsByColor(String color) {
        ArrayList<Car> carsByColor = new ArrayList<>();
        for (Car car : carRepository.getAll()) {
            if (car.getColor().equalsIgnoreCase(color)) {
                carsByColor.add(car);
            }
        }
        return carsByColor;
    }

    public ArrayList<Car> getCarsByBrand(String brand) {
        ArrayList<Car> carsByBrand = new ArrayList<>();
        for (Car car : carRepository.getAll()) {
            if (car.getCarType().getBrand().equalsIgnoreCase(brand)) {
                carsByBrand.add(car);
            }
        }
        return carsByBrand;
    }

    public ArrayList<Car> getCarsByNumberOfSeats(int numberOfSeats) {
        ArrayList<Car> carsBySeats = new ArrayList<>();
        for (Car car : carRepository.getAll()) {
            if (car.getCarType().getNumberOfseats() == numberOfSeats) {
                carsBySeats.add(car);
            }
        }
        return carsBySeats;
    }
}
