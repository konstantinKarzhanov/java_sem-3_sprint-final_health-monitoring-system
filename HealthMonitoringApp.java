// Import required packages
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Define class
public class HealthMonitoringApp {
    private static UserDao uDAO = new UserDao();
    private static DoctorPortalDao dpDAO = new DoctorPortalDao();
    private static HealthDataDao hDAO = new HealthDataDao();
    private static RecommendationSystem rSystem = new RecommendationSystem();
    private static RecommendationDao rDAO = new RecommendationDao();
    private static MedicineReminderDao mrDAO = new MedicineReminderDao();
    private static User loggedInUser = null;
    private static boolean loggedInUserIsDoctor = false;

    // Define method to generate header   
    public static void generateHeader(String header) {
        System.out.println("");
        System.out.printf("%s\n", "*".repeat(header.length() + 1));
        System.out.println(header);
        System.out.printf("%s\n", "*".repeat(header.length() + 1));
    }

    // Define method to generate menu
    public static void generateMenu(String header, String[] menuArr, boolean goback) {
        generateHeader(header);

        System.out.println("Choose the option:\n");
        for (int i = 1; i <= menuArr.length; i++) {
            System.out.printf("%d. %s\n", i, menuArr[i - 1]);
        }

        if (goback) {
            System.out.println("");
            System.out.println("(Go back: -1)");
            System.out.println("");
        }

        System.out.println("0. Exit\n");
    }

    // Define method to register user
    public static User registerUser(Scanner inScanner) throws InputMismatchException, Exception {
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
        // inScanner.nextLine();

        System.out.println("Enter Last Name:");
        lastName = inScanner.nextLine();
        // inScanner.nextLine();

        System.out.println("Enter the birth year:");
        int birthYear = inScanner.nextInt();
        inScanner.nextLine();

        System.out.println("Enter the birth month:");
        int birthMonth = inScanner.nextInt();
        inScanner.nextLine();

        System.out.println("Enter the birth day:");
        int birthDay = inScanner.nextInt();
        inScanner.nextLine();

        birthDate = LocalDate.of(birthYear, birthMonth, birthDay);

        System.out.println("Enter gender:");
        gender = inScanner.next().charAt(0);
        inScanner.nextLine();

        System.out.println("Enter email address:");
        email = inScanner.next();
        inScanner.nextLine();

        System.out.println("Enter password for Health Monitoring App:");
        password = inScanner.nextLine();
        // inScanner.nextLine();

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
            // inScanner.nextLine();

            System.out.println("Enter the number of years of experience:");
            experienceYears = inScanner.nextDouble();
            inScanner.nextLine();
        }
        
        // Create new user/doctor
        user = new User(firstName, lastName, birthDate, gender, email, password, isDoctor);

        // Add user to the database
        uDAO.createUser(user);

        if (isDoctor){
            doctor = new Doctor(user, medicalLicenseNumber, specialization, experienceYears);
            dpDAO.createDoctor(doctor);
        }

