package carsharing;

import carsharing.DbData.Car.CarDAO;
import carsharing.DbData.Company.CompanyDAO;

import static java.lang.System.*;

public class Main {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL_WITHOUT_NAME = "jdbc:h2:./src/carsharing/db/";
    static final String DEFAULT_DB_NAME = "test.db";

    public static String getDBName(String[] args) {
        final String dbNameKey = "-databaseFileName";
        String res = DEFAULT_DB_NAME;
        for (int i = 0; i < args.length; ++i) {
            if (dbNameKey.equals(args[i]) && i + 1 < args.length) {
                res = args[i + 1];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String DB_URL = DB_URL_WITHOUT_NAME + getDBName(args);

        var Db = new DbController(DB_URL, JDBC_DRIVER);

//        Db.dropAllTables();
        Db.createAllTables();

        Menu.Connect(DB_URL);
        Menu.Run();
    }

    public static void main_(String[] args) {
        String DB_URL = DB_URL_WITHOUT_NAME + getDBName(args);

        var Db = new DbController(DB_URL, JDBC_DRIVER);

        Db.dropCarTable();
        Db.dropCompanyTable();

        Db.createCompanyTableIfExists();
        Db.createCarTableIfExists();

        var compDao = new CompanyDAO(DB_URL);
        var carDao = new CarDAO(DB_URL);
        out.println("after all:");
        carDao.getAll().forEach(x -> out.println(x.getInfo()));
        compDao.getAll().forEach(x -> out.println(x.getInfo()));
    }
}