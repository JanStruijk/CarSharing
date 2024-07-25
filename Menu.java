package carsharing;

import carsharing.DbData.Car.Car;
import carsharing.DbData.Car.CarDAO;
import carsharing.DbData.Company.Company;
import carsharing.DbData.Company.CompanyDAO;
import carsharing.DbData.Customer.Customer;
import carsharing.DbData.Customer.CustomerDAO;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.*;

public class Menu{
    static CompanyDAO aCompanyDao;

    static void Connect(String URL) {
        aCompanyDao = new CompanyDAO(URL);
        CarProcessingMenu.Connect(URL);
        CustomerProcessMenu.Connect(URL);
    }


    static void Run() {
        var aScanner = new Scanner(in);
        int choice = 2;
        while (choice != 0)
        {
            out.println("" +
                    "1. Log in as a manager\n" +
                    "2. Log in as a customer\n" +
                    "3. Create a customer\n" +
                    "0. Exit");
            choice = aScanner.nextInt();
            switch (choice) {
                case 1 -> managerMenu();
                case 2 -> CustomerProcessMenu.customerGeneralMenu();
                case 3 -> CustomerProcessMenu.createCustomer();
                case 0 -> {
                }
                default -> out.println("Wrong choice");
            }
        }
    }

    static private void managerMenu() {
        var aScanner = new Scanner(in);
        int choice = 2;

        while (choice != 0)
        {
            out.println("" +
                    "1. Company list\n" +
                    "2. Create a company\n" +
                    "3. current condition\n" +
                    "0. Back");
            choice = aScanner.nextInt();
            switch (choice) {
                case 1 -> companyListManager();
                case 2 -> createCompany(aScanner);
                case 0 -> {
                }
                default -> out.println("Wrong choice");
            }
        }
    }

    public static void createCompany(Scanner aScanner) {
        out.print("\nEnter the company name:\n" +
                "> ");
        aScanner.nextLine();
        String companyName = aScanner.nextLine();
        aCompanyDao.insert(new Company(companyName));
        out.println("The company was created!\n");
    }

    public static void companyListManager() {
        var aScanner = new Scanner(in);
        var companyList = aCompanyDao.getAll();
        if (companyList.isEmpty()) {
            out.println("\nThe company list is empty!\n");
        } else {
            out.println("\nChoose the company:");
            companyList.forEach(x -> out.println(companyList.indexOf(x) + 1 + ". " + x.getName()));
            out.println("0. Back");

            int company_id = aScanner.nextInt();
            aScanner.nextLine();

            if (company_id != 0) {
                CarProcessingMenu.Run(companyList.get(company_id - 1));
            }

            out.println();
        }
    }

    public static int companyListOpinion() {
        int company_id = 0;
        var aScanner = new Scanner(in);
        var companyList = aCompanyDao.getAll();
        if (companyList.isEmpty()) {
            out.println("\nThe company list is empty!\n");
        } else {
            out.println("\nChoose the company:");
            companyList.forEach(x -> out.println(companyList.indexOf(x) + 1 + ". " + x.getName()));
            out.println("0. Back");

            company_id = aScanner.nextInt();
            aScanner.nextLine();

            out.println();
        }
        return companyList.get(company_id - 1).getId();
    }
}

class CustomerProcessMenu {
    static CustomerDAO customerDAO;

    static void Connect (String URL) {
        customerDAO = new CustomerDAO(URL);
    }

    static public void customerGeneralMenu() {
        var aScanner = new Scanner(in);
        int choice;
        var allCustomers = customerDAO.getAll();
        if (allCustomers.isEmpty()) {
            out.println("The customer list is empty!");
        } else {
            out.println("Customer list:");
            allCustomers.forEach(x -> out.println(x.getId() + ". " + x.getName()));
            out.println("0. Back");
            choice = aScanner.nextInt();
            aScanner.nextLine();
            if (choice > 0 && choice < allCustomers.size()) {
                customerMenu(allCustomers.get(choice - 1));
            }
        }
    }

