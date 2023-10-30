package Services;

import Models.*;
import Repositories.GenericRepository;

public class UserService {
    private GenericRepository<Rider> riderRepository;
    private GenericRepository<Captain> captainRepository;

    public UserService(GenericRepository<Rider> riderRepository, GenericRepository<Captain> captainRepository) {
        this.riderRepository = riderRepository;
        this.captainRepository = captainRepository;
    }

    public int login(String phoneNumber, String password) {
        for (Rider rider : riderRepository.getAll()) {
            if (rider.getPhoneNumber().equals(phoneNumber) && rider.getPassword().equals(password)) {
                return 1; // Rider login successful
            }
        }

        for (Captain captain : captainRepository.getAll()) {
            if (captain.getPhoneNumber().equals(phoneNumber) && captain.getPassword().equals(password)) {
                return 2; // Captain login successful
            }
        }
        return 0; // Login failed
    }

    public boolean doesUserExist(String phoneNumber) {
        for (Rider rider : riderRepository.getAll()) {
            if (rider.getPhoneNumber().equals(phoneNumber)) {
                return true; // Rider with the given phone number already exists
            }
        }

        for (Captain captain : captainRepository.getAll()) {
            if (captain.getPhoneNumber().equals(phoneNumber)) {
                return true; // Captain with the given phone number already exists
            }
        }
        return false; // No user with the given phone number exists
    }

    public void signUp(String name, String email, String phoneNumber, String password, Location riderLoc) {
        // Check if the rider with the given phone number already exists
        if (!doesUserExist(phoneNumber)) {
            Rider newRider = new Rider(BaseEntity.getNextId(), name, email, phoneNumber, riderLoc, password);
            riderRepository.addData(newRider);
        } else {
            System.out.println("Rider with the given phone number already exists.");
        }
    }

    public void signUp(String name, String email, String phoneNumber, String password, Car car) {
        // Check if the captain with the given phone number already exists
        if (!doesUserExist(phoneNumber)) {
            Captain newCaptain = new Captain(BaseEntity.getNextId(), name, email, phoneNumber, car, password);
            captainRepository.addData(newCaptain);
        } else {
            System.out.println("Captain with the given phone number already exists.");
        }
    }
}
