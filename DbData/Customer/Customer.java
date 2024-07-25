package carsharing.DbData.Customer;

public class Customer {
    private int id;
    private final String name;
    private int rented_car_id;

    public Customer(int id, String name, int rented_car_id) {
        this.id = id;
        this.name = name;
        this.rented_car_id = rented_car_id;
    }


    public Customer(String name, int rented_car_id) {
        this.name = name;
        this.rented_car_id = rented_car_id;
        this.id = 0;
    }

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getRented_car_id() {
        return rented_car_id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id: " + id + " name: " + name + " rented_car_id: " + rented_car_id;
    }
}
