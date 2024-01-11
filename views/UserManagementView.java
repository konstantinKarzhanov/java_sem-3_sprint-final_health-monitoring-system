package views;

// Import required packages
import java.util.Scanner;
import java.time.LocalDate;

// Import custom packages
import model.*;
import dao.UserDao;
import dao.DoctorPortalDao;

// Define class
public class UserManagementView {
    public static User loggedUser = null;

    // Define getter methods
    public static User getLoggedUser() {
        return loggedUser;
    }

    // Define method to register new user
    public static void registerUser(Scanner inScanner) throws Exception {
        // Define required variables
        String firstName = null;
        String lastName = null;
        LocalDate birthDate = null;
        char gender = '\0';
        String email = null;
        String password = null;
        boolean isDoctor = false;
        String medicalLicenseNumber = null;
        String specialization = null;
        double experienceYears = 0;
        User user = null;
        Doctor doctor = null;

        // Assign variables from user input
        System.out.println("Enter First Name:");
        firstName = inScanner.nextLine();

        System.out.println("Enter Last Name:");
        lastName = inScanner.nextLine();

        System.out.println("Enter the birth year:");
        int birthYear = inScanner.nextInt();
        inScanner.nextLine();

        System.out.println("Enter the birth month:");
        int birthMonth = inScanner.nextInt();
        inScanner.nextLine();

        System.out.println("Enter the birth day:");
        int birthDay = inScanner.nextInt();
        inScanner.nextLine();

        // Set birthDate
        birthDate = LocalDate.of(birthYear, birthMonth, birthDay);

        System.out.println("Enter gender:");
        gender = inScanner.next().charAt(0);
        inScanner.nextLine();

        System.out.println("Enter email address:");
        email = inScanner.next();
        inScanner.nextLine();

        System.out.println("Enter password for Health Monitoring App:");
        password = inScanner.nextLine();

        System.out.println("Are you a doctor? (Yes / No):");
        isDoctor = inScanner.next().toLowerCase().charAt(0) == 'y';
        inScanner.nextLine();

        // Assign variables from doctor input
        if (isDoctor) {
            System.out.println("Enter your medical license number:");
            medicalLicenseNumber = inScanner.next();
            inScanner.nextLine();

            System.out.println("Enter your specialization:");
            specialization = inScanner.nextLine();

            System.out.println("Enter the number of years of experience:");
            experienceYears = inScanner.nextDouble();
            inScanner.nextLine();
        }

        // Create new user/doctor
        user = new User(firstName, lastName, birthDate, gender, email, password, isDoctor);

        // Add user to the database
        UserDao.createUser(user);

        // Create new doctor
        if (isDoctor) {
            doctor = new Doctor(user, medicalLicenseNumber, specialization, experienceYears);
            DoctorPortalDao.createDoctor(doctor);
        }

        System.out.printf("\n\"%s %s\" is now in the system.\n", user.getFirstName(), user.getLastName());
    }

    // Define method to login the user
    public static boolean loginUser(Scanner inScanner) throws Exception {
        boolean flag = false;

        System.out.println("Enter your email:");
        String email = inScanner.next();
        inScanner.nextLine();

        System.out.println("Enter your password:");
        String password = inScanner.nextLine();

        User user = UserDao.getUserByEmail(email);

        if (user.getFirstName() == null) {
            System.out.printf("User with email \"%s\" wasn't found\n", email);
        } else if (UserDao.verifyPassword(email, password)) {
            loggedUser = user;
            flag = true;

            System.out.printf("Logged in as: \"%s %s\"\n", loggedUser.getFirstName(), loggedUser.getLastName());
        } else {
            System.out.println("Incorrect password. Try again");
        }

        return flag;
    }
}