    static private void customerMenu(Customer customer) {
        var scanner = new Scanner(in);
        int choice = -1;
        while (choice != 0)
        {
            out.println("1. Rent a car\n" +
                    "2. Return a rented car\n" +
                    "3. My rented car\n" +
                    "0. Back");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> RentCar(customer);
                case 2 -> ReturnRentedCar(customer);
                case 3 -> PrintRentedCar(customer);
                case 0 -> {
                }
                default -> out.println("There are no such option!\n");
            }
            customer = customerDAO.getById(customer.getId());
        }
    }

    static private void RentCar(Customer customer) {
        if (customer.getRented_car_id() == 0)
        {
            int company_id = Menu.companyListOpinion();
            if (company_id != 0) {
                var Car = CarProcessingMenu.carListOpinion(company_id, "Choose a car:");
                if (!Car.isEmpty())
                {
                    customerDAO.updateInfo(customer, new Customer(customer.getName(), Car.getId()));
                    out.println("You rented '" + Car.getName() + "'\n");
                }
            }
        } else {
            out.println("\nYou've already rented a car!\n");
        }

    }

    static private void ReturnRentedCar(Customer customer) {
        if (customer.getRented_car_id() == 0) {
            out.println("You didn't rent a car!");
        } else {
            customerDAO.updateInfo(customer, new Customer(customer.getName(), 0));
            out.println("You've returned a rented car!");
        }
    }

    static private void PrintRentedCar(Customer customer) {
        if (customer.getRented_car_id() == 0) {
            out.println("You didn't rent a car!");
        } else {
            out.println("Your rented car:");
            var car = CarProcessingMenu.aCarDAO.getById(customer.getRented_car_id());
            var comp = Menu.aCompanyDao.getById(car.getCompany_id());
            out.println(car.getName());
            out.println("Company:");
            out.println(comp.getName());
        }
    }

    static void createCustomer() {
        out.print("Enter the customer name:\n> ");
        var scanner = new Scanner(in);
        String name = scanner.nextLine();
        customerDAO.insertWithoutCarId(new Customer(name));
        out.println("The customer was added!");
    }

}

class CarProcessingMenu {
    static CarDAO aCarDAO;
    public static void Connect(String URl){
        aCarDAO = new CarDAO(URl);
    }

    public static void Run(Company subCompany) {
        int choice = -1;
        var aScanner = new Scanner(in);
        while (choice != 0)
        {
            out.println("'" + subCompany.getName() + "' " + "company");
            out.println("1. Car list\n" +
                    "2. Create a car\n" +
                    "0. Back");
            choice = aScanner.nextInt();
            aScanner.nextLine();
            switch (choice) {
                case 1 -> listByIdChoice(subCompany.getId(), "Car list: ");
                case 2 -> insertChoice(subCompany.getId());
                case 0 -> {
                }
                default -> out.println("There are no such opinion, try again");
            }
        }
    }
    private static List<Car> listByIdChoice(int company_id, String text) {
        var carList = aCarDAO.getAllByCompanyId(company_id);
        if (carList.isEmpty()) {
            out.println("The car list is empty!");
        } else {
            out.println(text);
            int i = 1;
            for (Car car : carList) {
                out.println(i++ + ". " + car.getName());
            }
        }
        return carList;
    }

    private static void insertChoice(int company_id) {
        var aScanner = new Scanner(in);
        out.print("Enter the car name:\n>");
        var name = aScanner.nextLine();
        aCarDAO.insert(new Car(name, company_id));
        out.println("The car was added!\n");
    }

    public static Car carListOpinion(int company_id, String text) {
        var carList = listByIdChoice(company_id, text);
        out.println("0. Back");
        var scanner = new Scanner(in);
        var choice = scanner.nextInt();
        if (choice != 0) {
            return carList.get(choice - 1);
        } else {
            return new Car("0", 0);
        }
    }
}