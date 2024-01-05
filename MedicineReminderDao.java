// Import required packages
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

// Define class
public class MedicineReminderDao {
    // Define method to create reminder
    public boolean createReminder(User user, MedicineReminder reminder) {
        boolean flag = false;

        // Prepare the SQL query
        String selectIdQuery = "SELECT id FROM users WHERE first_name = ? AND last_name = ?";
        String query = String.format("INSERT INTO medicine_reminders(user_id, medicine_name, dosage, schedule, start_date, end_date) VALUES((%s), ?, ?, ?, ?, ?);", selectIdQuery);
    
        // Database logic to insert data using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, reminder.getMedicineName());
            statement.setString(4, reminder.getDosage());
            statement.setString(5, reminder.getSchedule());
            statement.setDate(6, Date.valueOf(reminder.getStartDate()));
            statement.setDate(7, Date.valueOf(reminder.getEndDate()));

            statement.executeUpdate();

            flag = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return flag;
    }

    // Define method to get reminder from the database by id
    public MedicineReminder getReminderById(int id) {
        String medicineName = null;
        String dosage = null;
        String schedule = null;
        LocalDate startDate = null;
        LocalDate endDate = null;

        // Prepare the SQL query
        String query = "SELECT * FROM medicine_reminders WHERE id = ?;";

        // Database logic to insert data using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                medicineName = resultSet.getString("medicine_name");
                dosage = resultSet.getString("dosage");
                schedule = resultSet.getString("schedule");
                startDate = resultSet.getDate("start_date").toLocalDate();
                endDate = resultSet.getDate("end_date").toLocalDate();
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return new MedicineReminder(medicineName, dosage, schedule, startDate, endDate);
    }

    // Define method to get user's reminders from the database by user id
    public List<MedicineReminder> getRemindersByUserId(int userId) {
        String medicineName = null;
        String dosage = null;
        String schedule = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        List<MedicineReminder> reminderArr = new ArrayList<>();

        // Prepare the SQL query
        String query = "SELECT * FROM medicine_reminders WHERE user_id = ?;";

        // Database logic to insert data using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                medicineName = resultSet.getString("medicine_name");
                dosage = resultSet.getString("dosage");
                schedule = resultSet.getString("schedule");
                startDate = resultSet.getDate("start_date").toLocalDate();
                endDate = resultSet.getDate("end_date").toLocalDate();

                reminderArr.add(new MedicineReminder(medicineName, dosage, schedule, startDate, endDate));
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reminderArr;
    }

    // Define method to get user's reminders from the database by user
    public List<MedicineReminder> getRemindersByUser(User user) {
        String medicineName = null;
        String dosage = null;
        String schedule = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        List<MedicineReminder> reminderArr = new ArrayList<>();

        // Prepare the SQL query
        String selectIdQuery = "SELECT id FROM users WHERE first_name = ? AND last_name = ?";
        String query = String.format("SELECT * FROM medicine_reminders WHERE user_id = (%s);", selectIdQuery);

        // Database logic to insert data using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                medicineName = resultSet.getString("medicine_name");
                dosage = resultSet.getString("dosage");
                schedule = resultSet.getString("schedule");
                startDate = resultSet.getDate("start_date").toLocalDate();
                endDate = resultSet.getDate("end_date").toLocalDate();

                reminderArr.add(new MedicineReminder(medicineName, dosage, schedule, startDate, endDate));
            }

            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reminderArr;
    }

    // Define method to get user's DUE reminders from the database
    public List<MedicineReminder> getDueReminders(int userId) {
        List<MedicineReminder> dueReminderArr = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (MedicineReminder reminder : this.getRemindersByUserId(userId)) {
            LocalDate endDate = reminder.getEndDate();

            if (endDate.isBefore(currentDate) || endDate.isEqual(currentDate)) {
                dueReminderArr.add(reminder);
            }
        }

        return dueReminderArr;
    }

    // Define method to get user's DUE reminders from the database
    public List<MedicineReminder> getDueReminders(User user) {
        List<MedicineReminder> dueReminderArr = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (MedicineReminder reminder : this.getRemindersByUser(user)) {
            LocalDate endDate = reminder.getEndDate();

            if (endDate.isBefore(currentDate) || endDate.isEqual(currentDate)) {
                dueReminderArr.add(reminder);
            }
        }

        return dueReminderArr;
    }

    // Define method to update reminder
    public boolean updateReminder(int id, User user, MedicineReminder reminder) {
        boolean flag = false;

        // Prepare the SQL query
        String selectIdQuery = "SELECT id FROM users WHERE first_name = ? AND last_name = ?";
        String query = String.format("UPDATE medicine_reminders SET user_id = (%s), medicine_name = ?, dosage = ?, schedule = ?, start_date = ?, end_date = ? WHERE id = ?;", selectIdQuery);

        // Database logic to insert data using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, reminder.getMedicineName());
            statement.setString(4, reminder.getDosage());
            statement.setString(5, reminder.getSchedule());
            statement.setDate(6, Date.valueOf(reminder.getStartDate()));
            statement.setDate(7, Date.valueOf(reminder.getEndDate()));
            statement.setInt(8, id);

            statement.executeUpdate();
            
            flag = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return flag;
    }

    // Define method to delete reminder from the database
    public boolean deleteReminder(int id) {
        boolean flag = false;

        // Prepare the SQL query
        String query = "DELETE FROM medicine_reminders WHERE id = ?;";

        // Database logic to insert data using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();    
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setInt(1, id);
            statement.executeUpdate();

            flag = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return flag;
    }
}
