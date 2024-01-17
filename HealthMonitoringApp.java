// Import required packages
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

// Import custom packages
import util.*;

public class HealthMonitoringApp {
    public static void main(String[] args) {
        try (Scanner inScanner = new Scanner(System.in)) {
            do {
                boolean userIsLoggedIn = MenuUtil.processWelcomeMenu(inScanner);

                if (userIsLoggedIn) {
                    MenuUtil.processMainMenu(inScanner);
                } else {
                    System.out.println("To use the app login with your credentials\n");
                }
            } while (MenuUtil.getChosenOption() != 0);

            System.out.println("See you next time!");
        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Something went wrong. Please try again using correct data for inputs\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
