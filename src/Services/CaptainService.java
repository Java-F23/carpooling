package Services;

import Models.Captain;
import Models.Car;
import Repositories.GenericRepository;

import java.util.ArrayList;

public class CaptainService {
    private final GenericRepository<Captain> captainRepository;

    public CaptainService(GenericRepository<Captain> captainRepository) {
        this.captainRepository = captainRepository;
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
