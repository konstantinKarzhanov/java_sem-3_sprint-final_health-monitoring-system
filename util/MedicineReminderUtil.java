package util;

// Import required packages
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Import custom packages
import dao.MedicineReminderDao;
import model.MedicineReminder;
import model.User;

public class MedicineReminderUtil {
    // Define method to add medicine reminder
    public static MedicineReminder addMedicineReminder(Scanner inScanner) throws InputMismatchException {
        // Define required variables
        String medicineName = null;
        String dosage = null;
        String schedule = null;
        LocalDate startDate = null;
        LocalDate endDate = null;

        System.out.println("Enter the medicine name:");
        medicineName = inScanner.nextLine();

        System.out.println("Enter the dosage:");
        dosage = inScanner.nextLine();

        System.out.println("Enter the schedule:");
        schedule = inScanner.nextLine();

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

        return new MedicineReminder(medicineName, dosage, schedule, startDate, endDate);
    }

    public static void processMedicineReminder(Scanner inScanner) throws SQLException {
        User loggedUser = UserManagementUtil.getLoggedUser();
        
        MedicineReminder medicineReminder = addMedicineReminder(inScanner);

        String message = 
            MedicineReminderDao.createReminder(loggedUser, medicineReminder)
            ? "\nMedicine reminder for \"%s %s\" was saved successfully\n\n" 
            : "\nSomething went wrong. Medicine reminder for \"%s %s\" wasn't saved\n";
        
        System.out.printf(message, loggedUser.getFirstName(), loggedUser.getLastName());
    }

    public static void displayMedicineReminderList(Scanner inScanner, boolean isDue) throws SQLException {
        User loggedUser = UserManagementUtil.getLoggedUser();
        List<MedicineReminder> reminderList = null;

        // Get reminders based on condition
        if (loggedUser.isDoctor()) {
            // Get reminders for a specific user
            System.out.println("Enter the user id to get medicine reminders for a specific user:");
            int userId = inScanner.nextInt();
            inScanner.nextLine();

            reminderList = MedicineReminderDao.getReminders(userId, isDue);
        } else {
            // Get personal reminders
            reminderList = MedicineReminderDao.getReminders(loggedUser, isDue);
        }

        if (reminderList.size() == 0) {
            System.out.println("There are no reminders yet\n");
            return;
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
    }
}
