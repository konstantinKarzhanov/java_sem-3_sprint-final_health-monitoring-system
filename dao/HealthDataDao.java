package dao;

// Import required packages
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

// import custom packages
import config.DatabaseConnection;
import model.HealthData;
import model.User;

public class HealthDataDao {
    // Define method to insert user's health data into the database 
    public static boolean createHealthData(User user, HealthData healthData) throws SQLException { 
        boolean flag = false;

        // Prepare the SQL query
        String selectIdQuery = "SELECT id FROM users WHERE first_name = ? AND last_name = ?";
        String query = String.format("INSERT INTO health_data(user_id, weight, height, steps, heart_rate, date) VALUES((%s), ?, ?, ?, ?, ?);", selectIdQuery);

        // Database logic to insert data using Prepared Statement
        try (   
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDouble(3, healthData.getWeight());
            statement.setDouble(4, healthData.getHeight());
            statement.setInt(5, healthData.getSteps());
            statement.setInt(6, healthData.getHeartRate());
            statement.setDate(7, Date.valueOf(healthData.getDate()));

            statement.executeUpdate();

            flag = true;
        }

        return flag;
    }

    // Define method to get health data from the database by id
    public static HealthData getHealthDataById(int id) throws SQLException {
        double weight = 0;
        double height = 0;
        int steps = 0;
        int heartRate = 0;
        LocalDate date = null;

        // Prepare the SQL query
        String query = "SELECT * FROM health_data WHERE id = ?;";

        // Database logic to get data using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                weight = resultSet.getDouble("weight");
                height = resultSet.getDouble("height");
                steps = resultSet.getInt("steps");
                heartRate = resultSet.getInt("heart_rate");
                date = resultSet.getDate("date").toLocalDate();
            }

            resultSet.close();
        }

        return new HealthData(weight, height, steps, heartRate, date);
    }
  
    // Define method to get user's health data from the database by user id
    public static List<HealthData> getHealthDataByUserId(int userId) throws SQLException { 
        double weight = 0;
        double height = 0;
        int steps = 0;
        int heartRate = 0;
        LocalDate date = null;
        List<HealthData> healthDataList = new ArrayList<>();

        // Prepare the SQL query
        String query = "SELECT * FROM health_data WHERE user_id = ?;";

        // Database logic to get health_data by user's id using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                weight = resultSet.getDouble("weight");
                height = resultSet.getDouble("height");
                steps = resultSet.getInt("steps");
                heartRate = resultSet.getInt("heart_rate");
                date = resultSet.getDate("date").toLocalDate();

                healthDataList.add(new HealthData(weight, height, steps, heartRate, date));
            }

            resultSet.close();
        }

        return healthDataList;
    }
  
    // Define method to update the health data by id
    public static boolean updateHealthData(int id, User user, HealthData healthData) throws SQLException { 
        boolean flag = false;

        // Prepare the SQL query
        String selectIdQuery = "SELECT id FROM users WHERE first_name = ? AND last_name = ?";
        String query = String.format("UPDATE health_data SET user_id = (%s), weight = ?, height = ?, steps = ?, heart_rate = ?, date = ? WHERE id = ?;", selectIdQuery);

        // Database logic to update health_data by id using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDouble(3, healthData.getWeight());
            statement.setDouble(4, healthData.getHeight());
            statement.setInt(5, healthData.getSteps());
            statement.setInt(6, healthData.getHeartRate());
            statement.setDate(7, Date.valueOf(healthData.getDate()));
            statement.setInt(8, id);

            statement.executeUpdate();

            flag = true;
        }

        return flag;
    }

    // Define method to delete the health data from the database by id
    public static boolean deleteHealthData(int id) throws SQLException { 
        boolean flag = false;

        // Prepare the SQL query
        String query = "DELETE FROM health_data WHERE id = ?;";

        // Database logic to delete health_data by id using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ){
            statement.setInt(1, id);
            statement.executeUpdate();

            flag = true;
        }

        return flag;
    }
}
