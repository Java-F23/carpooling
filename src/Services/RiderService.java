package Services;

import Models.Rider;
import Repositories.GenericRepository;

import java.util.ArrayList;

public class RiderService {
    private GenericRepository<Rider> repo;

    public RiderService() {
        this.repo = new GenericRepository<Rider>();
    }

    public void addRiders(Rider rider) {
        repo.addData(rider);
    }

    public void removeRiderById(int id) {
        repo.removeById(id);
    }

    public void updateRider(int id, Rider updatedRider) {
        repo.update(id, updatedRider);
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



    public ArrayList<Rider> searchRidersByCriteria(String searchCriteria) {
        ArrayList<Rider> matchingRiders = new ArrayList<>();
        for (Rider rider : repo.getAll()) {
            // Assuming Rider class has a method getFullName() to get full name for searching
            if (rider.getName().toLowerCase().contains(searchCriteria.toLowerCase())) {
                matchingRiders.add(rider);
            }
        }
        return matchingRiders;
    }
}

