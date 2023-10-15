package Models;
public class Payment {
    private String paymentType;

    public Payment(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }
}