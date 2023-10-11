package Services;

import Models.Captain;
import Models.Car;
import Repositories.GenericRepository;

import java.util.ArrayList;

public class CaptainService {
    private GenericRepository<Captain> captainRepository;

    public CaptainService() {
        this.captainRepository = new GenericRepository<>();
    }

    public void addCaptain(int id, String name, String email, String phoneNumber, Car car) {
        Captain captain = new Captain(id, name, email, phoneNumber, car);
        captainRepository.addData(captain);
    }

    public void updateCaptain(int id, Captain updatedCaptain) {
        captainRepository.update(id, updatedCaptain);
    }

    public Captain getCaptainById(int id) {
        return captainRepository.getById(id);
    }

    public ArrayList<Captain> getAllCaptains() {
        return captainRepository.getAll();
    }

    public void removeCaptainById(int id) {
        captainRepository.removeById(id);
    }

    public ArrayList<Captain> getCaptainsByCarPlate(String plate) {
        ArrayList<Captain> captainsWithCarPlate = new ArrayList<>();
        for (Captain captain : captainRepository.getAll()) {
            if (captain.getCar().getPlate().equalsIgnoreCase(plate)) {
                captainsWithCarPlate.add(captain);
            }
        }
        return captainsWithCarPlate;
    }

    public ArrayList<Captain> getCaptainsByCarBrand(String brand) {
        ArrayList<Captain> captainsWithCarBrand = new ArrayList<>();
        for (Captain captain : captainRepository.getAll()) {
            if (captain.getCar().getCarType().getBrand().equalsIgnoreCase(brand)) {
                captainsWithCarBrand.add(captain);
            }
        }
        return captainsWithCarBrand;
    }
}
