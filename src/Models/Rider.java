package Models;

public class Rider extends  BaseUser{
    Location riderLoc;
    public Rider(int id, String name, String email, String phoneNumber, Location riderLoc) {
        super(id, name, email, phoneNumber);
        this.riderLoc = riderLoc;
    }
}
