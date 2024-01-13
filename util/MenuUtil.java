package util;

// Import required packages
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Import custom packages
import dao.DoctorPortalDao;

import model.Doctor;
import model.HealthData;
import model.Menu;
import model.User;

public class MenuUtil {
	// Define attributes
	public static final Menu WELCOME_MENU = new Menu(
		"Welcome to Health Monitoring App", 
		new String[] { 
			"Register a new user", 
			"Log in the user"
		}
	);

	public static final Menu MAIN_MENU = new Menu(
		"Health Monitoring App",
		new String[] {
			"Add health data",
			"Generate recommendations",
			"Add a medicine reminder",
			"Get reminders for a specific user",
			"Get due reminders for a specific user",
			"Test doctor portal"
		}
	);

	private static final Menu DOCTOR_PORTAL_MENU = new Menu(
			"Doctor Portal",
			new String[] { 
				"Get doctor by id",
				"Get patients associated with a doctor",
				"Get health data for a specific patient"
			}
		);
	
    // Define method to process menu choice   
    public static int handleMenuChoice(Menu menu, Scanner inScanner) throws InputMismatchException {
        int optionChoice = 0;

        // Display menu
        menu.displayMenu();

        optionChoice = inScanner.nextInt();
        inScanner.nextLine();

        if (optionChoice != 0) menu.displayHeader(optionChoice - 1);

        return optionChoice;
    }

    // Define method to handle welcome menu choice
    public static boolean processWelcomeMenuChoice(int optionChoice, Scanner inScanner) throws InputMismatchException, SQLException {
        boolean userIsLoggedIn = false;

		switch (optionChoice) {
            case 1:
                // Register a new user
                UserManagementUtil.registerUser(inScanner);

                break;
            case 2:
                // Log in a user
				userIsLoggedIn = UserManagementUtil.loginUser(inScanner);

                break;
            case 0:
            default:                    
                break;
        }

        return userIsLoggedIn;
    }

    // Define method to handle doctor portal menu choice
    private static void processDoctorPortalMenuChoice(int optionChoice, Scanner inScanner) throws InputMismatchException, SQLException {
        int doctorId = 0;

        switch (optionChoice) {
            case 1:
                // Get doctor by id
                System.out.println("Enter the doctor id:");
                doctorId = inScanner.nextInt();
                inScanner.nextLine();
                
                Doctor doctor = DoctorPortalDao.getDoctorById(doctorId);
                
                if (doctor.getFirstName() == null || doctor.getMedicalLicenseNumber() == null) {
                    System.out.printf("Doctor with id \"%d\" wasn't found\n", doctorId);
                    break;
                }

                // Display doctor's info
                System.out.println("");
                System.out.printf("Medical License Number: \"%s\"\n", doctor.getMedicalLicenseNumber());
                System.out.printf("First Name: \"%s\"\n", doctor.getFirstName());
                System.out.printf("Last Name: \"%s\"\n", doctor.getLastName());
                System.out.printf("Specialization: \"%s\"\n", doctor.getSpecialization());
                System.out.printf("Experience years: \"%.1f\"\n", doctor.getExperienceYears());
                System.out.printf("Day of Birth: \"%s\"\n", doctor.getBirthDate());
                System.out.printf("Gender: \"%s\"\n", doctor.getGender() == 'M' ? "Male" : doctor.getGender() == 'F' ? "Female" : Character.toString(doctor.getGender()));
                System.out.printf("Email: \"%s\"\n", doctor.getEmail());
                System.out.printf("Hashed Password: \"%s\"\n", doctor.getPassword());
                System.out.println("");

                break;
            case 2:
                // Get patients associated with a doctor
                System.out.println("Enter the doctor id:");
                doctorId = inScanner.nextInt();
                inScanner.nextLine();
                
                List<User> patientList = DoctorPortalDao.getPatientsByDoctorId(doctorId);

                if (patientList.size() == 0) {
                    System.out.printf("Doctor with id \"%d\" does not have patients yet\n", doctorId);
                    break;
                } 

                System.out.printf("Doctor \"%d\", patients:\n", doctorId);

                for (User patient : patientList) {
                    // Display patient's info
                    System.out.println("Patient:");
                    System.out.printf("First Name: \"%s\"\n", patient.getFirstName());
                    System.out.printf("Last Name: \"%s\"\n", patient.getLastName());
                    System.out.printf("Day of Birth: \"%s\"\n", patient.getBirthDate());
                    System.out.printf("Gender: \"%s\"\n", patient.getGender() == 'M' ? "Male" : patient.getGender() == 'F' ? "Female" : Character.toString(patient.getGender()));
                    System.out.printf("Email: \"%s\"\n", patient.getEmail());
                    System.out.printf("Hashed Password: \"%s\"\n", patient.getPassword());
                    System.out.println("");
                }

                break;
            case 3:
                // Get health data for a specific patient
                System.out.println("Enter the patient id:");
                int patientId = inScanner.nextInt();
                inScanner.nextLine();

                List<HealthData> healthDataList = DoctorPortalDao.getHealthDataByPatientId(patientId);

                System.out.printf("Patient: \"%d\"\n", patientId);
                for (HealthData hData : healthDataList) {
                    // Display patient's info
                    System.out.printf("Weight: \"%s\"\n", hData.getWeight());
                    System.out.printf("Height: \"%s\"\n", hData.getHeight());
                    System.out.printf("Steps: \"%s\"\n", hData.getSteps());
                    System.out.printf("Heart Rate: \"%s\"\n", hData.getHeartRate());
                    System.out.printf("Date: \"%s\"\n", hData.getDate());
                    System.out.println("");
                }

                break;
            case 0:
            default:
                break;
        }
    }

	public static void processDoctorPortal(Scanner inScanner) throws InputMismatchException, SQLException {
		if (!UserManagementUtil.getLoggedUser().isDoctor()) {
			System.out.println("\nDoctor portal functionality available only for doctors");
			System.out.println("Try to login as a doctor\n");
			return;
		};
		
		// Display Doctor Portal menu
		int optionChoice = handleMenuChoice(DOCTOR_PORTAL_MENU, inScanner);
		processDoctorPortalMenuChoice(optionChoice, inScanner);
	}

    // Define method to handle main menu choice
    public static void processMainMenuChoice(int optionChoice, Scanner inScanner) throws InputMismatchException, SQLException {
        switch (optionChoice) {
            case 1:
                HealthDataAndRecommendationUtil.processHealthDataAndRecommendationList(inScanner);
                break;
            case 2:
                HealthDataAndRecommendationUtil.displayRecommendationList();
                break;
            case 3:
                MedicineReminderUtil.processMedicineReminder(inScanner);
                break;
            case 4:
                MedicineReminderUtil.displayMedicineReminderList(inScanner, false);
                break;
            case 5:
                MedicineReminderUtil.displayMedicineReminderList(inScanner, true);
                break;
            case 6:
                MenuUtil.processDoctorPortal(inScanner);
                break;
            case 0:
            default:
                break;
        }
    }
}
