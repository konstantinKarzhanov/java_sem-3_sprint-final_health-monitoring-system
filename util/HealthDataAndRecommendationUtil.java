package util;

// Import required packages
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Import custom packages
import dao.HealthDataDao;
import dao.RecommendationDao;

import model.HealthData;
import model.Recommendation;
import model.User;


public class HealthDataAndRecommendationUtil {
    // Define method to add health data
    public static HealthData addHealthData(Scanner inScanner) throws InputMismatchException {
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

    public static void processHealthDataAndRecommendationList(Scanner inScanner) throws SQLException {
        User loggedUser = UserManagementUtil.getLoggedUser();

        // Add health data
        HealthData healthData = addHealthData(inScanner);
                
        // Generate recommendation list
        List<String> recommendationList = Recommendation.generateRecommendationList(healthData);

        String message = 
            HealthDataDao.createHealthData(loggedUser, healthData) && RecommendationDao.createRecommendation(loggedUser, recommendationList) 
            ? "\nHealth data with recommendations for \"%s %s\" was saved successfully\n\n" 
            : "\nSomething went wrong. Health data for \"%s %s\" wasn't saved\n";
        
        System.out.printf(message, loggedUser.getFirstName(), loggedUser.getLastName());
    }

    public static void displayRecommendationList() throws SQLException {
        User loggedUser = UserManagementUtil.getLoggedUser();
        
        System.out.printf("\nRecommendations for: \"%s %s\"\n", loggedUser.getFirstName(), loggedUser.getLastName());
        
        for (String recommendation : RecommendationDao.getRecommendation(loggedUser)) {
            System.out.printf("\n%s\n", recommendation);
        }

        System.out.println("\n");
    }
}
