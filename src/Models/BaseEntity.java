package Models;

public class BaseEntity {
    private static int lastId = 0;

    private int id;

    public BaseEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public static int getNextId() {
        return ++lastId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
