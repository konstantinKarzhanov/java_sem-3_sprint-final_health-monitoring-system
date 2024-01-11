// Import required packages
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.InputMismatchException;

// Import custom packages
import dao.*;
import model.*;
import views.*;

// Define class
public class HealthMonitoringApp {
    // Define attributes
    private static User loggedUser = null;
    private static int optionChoice = 0;

    public static void main(String[] args) {
        // Set scanner
        Scanner inScanner = new Scanner(System.in);

        // Define required menu
        Menu welcomeMenu = new Menu(
            "Welcome to Health Monitoring App", 
            new String[] { 
                "Register a new user", 
                "Log in the user"
            }
        );

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

        Menu doctorPortalMenu = new Menu(
            "Doctor Portal",
            new String[] { 
                "Get doctor by id",
                "Get patients associated with a doctor",
                "Get health data for a specific patient"
            }
        );

        // Display welcome menu
        welcomeMenu.getFormattedMenu(false);

        try {
            optionChoice = inScanner.nextInt();
            inScanner.nextLine();

            if (optionChoice != 0) welcomeMenu.getFormattedHeader(optionChoice - 1);

            switch (optionChoice) {
                case 1:
                    // Register a new user
                    UserManagementView.registerUser(inScanner);
        
                    break;
                case 2:
                    // Log in a user
                    if (UserManagementView.loginUser(inScanner)) {
                        loggedUser = UserManagementView.getLoggedUser();
                    }

                    break;
                case 0:
                default:                    
                    break;
            }

            // Check if user is logged in
            if (loggedUser == null) {
                System.out.println("To use the app login with your credentials\n");
                inScanner.close();

                return;
            };

            // Display main menu
            mainMenu.getFormattedMenu(false);

            optionChoice = inScanner.nextInt();
            inScanner.nextLine();

            if (optionChoice != 0) mainMenu.getFormattedHeader(optionChoice - 1);

            String message = null;

            switch (optionChoice) {
                case 1:
                    // Add health data
                    HealthData healthData = HealthDataView.addHealthData(inScanner);
                    
                    // Create recommendations
                    List<String> recommendationList = Recommendation.generateRecommendationList(healthData);

                    message = 
                        HealthDataDao.createHealthData(loggedUser, healthData) && RecommendationDao.createRecommendation(loggedUser, recommendationList) 
                        ? "\nHealth data with recommendations for \"%s %s\" was saved successfully\n\n" 
                        : "\nSomething went wrong. Health data for \"%s %s\" wasn't saved\n";
                    
                    System.out.printf(message, loggedUser.getFirstName(), loggedUser.getLastName());

                    break;
                case 2:
                    // Generate recommendations
                    System.out.printf("\nRecommendations for: \"%s %s\"\n", loggedUser.getFirstName(), loggedUser.getLastName());

                    for (String recommendation : RecommendationDao.getRecommendation(loggedUser)) {
                        System.out.printf("\n%s\n", recommendation);
                    }

                    System.out.println("\n");

                    break;
                case 3:
                    // Add a medicine reminder
                    MedicineReminder medicineReminder = MedicineReminderView.addMedicineReminder(inScanner);

                    // Create medicine reminder
                    message = 
                        MedicineReminderDao.createReminder(loggedUser, medicineReminder)
                        ? "\nMedicine reminder for \"%s %s\" was saved successfully\n\n" 
                        : "\nSomething went wrong. Medicine reminder for \"%s %s\" wasn't saved\n";
                    
                    System.out.printf(message, loggedUser.getFirstName(), loggedUser.getLastName());

                    break;
                case 4:
                    List<MedicineReminder> reminderList = null;

                    // Get reminders based on condition
                    if (loggedUser.isDoctor()) {
                        // Get reminders for a specific user
                        System.out.println("Enter the user id to get medicine reminders for a specific user:");
                        int userId = inScanner.nextInt();
                        inScanner.nextLine();

                        reminderList = MedicineReminderDao.getRemindersByUserId(userId);
                    } else {
                        // Get personal reminders
                        reminderList = MedicineReminderDao.getRemindersByUser(loggedUser);
                    }

                    for (int i = 1; i <= reminderList.size(); i++) {
                        MedicineReminder reminder = reminderList.get(i - 1);

                        String reminderMedicineName = reminder.getMedicineName();
                        String reminderDosage = reminder.getDosage();
                        String reminderSchedule = reminder.getSchedule();
                        LocalDate reminderStartDate = reminder.getStartDate();
                        LocalDate reminderEndDate = reminder.getEndDate();
                        
                        System.out.printf("\n%d. %s: (%s; %s) - %s / %s\n", i, reminderMedicineName, reminderDosage, reminderSchedule, reminderStartDate, reminderEndDate);
                    }
                    System.out.println("");
    
                    break;
                case 5:
                    // Get DUE reminders for a specific user
                    List<MedicineReminder> dueReminderList = null;

                    // Get DUE reminders based on condition
                    if (loggedUser.isDoctor()) {
                        // Get DUE reminders for a specific user
                        System.out.println("Enter the user id to get medicine DUE reminders for a specific user:");
                        int userId = inScanner.nextInt();
                        inScanner.nextLine();

                        dueReminderList = MedicineReminderDao.getDueReminders(userId);
                    } else {
                        // Get personal DUE reminders
                        dueReminderList = MedicineReminderDao.getDueReminders(loggedUser);
                    }

                    if (dueReminderList.size() == 0) {
                        System.out.println("There are no due reminders yet\n");
                        break;
                    }

                    for (int i = 1; i <= dueReminderList.size(); i++) {
                        MedicineReminder reminder = dueReminderList.get(i - 1);

                        String reminderMedicineName = reminder.getMedicineName();
                        String reminderDosage = reminder.getDosage();
                        String reminderSchedule = reminder.getSchedule();
                        LocalDate reminderStartDate = reminder.getStartDate();
                        LocalDate reminderEndDate = reminder.getEndDate();
                        
                        System.out.printf("\n%d. %s: (%s; %s) - %s / %s\n", i, reminderMedicineName, reminderDosage, reminderSchedule, reminderStartDate, reminderEndDate);
                    }
                    System.out.println("");
    
                    break;
                case 6:
                    // Test doctor portal
                    if (!loggedUser.isDoctor()) {
                        System.out.println("\nDoctor portal functionality available only for doctors");
                        System.out.println("Try to login as a doctor\n");
                        break;
                    };
                    
                    // Display Doctor Portal menu
                    doctorPortalMenu.getFormattedMenu(false);
                    
                    optionChoice = inScanner.nextInt();
                    inScanner.nextLine();

                    if (optionChoice != 0) doctorPortalMenu.getFormattedHeader(optionChoice - 1);

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

                    break;
                case 0:
                default:
                    break;
            }
            
            System.out.println("See you next time!");
        } catch (InputMismatchException e) {
            System.out.println("Something went wrong. Please try again using correct data for inputs\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inScanner.close();
        }
    }
}
