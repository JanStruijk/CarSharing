package carsharing.DbData.Car;

public class Car {
    private int id = -1;
    private String name;
    private int company_id;

    public Car(int id, String name, int company_id) {
        this.company_id = company_id;
        this.id = id;
        this.name = name;
    }

    public Car(String name, int company_id) {
        this.name = name;
        this.company_id = company_id;
    }

    public boolean isEmpty() {
        return company_id == 0;
    }

    public int getCompany_id() {
        return company_id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return "id: " + id + " name" + name + " company_id " + company_id;
    }

    @Override
    public String toString(){
        return name;
    }
}

