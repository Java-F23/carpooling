package Services;

import Models.Payment;
import Models.Ride;
import Models.Rider;

public class PaymentService {
    private RideService rideService;
    private RiderService riderService;

    public PaymentService(RideService rideService, RiderService riderServiec) {
        this.rideService = rideService;
        this.riderService = riderServiec;
    }

    public boolean pay(int rideId, int riderId, String paymentType) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = riderService.getRiderById(riderId);

        if (ride != null && rider != null) {
            // Check if the ride and rider exist
            Payment payment = new Payment(paymentType);

            // Process payment logic (for example, check payment with external payment provider)
            boolean paymentSuccessful = processPayment(payment);

            if (paymentSuccessful) {
                // Set the payment status to true for the corresponding rider
                int riderIndex = ride.getRiders().indexOf(rider);
                if (riderIndex >= 0 && riderIndex < ride.getPayments().size()) {
                    ride.getPayments().set(riderIndex, true);
                }

                // Perform actions for a successful payment (if needed)
                return true;
            } else {
                // Payment failed, handle accordingly
                return false;
            }
        } else {
            // Ride or rider not found
            return false;
        }
    }

    private boolean processPayment(Payment payment) {
        // Logic for processing payment (e.g., connecting to a payment gateway)
        // For demonstration purposes, assume payment is always successful
        return true;
    }
}
