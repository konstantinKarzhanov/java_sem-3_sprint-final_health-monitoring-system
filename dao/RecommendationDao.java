package dao;

// Import required packages
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.sql.SQLException;

// import custom packages
import config.DatabaseConnection;
import model.User;

// Define class
public class RecommendationDao {
    // Define method to create recommendation in the database
    public static boolean createRecommendation(User user, List<String> recommendationList) {
        boolean flag = false;

        // Prepare the SQL query
        String selectIdQuery = "SELECT id FROM users WHERE first_name = ? AND last_name = ?";
        String query = String.format("INSERT INTO recommendations(user_id, recommendation_text, date) VALUES((%s), ?, ?);", selectIdQuery);

        // Database logic to insert data into the database
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());

            for (String recommendation : recommendationList) {
                statement.setString(3, recommendation);
                statement.setObject(4, LocalDate.now());

                statement.executeUpdate();
            }
            
            flag = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return flag;
    }

    // Define method to get recommendation from the database for a specific user
    public static List<String> getRecommendation(User user) {
        List<String> recommendationList = new ArrayList<>();
        
        // Prepare the SQL query
        String selectIdQuery = "SELECT id FROM users WHERE first_name = ? AND last_name = ?";
        String query = String.format("SELECT * FROM recommendations WHERE user_id = (%s)", selectIdQuery);

        // Database logic to get data from the database
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                recommendationList.add(resultSet.getString("recommendation_text"));
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return recommendationList;
    }
}