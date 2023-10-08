package Models;

public class BaseUser extends BaseEntity {

    private String name;
    private String email;
    private String phoneNumber;

    public BaseUser(int id, String name, String email, String phoneNumber) {
        super(id);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
