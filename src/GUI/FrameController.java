    package GUI;

    import Models.Captain;
    import Models.Ride;
    import Models.Rider;
    import Repositories.GenericRepository;
    import Repositories.RideRepository;
    import Seeders.Seeder;
    import Services.*;
    import com.sun.tools.javac.Main;

    import javax.swing.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;

    public class FrameController implements LoginListener, LogoutListener {
        private JFrame currentFrame;
        private GenericRepository<Rider> riderRepo;
        private GenericRepository<Captain> captainRepo;
        private UserService userService;
        private RideRepository rideRepo;
        private RiderService rrs;
        private RideService rs;
        private  CaptainService cs;
        private PaymentService ps;

        public FrameController() {
            riderRepo = new GenericRepository<Rider>();
            captainRepo = new GenericRepository<Captain>();
            rideRepo = new RideRepository();

            Seeder.seedCaptains(captainRepo, 10);
            Seeder.seedRiders(riderRepo, 50);
            Seeder.seedRides(rideRepo, 20, riderRepo, captainRepo);

            userService = new UserService(riderRepo, captainRepo);
            rrs = new RiderService(riderRepo);
            rs = new RideService(rrs, rideRepo);
            cs = new CaptainService(captainRepo);
            ps = new PaymentService(rs, rrs);
            // Initialize the first frame (WelcomeComponent)
            Init();
        }

        private void Init() {
            currentFrame = new WelcomeComponent();
            currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            currentFrame.setVisible(true);

            // Add action listener for the login button in WelcomeComponent
            ((WelcomeComponent) currentFrame).setLoginButtonListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openLoginFrame();
                }
            });

            ((WelcomeComponent) currentFrame).setSignupButtonListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openSignupFrame();
                }
            });
        }

        private void openLoginFrame() {
            // Create a LoginFrame and cast it to the specific type
            LoginFrame loginFrame = new LoginFrame(userService, this);

            // Set up the frame and close the current frame
            setupAndCloseCurrentFrame(loginFrame);
        }


        private void setupAndCloseCurrentFrame(JFrame newFrame) {
            newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            newFrame.setVisible(true);

            // Close the current frame
            currentFrame.dispose();

            // Update the current frame reference
            currentFrame = newFrame;
        }


        @Override
        public void onLoginResult(int userType, String phoneNumber) {
            // Handle login result here (userType: 1 for Rider, 2 for Captain, 0 for login failure)
            if (userType == 1) {
                // Rider login
                openRiderFrame(phoneNumber);
            } else if (userType == 2) {
                // Captain login
                openCaptainFrame(phoneNumber);
            } else {
                // Show login failure message or handle accordingly
                JOptionPane.showMessageDialog(currentFrame, "Login failed. Invalid phone number or password.");
            }
        }
        private void openRiderFrame(String phoneNumber) {
            MainFrame riderFrame = new MainFrame(rrs, rs, phoneNumber, ps, this::onLogout);
            setupAndCloseCurrentFrame(riderFrame);
        }

        private void openCaptainFrame(String phoneNumber) {
            MainFrame captainFrame = new MainFrame(cs, rs, phoneNumber, ps, this::onLogout);
            setupAndCloseCurrentFrame(captainFrame);
        }

        @Override
        public void onLogout() {
            // Close the current frame
            currentFrame.dispose();
            Init();
        }
        private void openSignupFrame() {
            // Create a SignupFrame and cast it to the specific type
            SignUpFrame signupFrame = new SignUpFrame(userService);

            // Set up the frame and close the current frame
            signupFrame.setVisible(true);
        }
    }






