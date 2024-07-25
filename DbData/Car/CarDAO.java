package carsharing.DbData.Car;

import carsharing.DAO.DAO;
import carsharing.DbData.Company.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO extends DAO<Car> {
    Connection con = null;

    public CarDAO(String URL) {
        try {
            con = DriverManager.getConnection(URL);
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println("Error while connecting");
        }
    }

    @Override
    public void insert(Car aCar) {
        try {
            PreparedStatement st = con.prepareStatement("INSERT INTO CAR(NAME, COMPANY_ID) values(?, ?);");
            st.setString(1, aCar.getName());
            st.setInt(2, aCar.getCompany_id());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Car> getAll() {
        var allCompanies = new ArrayList<Car>();
        try {
            var st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CAR;");
            while (rs.next()) {
                allCompanies.add(new Car(rs.getInt(1),
                        rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException ignored) {}
        return allCompanies;
    }

    public Car getById(int id) {
        var foundCar = new Car("", 0);
        try {
            var st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CAR " +
                    "   WHERE ID=" + id + ";");
            rs.next();
            foundCar.setId(rs.getInt(1));
            foundCar.setName(rs.getString(2));
            foundCar.setCompany_id(rs.getInt(3));
        } catch (SQLException ignored) {}
        return foundCar;
    }

    public List<Car> getAllByCompanyId(int company_id) {
        var allCompanies = new ArrayList<Car>();
        try {
            var st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CAR " +
                    "WHERE COMPANY_ID=" + company_id +" ;");
            while (rs.next()) {
                allCompanies.add(new Car(rs.getInt(1),
                        rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException ignored) {}
        return allCompanies;
    }
}

