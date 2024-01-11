package views;

// Import required packages
import java.util.Scanner;
import java.time.LocalDate;

// Import custom packages
import model.MedicineReminder;

// Define class
public class MedicineReminderView {
    // Define method to add medicine reminder
    public static MedicineReminder addMedicineReminder(Scanner inScanner) throws Exception {
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
}