        return user;
    }

    // Define method to login the user
    public static boolean loginUser(String email, String password) {
        boolean flag = false;
        User user = uDAO.getUserByEmail(email);

        if (user.getFirstName() == null) {
            System.out.printf("User with email \"%s\" wasn't found\n", email);
        } else if (uDAO.verifyPassword(email, password)) {
            loggedInUser = user;
            loggedInUserIsDoctor = user.isDoctor();

            System.out.printf("Logged in as: \"%s %s\"\n", user.getFirstName(), user.getLastName());

            flag = true;
        } else {
            System.out.println("Incorrect password. Try again");
        }

        return flag;
    }

    public static void main(String[] args) {
        // Define required variables
        Scanner inScanner = new Scanner(System.in);

        int optionChoice = 0;
        boolean isLoggedIn = false;
        
        String welcomeHeader = "Welcome to Health Monitoring App";
        String[] welcomeMenuArr = new String[2];
        
        welcomeMenuArr[0] = "Register a new user";
        welcomeMenuArr[1] = "Log in the user";

        String mainHeader = "Health Monitoring App";
        String[] mainMenuArr = new String[6];

        mainMenuArr[0] = "Add health data";
        mainMenuArr[1] = "Generate recommendations";
        mainMenuArr[2] = "Add a medicine reminder";
        mainMenuArr[3] = "Get reminders for a specific user";
        mainMenuArr[4] = "Get due reminders for a specific user";
        mainMenuArr[5] = "Test doctor portal";

        String doctorPortalHeader = "Doctor Portal";
        String[] doctorPortalMenuArr = new String[3];

        doctorPortalMenuArr[0] = "Get doctor by id";
        doctorPortalMenuArr[1] = "Get patients associated with a doctor";
        doctorPortalMenuArr[2] = "Get health data for a specific patient";

        // Display welcome menu
        generateMenu(welcomeHeader, welcomeMenuArr, false);

        try {
            optionChoice = inScanner.nextInt();
            inScanner.nextLine();

            if (optionChoice != 0) generateHeader(welcomeMenuArr[optionChoice - 1]);

            switch (optionChoice) {
                case 1:
                    // Register a new user
                    User user = registerUser(inScanner);

                    System.out.printf("\n\"%s %s\" is now in the system.\n", user.getFirstName(), user.getLastName());
                    System.out.println("To use the app login with your credentials\n");
        
                    break;
                case 2:
                    // Login a user
                    System.out.println("Enter your email:");
                    String email = inScanner.next();
                    inScanner.nextLine();

                    System.out.println("Enter your password:");
                    String password = inScanner.nextLine();
                    // inScanner.nextLine();

                    isLoggedIn = loginUser(email, password);

                    break;
                case 0:
                default:
                    System.out.println("See you next time!");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Something went wrong. Please try again using correct data for inputs\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Check if user is logged
        if (!isLoggedIn) return;

        // Display main menu
        generateMenu(mainHeader, mainMenuArr, false);

        try {
            optionChoice = inScanner.nextInt();
            inScanner.nextLine();

            if (optionChoice != 0) generateHeader(mainMenuArr[optionChoice - 1]);

            switch (optionChoice) {
                case 1:
                    // Add health data
                    System.out.println("Enter your current weight:");
                    double weight = inScanner.nextDouble();
                    inScanner.nextLine();

                    System.out.println("Enter your current height:");
                    double height = inScanner.nextDouble();
                    inScanner.nextLine();

                    System.out.println("Enter number of steps you've walked:");
                    int steps = inScanner.nextInt();
                    inScanner.nextLine();

                    System.out.println("Enter your current heart rate:");
                    int heartRate = inScanner.nextInt();
                    inScanner.nextLine();

                    HealthData healthDataLoggedInUser = new HealthData(weight, height, steps, heartRate, LocalDate.now());
                    
                    // Create recommendations
                    List<String> recommendationArr = rSystem.generateRecommendations(healthDataLoggedInUser);
                    
                    if (hDAO.createHealthData(loggedInUser, healthDataLoggedInUser) && rDAO.createRecommendation(loggedInUser, recommendationArr)) {
                        System.out.printf("\nHealth data with recommendations for \"%s %s\" was saved successfully\n\n", loggedInUser.getFirstName(), loggedInUser.getLastName());
                    } else {
                        System.out.printf("\nSomething went wrong. Health data for \"%s %s\" wasn't saved\n", loggedInUser.getFirstName(), loggedInUser.getLastName());
                    };
                    
                    break;
                case 2:
                    // Generate recommendations
                    System.out.printf("\nRecommendations for: \"%s %s\"\n", loggedInUser.getFirstName(), loggedInUser.getLastName());

                    for (String recommendation : rDAO.getRecommendation(loggedInUser)) {
                        System.out.printf("\n%s\n", recommendation);
                    }
                    System.out.println("\n");

                    break;
                case 3:
                    String medicineName = null;
                    String dosage = null;
                    String schedule = null;
                    LocalDate startDate = null;
                    LocalDate endDate = null;

                    // Add a medicine reminder
                    System.out.println("Enter the medicine name:");
                    medicineName = inScanner.nextLine();
                    // inScanner.nextLine();

                    System.out.println("Enter the dosage:");
                    dosage = inScanner.nextLine();
                    // inScanner.nextLine();

                    System.out.println("Enter the schedule:");
                    schedule = inScanner.nextLine();
                    // inScanner.nextLine();

                    // Enter the start date
                    System.out.println("Enter the start date year:");
                    int startDateYear = inScanner.nextInt();
                    inScanner.nextLine();

                    System.out.println("Enter the start date month:");
                    int startDateMonth = inScanner.nextInt();
                    inScanner.nextLine();

                    System.out.println("Enter the start date day:");
                    int startDateDay = inScanner.nextInt();
                    inScanner.nextLine();

                    startDate = LocalDate.of(startDateYear, startDateMonth, startDateDay);
                    
                    // Enter the end date
                    System.out.println("Enter the end date year:");
                    int endDateYear = inScanner.nextInt();
                    inScanner.nextLine();

                    System.out.println("Enter the end date month:");
                    int endDateMonth = inScanner.nextInt();
                    inScanner.nextLine();

                    System.out.println("Enter the end date day:");
                    int endDateDay = inScanner.nextInt();
                    inScanner.nextLine();

                    endDate = LocalDate.of(endDateYear, endDateMonth, endDateDay);

                    // Create medicine reminder
                    if (mrDAO.createReminder(loggedInUser, new MedicineReminder(medicineName, dosage, schedule, startDate, endDate))) {
                        System.out.printf("\nMedicine reminder for \"%s %s\" was saved successfully\n\n", loggedInUser.getFirstName(), loggedInUser.getLastName());
                    } else {
                        System.out.printf("\nSomething went wrong. Medicine reminder for \"%s %s\" wasn't saved\n", loggedInUser.getFirstName(), loggedInUser.getLastName());
                    };

                    break;
                case 4:
                    List<MedicineReminder> reminderArr = null;

                    // Get reminders based on condition
                    if (loggedInUserIsDoctor) {
                        // Get reminders for a specific user
                        System.out.println("Enter the user id to get medicine reminders for a specific user:");
                        int userId = inScanner.nextInt();
                        inScanner.nextLine();

                        reminderArr = mrDAO.getRemindersByUserId(userId);
                    } else {
                        // Get personal reminders
                        reminderArr = mrDAO.getRemindersByUser(loggedInUser);
                    }

                    for (int i = 1; i <= reminderArr.size(); i++) {
                        MedicineReminder reminder = reminderArr.get(i - 1);

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
                    List<MedicineReminder> dueReminderArr = null;

                    // Get DUE reminders based on condition
                    if (loggedInUserIsDoctor) {
                        // Get DUE reminders for a specific user
                        System.out.println("Enter the user id to get medicine DUE reminders for a specific user:");
                        int userId = inScanner.nextInt();
                        inScanner.nextLine();

                        dueReminderArr = mrDAO.getDueReminders(userId);
                    } else {
                        // Get personal DUE reminders
                        dueReminderArr = mrDAO.getDueReminders(loggedInUser);
                    }

                    if (dueReminderArr.size() == 0) {
                        System.out.println("There are no due reminders yet\n");
                        break;
                    }

                    for (int i = 1; i <= dueReminderArr.size(); i++) {
                        MedicineReminder reminder = dueReminderArr.get(i - 1);

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
                    if (!loggedInUserIsDoctor) {
                        System.out.println("\nDoctor portal functionality available only for doctors");
                        System.out.println("Try to login as a doctor\n");
                        break;
                    };
                    
                    // Display Doctor Portal menu
                    generateMenu(doctorPortalHeader, doctorPortalMenuArr, false);
                    
                    optionChoice = inScanner.nextInt();
                    inScanner.nextLine();

                    if (optionChoice != 0) generateHeader(doctorPortalMenuArr[optionChoice - 1]);

                    switch (optionChoice) {
                        case 1:
                            // Get doctor by id
                            System.out.println("Enter the doctor id:");
                            int doctorId = inScanner.nextInt();
                            inScanner.nextLine();
                            
                            Doctor doctor = dpDAO.getDoctorById(doctorId);
                            
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
                            int docId = inScanner.nextInt();
                            inScanner.nextLine();
                            
                            List<User> patientArr = dpDAO.getPatientsByDoctorId(docId);

                            if (patientArr.size() == 0) {
                                System.out.printf("Doctor with id \"%d\" does not have patients yet\n", docId);
                                break;

                            } 
                            System.out.printf("Doctor \"%d\", patients:\n", docId);
                            for (User patient : patientArr) {
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

                            List<HealthData> healthDataArr = dpDAO.getHealthDataByPatientId(patientId);

                            System.out.printf("Patient: \"%d\"\n", patientId);
                            for (HealthData healthData : healthDataArr) {
                                // Display patient's info
                                System.out.printf("Weight: \"%s\"\n", healthData.getWeight());
                                System.out.printf("Height: \"%s\"\n", healthData.getHeight());
                                System.out.printf("Steps: \"%s\"\n", healthData.getSteps());
                                System.out.printf("Heart Rate: \"%s\"\n", healthData.getHeartRate());
                                System.out.printf("Date: \"%s\"\n", healthData.getDate());
                                System.out.println("");
                            }

                            break;
                        case 0:
                        default:
                            System.out.println("See you next time!");
                            break;
                    }

                    break;
                case 0:
                default:
                    System.out.println("See you next time!");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Something went wrong. Please try again using correct data for inputs\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        inScanner.close();
    }
}
