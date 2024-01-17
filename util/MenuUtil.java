package util;

// Import required packages
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

// Import custom packages
import model.Menu;

public class MenuUtil {
	private static int chosenOption = 0;

	public static int getChosenOption() {
		return chosenOption;
	}
	
    // Define method to process menu choice   
    private static void processMenu(Menu menu, Scanner inScanner) throws InputMismatchException {
        menu.displayMenu();

        chosenOption = inScanner.nextInt();
        inScanner.nextLine();

        if (chosenOption != 0) menu.displayHeader(chosenOption - 1);
    }

    // Define method to handle welcome menu choice
    public static boolean processWelcomeMenu(Scanner inScanner) throws InputMismatchException, SQLException {
        boolean userIsLoggedIn = false;
		Menu welcomeMenu = new Menu(
			"Welcome to Health Monitoring App", 
			new String[] { 
				"Register a new user", 
				"Log in the user"
			}
		);

		processMenu(welcomeMenu, inScanner);

		switch (chosenOption) {
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
    private static void processDoctorPortalMenu(Scanner inScanner) throws InputMismatchException, SQLException {
		Menu doctorPortalMenu = new Menu(
			"Doctor Portal",
			new String[] { 
				"Get doctor by id",
				"Get patients associated with a doctor",
				"Get health data for a specific patient"
			}
		);

		processMenu(doctorPortalMenu, inScanner);
		
        switch (chosenOption) {
            case 1:
                DoctorPortalUtil.displayDoctorsInfo(inScanner);
                
                break;
            case 2:
                DoctorPortalUtil.displayPatientsList(inScanner);

                break;
            case 3:
                DoctorPortalUtil.displayPatientHealthData(inScanner);

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
		processDoctorPortalMenu(inScanner);
	}

    // Define method to handle main menu choice
    public static void processMainMenu(Scanner inScanner) throws InputMismatchException, SQLException {
		Menu mainMenu = new Menu(
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

		processMenu(mainMenu, inScanner);
		
		switch (chosenOption) {
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
