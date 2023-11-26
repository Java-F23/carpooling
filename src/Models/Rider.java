package Models;

public class Rider extends  BaseUser{
    Location riderLoc;
    public Rider(int id, String name, String email, String phoneNumber, Location riderLoc, String password) {
        super(id, name, email, phoneNumber, password);
        this.riderLoc = riderLoc;
    }
    public Location getRiderLoc(){
        return riderLoc;
    }


}
