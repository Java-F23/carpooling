package Controller;

import GUI.FrameController;
    import Repositories.GenericRepository;
import Repositories.RideRepository;
import Seeders.Seeder;
import Services.*;
import Models.Captain;
import Models.Rider;

public class Controller {
    private FrameController frameController;

    public Controller() {
        // Create repositories and seed data
        GenericRepository<Rider> riderRepo = new GenericRepository<>();
        GenericRepository<Captain> captainRepo = new GenericRepository<>();
        RideRepository rideRepo = new RideRepository();

        Seeder.seedCaptains(captainRepo, 10);
        Seeder.seedRiders(riderRepo, 50);
        Seeder.seedRides(rideRepo, 20, riderRepo, captainRepo);

        // Create services
        UserService userService = new UserService(riderRepo, captainRepo);
        RiderService rrs = new RiderService(riderRepo);
        RideService rs = new RideService(rrs, rideRepo);
        CaptainService cs = new CaptainService(captainRepo);
        PaymentService ps = new PaymentService(rs, rrs);

        // Create FrameController and pass the services
        frameController = new FrameController(userService, rrs, rs, cs, ps);
    }

}
