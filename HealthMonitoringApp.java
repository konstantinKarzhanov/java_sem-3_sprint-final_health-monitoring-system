// Import required packages
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

// Import custom packages
import util.*;

public class HealthMonitoringApp {
    private static int optionChoice = 0;
    
    public static void main(String[] args) {
        try (Scanner inScanner = new Scanner(System.in)) {
            do {
                optionChoice = MenuUtil.handleMenuChoice(MenuUtil.WELCOME_MENU, inScanner);
                boolean userIsLoggedIn = MenuUtil.processWelcomeMenuChoice(optionChoice, inScanner);

                if (userIsLoggedIn) {
                    optionChoice = MenuUtil.handleMenuChoice(MenuUtil.MAIN_MENU, inScanner);
                    MenuUtil.processMainMenuChoice(optionChoice, inScanner);
                } else {
                    System.out.println("To use the app login with your credentials\n");
                }
            } while (optionChoice != 0);

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
