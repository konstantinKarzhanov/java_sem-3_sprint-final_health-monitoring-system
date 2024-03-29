package dao;

// Import external packages
import org.mindrot.jbcrypt.BCrypt;

// Import requierd packages
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

// import custom packages
import config.DatabaseConnection;
import model.User;

public class UserDao {
    // Define method to insert user into the database 
    public static boolean createUser(User user) throws SQLException {
        boolean flag = false;
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        
        // Prepare the SQL query
        String query = "INSERT INTO users(first_name, last_name, birth_date, gender, email, password, is_doctor) VALUES(?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ?);";
        
        // Database logic to insert data using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, Date.valueOf(user.getBirthDate()));
            statement.setString(4, Character.toString(user.getGender()));
            statement.setString(5, user.getEmail());
            statement.setString(6, hashedPassword);
            statement.setBoolean(7, user.isDoctor());

            statement.executeUpdate();

            flag = true;
        }

        return flag;
    }

    // Define method to get user from database by id 
    public static User getUserById(int id) throws SQLException { 
        String firstName = null;
        String lastName = null;
        LocalDate birthDate = null;
        char gender = '\0';
        String email = null;
        String password = null;
        boolean isDoctor = false;

        // Prepare the SQL query
        String query = "SELECT * FROM users WHERE id = ?;";

        // Database logic to get data by ID using Prepared Statement        
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                firstName = resultSet.getString("first_name");
                lastName = resultSet.getString("last_name");
                birthDate = resultSet.getDate("birth_date").toLocalDate();
                gender = resultSet.getString("gender").charAt(0);
                email = resultSet.getString("email");
                password = resultSet.getString("password");
                isDoctor = resultSet.getBoolean("is_doctor");
            }

            resultSet.close();
        }

        return new User(firstName, lastName, birthDate, gender, email, password, isDoctor);
    }

    // Define method to get user from the database by email
    public static User getUserByEmail(String user_email) throws SQLException { 
        String firstName = null;
        String lastName = null;
        LocalDate birthDate = null;
        char gender = '\0';
        String email = null;
        String password = null;
        boolean isDoctor = false;

        // Prepare the SQL query
        String query = "SELECT * FROM users WHERE email = ?;";

        // Database logic to get data by email using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setString(1, user_email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                firstName = resultSet.getString("first_name");
                lastName = resultSet.getString("last_name");
                birthDate = resultSet.getDate("birth_date").toLocalDate();
                gender = resultSet.getString("gender").charAt(0);
                email = resultSet.getString("email");
                password = resultSet.getString("password");
                isDoctor = resultSet.getBoolean("is_doctor");
            }

            resultSet.close();
        }

        return new User(firstName, lastName, birthDate, gender, email, password, isDoctor);
    }

    // Define method to update the user by id
    public static boolean updateUser(int id, User user) throws SQLException {
        boolean flag = false;

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        // Prepare the SQL query
        String query = "UPDATE users SET first_name = ?, last_name = ?, birth_date = ?, gender = ?, email = ?, password = ?, is_doctor = ? WHERE id = ?;";
        
        // Database logic to update user using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, Date.valueOf(user.getBirthDate()));
            statement.setString(4, Character.toString(user.getGender()));
            statement.setString(5, user.getEmail());
            statement.setString(6, hashedPassword);
            statement.setBoolean(7, user.isDoctor());
            statement.setInt(8, id);

            statement.executeUpdate();

            flag = true;
        }

        return flag;
    }

    // Define method to delete the user from the database by id
    public static boolean deleteUser(int id) throws SQLException {
        boolean flag = false;

        // Prepare the SQL query        
        String deleteRelatedQuery = "DELETE FROM health_data WHERE user_id = ?;";
        String deleteQuery = "DELETE FROM users WHERE id = ?;";

        // Database logic to delete user
        Connection dbConnection = DatabaseConnection.useConnection();

        try (PreparedStatement deleteRelatedStatement = dbConnection.prepareStatement(deleteRelatedQuery); 
             PreparedStatement deleteStatement = dbConnection.prepareStatement(deleteQuery)) {
            // Disable the auto-commit mode to group statements into one transaction
            dbConnection.setAutoCommit(false);

            deleteRelatedStatement.setInt(1, id);
            deleteStatement.setInt(1, id);
            
            deleteRelatedStatement.executeUpdate();
            deleteStatement.executeUpdate();

            // Commit transaction
            dbConnection.commit();

            flag = true;
        } catch (SQLException e) {
            if (dbConnection != null) {
                try {
                    // Rollback transaction       
                    dbConnection.rollback();
                } catch (SQLException rollbackE) {
                    throw rollbackE;
                }
            }

            throw e;
        } finally {
            // Set auto-commit mode to default state
            try {
                dbConnection.setAutoCommit(true);

                if (dbConnection != null) dbConnection.close();
            } catch (SQLException e) {
                throw e;
            }
        }

        return flag;
    }

    // Define method to verify the user's password
    public static boolean verifyPassword (String email, String password) throws SQLException {
        String hashedPassword = null;

        // Prepare the SQL query
        String query = "SELECT password FROM users WHERE email = ?;";

        // Implement logic to retrieve password using the Bcrypt
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                hashedPassword = resultSet.getString("password");
            }

            resultSet.close();
        }

        // Check if password is correct and return true/false
        return BCrypt.checkpw(password, hashedPassword);
    }
}
