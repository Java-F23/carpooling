package Repositories;

import Models.BaseEntity;

import java.util.ArrayList;

public class GenericRepository<T extends BaseEntity> {
    ArrayList<T> data = new ArrayList<T>();

    public ArrayList<T> getAll() {
        return data;
    }
    public void addData(T value){
        data.add(value);
    }
    public void removeById(int id) {
        T objectToRemove = null;
        for (T obj : data) {
            // Assuming getId() is the method to get the ID of the object
            if (obj !=null && obj.getId() == id) {
                objectToRemove = obj;
                break;
            }
        }
        if (objectToRemove != null) {
            data.remove(objectToRemove);
            System.out.println("Object with ID " + id + " removed successfully.");
        } else {
            System.out.println("Object with ID " + id + " not found.");
        }
    }

    public void update(int id, T updatedEntity) {
        for (int i = 0; i < data.size(); i++) {
            T entity = data.get(i);
            if ( entity.getId() == id) {
                data.set(i, updatedEntity);
                System.out.println("Object with ID " + id + " updated successfully.");
                return;
            }
        }
        System.out.println("Object with ID " + id + " not found.");
    }
}
