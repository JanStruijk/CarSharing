package carsharing.DbData.Company;

public class Company {

    private int id;
    private String name;

    public Company(String name) {
        this.name = name;
    }

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return "id: " + id + " name: " + name;
    }

    @Override
    public String toString() {
        return name;
    }
}
