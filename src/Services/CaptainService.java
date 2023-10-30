package Services;

import Models.Captain;
import Models.Car;
import Models.Ride;
import Repositories.GenericRepository;

import java.util.ArrayList;

public class CaptainService {
    private GenericRepository<Captain> captainRepository;

    public CaptainService(GenericRepository<Captain> captainRepository) {
        this.captainRepository = captainRepository;
    }

    public void addCaptain(int id, String name, String email, String phoneNumber, Car car, String password) {
        Captain captain = new Captain(id, name, email, phoneNumber, car, password);
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

    public Captain getCaptainByPhoneNumber(String phoneNumber) {
        ArrayList<Captain> captains = captainRepository.getAll(); // Assuming captainRepository is your GenericRepository for Captain objects
        for (Captain captain : captains) {
            if (captain.getPhoneNumber().equals(phoneNumber)) {
                return captain; // Found the captain with the matching phone number
            }
        }
        return null; // Captain not found
    }


}
