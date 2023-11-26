package Services;

import Models.Rider;
import Repositories.GenericRepository;

import java.util.ArrayList;

public class RiderService {
    private GenericRepository<Rider> repo;

    public RiderService(GenericRepository<Rider> riderRepo) {
        this.repo = riderRepo;
    }


    public Rider getRiderById(int id) {
        ArrayList<Rider> riders = repo.getAll();
        for (Rider rider : riders) {
            if (rider.getId() == id) {
                return rider;
            }
        }
        return null; // Rider not found
    }

    public ArrayList<Rider> getAllRiders() {
        return repo.getAll();
    }



    public Rider getRiderByPhoneNumber(String phoneNumber) {
        ArrayList<Rider> riders = repo.getAll();
        for (Rider rider : riders) {
            if (rider.getPhoneNumber().equals(phoneNumber)) {
                return rider;
            }
        }
        return null; // Rider not found for the given phone number
    }
}

