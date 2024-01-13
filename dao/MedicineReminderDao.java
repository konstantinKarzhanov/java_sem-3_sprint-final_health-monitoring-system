package dao;

// Import required packages
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// import custom packages
import config.DatabaseConnection;
import model.MedicineReminder;
import model.User;


public class MedicineReminderDao {
    // Define method to create reminder
    public static boolean createReminder(User user, MedicineReminder reminder) throws SQLException {
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
        }

        return flag;
    }

    // Define method to get reminder from the database by id
    public static MedicineReminder getReminderById(int id) throws SQLException {
        String medicineName = null;
        String dosage = null;
        String schedule = null;
        LocalDate startDate = null;
        LocalDate endDate = null;

        // Prepare the SQL query
        String query = "SELECT * FROM medicine_reminders WHERE id = ?;";

        // Database logic to get data using Prepared Statement
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
        }

        return new MedicineReminder(medicineName, dosage, schedule, startDate, endDate);
    }

    // // Define method to get reminders from the database by user id
    // public static List<MedicineReminder> getReminders(int userId) throws SQLException {
    //     String medicineName = null;
    //     String dosage = null;
    //     String schedule = null;
    //     LocalDate startDate = null;
    //     LocalDate endDate = null;
    //     List<MedicineReminder> reminderList = new ArrayList<>();

    //     // Prepare the SQL query
    //     String query = "SELECT * FROM medicine_reminders WHERE user_id = ?;";

    //     // Database logic to get data using Prepared Statement
    //     try (
    //             Connection dbConnection = DatabaseConnection.useConnection();
    //             PreparedStatement statement = dbConnection.prepareStatement(query);
    //         ) {
    //         statement.setInt(1, userId);
    //         ResultSet resultSet = statement.executeQuery();

    //         while(resultSet.next()) {
    //             medicineName = resultSet.getString("medicine_name");
    //             dosage = resultSet.getString("dosage");
    //             schedule = resultSet.getString("schedule");
    //             startDate = resultSet.getDate("start_date").toLocalDate();
    //             endDate = resultSet.getDate("end_date").toLocalDate();

    //             reminderList.add(new MedicineReminder(medicineName, dosage, schedule, startDate, endDate));
    //         }

    //         resultSet.close();
    //     }

    //     return reminderList;
    // }

    // // Define method to get user's reminders from the database by user
    // public static List<MedicineReminder> getReminders(User user) throws SQLException {
    //     String medicineName = null;
    //     String dosage = null;
    //     String schedule = null;
    //     LocalDate startDate = null;
    //     LocalDate endDate = null;
    //     List<MedicineReminder> reminderList = new ArrayList<>();

    //     // Prepare the SQL query
    //     String selectIdQuery = "SELECT id FROM users WHERE first_name = ? AND last_name = ?";
    //     String query = String.format("SELECT * FROM medicine_reminders WHERE user_id = (%s);", selectIdQuery);

    //     // Database logic to get data using Prepared Statement
    //     try (
    //             Connection dbConnection = DatabaseConnection.useConnection();
    //             PreparedStatement statement = dbConnection.prepareStatement(query);
    //         ) {
    //         statement.setString(1, user.getFirstName());
    //         statement.setString(2, user.getLastName());

    //         ResultSet resultSet = statement.executeQuery();

    //         while(resultSet.next()) {
    //             medicineName = resultSet.getString("medicine_name");
    //             dosage = resultSet.getString("dosage");
    //             schedule = resultSet.getString("schedule");
    //             startDate = resultSet.getDate("start_date").toLocalDate();
    //             endDate = resultSet.getDate("end_date").toLocalDate();

    //             reminderList.add(new MedicineReminder(medicineName, dosage, schedule, startDate, endDate));
    //         }

    //         resultSet.close();
    //     }

    //     return reminderList;
    // }

    // Define method to get reminders from the database by user id
    public static List<MedicineReminder> getReminders(int userId, boolean isDue) throws SQLException {
        String medicineName = null;
        String dosage = null;
        String schedule = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        List<MedicineReminder> reminderList = new ArrayList<>();

        // Prepare the SQL query
        String query = !isDue ? "SELECT * FROM medicine_reminders WHERE user_id = ?;" : "SELECT * FROM medicine_reminders WHERE user_id = ? AND end_date < NOW();";

        // Database logic to get data using Prepared Statement
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

                reminderList.add(new MedicineReminder(medicineName, dosage, schedule, startDate, endDate));
            }

            resultSet.close();
        }

        return reminderList;
    }

    // Define method to get user's reminders from the database by user
    public static List<MedicineReminder> getReminders(User user, boolean isDue) throws SQLException {
        String medicineName = null;
        String dosage = null;
        String schedule = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        List<MedicineReminder> reminderList = new ArrayList<>();

        // Prepare the SQL query
        String selectIdQuery = "SELECT id FROM users WHERE first_name = ? AND last_name = ?";
        String query = !isDue ? "SELECT * FROM medicine_reminders WHERE user_id = (%s);" : "SELECT * FROM medicine_reminders WHERE user_id = (%s) AND end_date < NOW();";
        query = String.format(query, selectIdQuery);
        
        // Database logic to get data using Prepared Statement
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

                reminderList.add(new MedicineReminder(medicineName, dosage, schedule, startDate, endDate));
            }

            resultSet.close();
        }

        return reminderList;
    }

    // // Define method to get DUE reminders from the database by user id
    // public static List<MedicineReminder> getDueReminders(int userId) throws SQLException {
    //     List<MedicineReminder> dueReminderList = new ArrayList<>();
    //     LocalDate currentDate = LocalDate.now();

    //     for (MedicineReminder reminder : getReminders(userId)) {
    //         LocalDate endDate = reminder.getEndDate();

    //         if (endDate.isBefore(currentDate) || endDate.isEqual(currentDate)) {
    //             dueReminderList.add(reminder);
    //         }
    //     }

    //     return dueReminderList;
    // }

    // // Define method to get DUE reminders from the database by user
    // public static List<MedicineReminder> getDueReminders(User user) throws SQLException {
    //     List<MedicineReminder> dueReminderList = new ArrayList<>();
    //     LocalDate currentDate = LocalDate.now();

    //     for (MedicineReminder reminder : getReminders(user)) {
    //         LocalDate endDate = reminder.getEndDate();

    //         if (endDate.isBefore(currentDate) || endDate.isEqual(currentDate)) {
    //             dueReminderList.add(reminder);
    //         }
    //     }

    //     return dueReminderList;
    // }

    // Define method to update reminder
    public static boolean updateReminder(int id, User user, MedicineReminder reminder) throws SQLException {
        boolean flag = false;

        // Prepare the SQL query
        String selectIdQuery = "SELECT id FROM users WHERE first_name = ? AND last_name = ?";
        String query = String.format("UPDATE medicine_reminders SET user_id = (%s), medicine_name = ?, dosage = ?, schedule = ?, start_date = ?, end_date = ? WHERE id = ?;", selectIdQuery);

        // Database logic to update medicine_reminders data using Prepared Statement
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
        }

        return flag;
    }

    // Define method to delete reminder from the database
    public static boolean deleteReminder(int id) throws SQLException {
        boolean flag = false;

        // Prepare the SQL query
        String query = "DELETE FROM medicine_reminders WHERE id = ?;";

        // Database logic to delete data using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();    
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setInt(1, id);
            statement.executeUpdate();

            flag = true;
        }

        return flag;
    }
}
