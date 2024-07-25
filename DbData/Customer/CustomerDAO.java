package carsharing.DbData.Customer;

import carsharing.DAO.DAO;
import carsharing.DbData.Company.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends DAO<Customer> {
    Connection con = null;

    public CustomerDAO(String URL) {
        try {
            con = DriverManager.getConnection(URL);
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void insert(Customer customer) {
        try {
            var st = con.prepareStatement("INSERT INTO CUSTOMER(NAME, RENTED_CAR_ID) VALUES(?, ?);");
            st.setString(1, customer.getName());
            st.setInt(2, customer.getRented_car_id());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertWithoutCarId(Customer customer) {
        try {
            var st = con.prepareStatement("INSERT INTO CUSTOMER(NAME) VALUES(?);");
            st.setString(1, customer.getName());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Customer getById(int id) {
        var customer = new Customer("");
        try {
            var st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CUSTOMER " +
                    "   WHERE ID=" + id + ";");
            rs.next();
            customer = new Customer(id, rs.getString(2), rs.getInt(3));
        } catch (SQLException ignored) {}
        return customer;
    }

    public void updateInfo(Customer customer, Customer newCustomer) {
        try {
            var st = con.prepareStatement("UPDATE CUSTOMER" +
                    "   SET name=?," +
                    "       rented_car_id=?" +
                    "   WHERE" +
                    "   name=?;");
            st.setString(1, newCustomer.getName());
            if (newCustomer.getRented_car_id() != 0)
            {
                st.setInt(2, newCustomer.getRented_car_id());
            } else {
                st.setNull(2, Types.INTEGER);
            }
            st.setString(3, customer.getName());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Customer> getAll() {
        var allCustomers = new ArrayList<Customer>();
        try {
            var st = con.createStatement();
            ResultSet rc = st.executeQuery("SELECT * FROM CUSTOMER;");
            while(rc.next()) {
                allCustomers.add(new Customer(rc.getInt(1),
                        rc.getString(2), rc.getInt(3)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allCustomers;
    }
}
