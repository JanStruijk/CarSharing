package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.DriverManager.*;

public class DbController {
    Connection con;

    public DbController(String URL, String DRIVER_NAME) {
        try {
            Class.forName(DRIVER_NAME);
            con = getConnection(URL);
            con.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException ignored){}
    }

    // Create tables
    public void createCompanyTableIfExists() {
        try {
            var st = con.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS COMPANY(" +
                    "ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(255) NOT NULL UNIQUE" +
                    ");");
        } catch (SQLException ignored){}
    }

    public void createCarTableIfExists() {
        try {
            var st = con.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS CAR(" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(255) NOT NULL UNIQUE," +
                    "COMPANY_ID INT NOT NULL," +
                    "CONSTRAINT comp_key FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(ID)" +
                    "ON DELETE CASCADE " +
                    "ON UPDATE CASCADE" +
                    ");");
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void createCustomerTableIfExists() {
        try {
            var st = con.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS CUSTOMER(" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(255) UNIQUE NOT NULL," +
                    "RENTED_CAR_ID INT DEFAULT NULL," +
                    "FOREIGN KEY(RENTED_CAR_ID) REFERENCES CAR(ID) " +
                    "ON UPDATE CASCADE " +
                    "ON DELETE CASCADE " +
                    ");");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void createAllTables() {
        createCompanyTableIfExists();
        createCarTableIfExists();
        createCustomerTableIfExists();
    }

    // Drop tables
    public void dropAllTables() {
        dropCustomerTable();
        dropCarTable();
        dropCompanyTable();
    }

    public void dropCompanyTable() {
        dropTable("COMPANY");
    }

    public void dropCarTable() {
        dropTable("CAR");
    }

    public void dropCustomerTable() {
        dropTable("CUSTOMER");
    }

    private void dropTable(String tableName) {
        try {
            var st = con.createStatement();
            st.execute("DROP TABLE IF EXISTS " + tableName + " ;");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void TruncateTable(String tableName) {
        try {
            var st = con.createStatement();
            st.execute("TRUNCATE TABLE " + tableName + " ;");
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void TruncateCompany() {
        TruncateTable("COMPANY");
    }

    public void TruncateCar() {
        TruncateTable("CAR");
    }
}