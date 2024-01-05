// Import required packages
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

// Define class
public class UserDao {
    // Define method to insert user into the database 
    public boolean createUser(User user) {
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return flag;
    }

    // Define method to get user from database by id 
    public User getUserById(int id) { 
        String firstName = null;
        String lastName = null;
        LocalDate birthDate = null;
        char gender = '\0';
        String email = null;
        String password = null;
        boolean isDoctor = false;

        // Prepare the SQL query
        String query = "SELECT * FROM users WHERE id = ?;";

        // Database logic to get data by ID Using Prepared Statement        
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return new User(firstName, lastName, birthDate, gender, email, password, isDoctor);
    }

    // Define method to get user from the database by email
    public User getUserByEmail(String user_email) { 
        String firstName = null;
        String lastName = null;
        LocalDate birthDate = null;
        char gender = '\0';
        String email = null;
        String password = null;
        boolean isDoctor = false;

        // Prepare the SQL query
        String query = "SELECT * FROM users WHERE email = ?;";

        // Database logic to get data by ID Using Prepared Statement
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return new User(firstName, lastName, birthDate, gender, email, password, isDoctor);
    }

    // Define method to update the user by id
    public boolean updateUser(int id, User user) {
        boolean flag = false;

        System.out.println(user.getPassword());
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        System.out.println(hashedPassword);

        // Prepare the SQL query
        String query = "UPDATE users SET first_name = ?, last_name = ?, birth_date = ?, gender = ?, email = ?, password = ?, is_doctor = ? WHERE id = ?;";
        
        // Database logic to get update user Using Prepared Statement
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return flag;
    }

    // Define method to delete the user from the database by id
    public boolean deleteUser(int id) {
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
            System.out.println(e.getMessage());

            if (dbConnection != null) {
                try {
                    // Rollback transaction       
                    dbConnection.rollback();
                } catch (SQLException rollbackE) {
                    System.out.println(rollbackE.getMessage());
                }
            }
        } finally {
            // Set auto-commit mode to default state
            try {
                dbConnection.setAutoCommit(true);

                if (dbConnection != null) dbConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return flag;
    }

    // Define method to verify the user's password
    public boolean verifyPassword (String email, String password) {
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return BCrypt.checkpw(password, hashedPassword);
    }
}
