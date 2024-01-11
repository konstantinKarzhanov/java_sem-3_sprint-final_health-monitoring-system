package views;

// Import required packages
import java.util.Scanner;
import java.time.LocalDate;

// Import custom packages
import model.HealthData;

// Define class
public class HealthDataView {
    // Define method to add health data
    public static HealthData addHealthData(Scanner inScanner) throws Exception {
        // Define required variables
        double weight = 0;
        double height = 0;
        int steps = 0;
        int heartRate = 0;
        
        System.out.println("Enter your current weight:");
        weight = inScanner.nextDouble();
        inScanner.nextLine();

        System.out.println("Enter your current height:");
        height = inScanner.nextDouble();
        inScanner.nextLine();

        System.out.println("Enter number of steps you've walked:");
        steps = inScanner.nextInt();
        inScanner.nextLine();

        System.out.println("Enter your current heart rate:");
        heartRate = inScanner.nextInt();
        inScanner.nextLine();

        return new HealthData(weight, height, steps, heartRate, LocalDate.now());
    }
}
