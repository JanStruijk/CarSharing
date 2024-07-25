package carsharing.DbData.Company;

import carsharing.DAO.DAO;
import carsharing.DbData.Car.Car;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class CompanyDAO extends DAO<Company> {
    Connection con = null;

    public CompanyDAO(String URL) {
        try {
            con = DriverManager.getConnection(URL);
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println("Error while connecting");
        }
    }

    @Override
    public void insert(Company aCompany) {
        try {
            PreparedStatement st = con.prepareStatement("INSERT INTO COMPANY(NAME) values(?);");
            st.setString(1, aCompany.getName());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Company getById(int id) {
        var foundComp = new Company("");
        try {
            var st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM COMPANY " +
                    "   WHERE ID=" + id + ";");
            rs.next();
            foundComp.setId(rs.getInt(1));
            foundComp.setName(rs.getString(2));
        } catch (SQLException ignored) {}
        return foundComp;
    }

    @Override
    public List<Company> getAll() {
        var allCompanies = new ArrayList<Company>();
        try {
            var st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM COMPANY;");
            while (rs.next()) {
                allCompanies.add(new Company(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ignored) {}
        return allCompanies;
    }
}
