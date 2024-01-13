package dao;

// Import required packages
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

// import custom packages
import config.DatabaseConnection;
import model.Doctor;
import model.HealthData;
import model.User;


public class DoctorPortalDao {
    // Define method to insert doctor into the database 
    public static boolean createDoctor(Doctor doctor) throws SQLException {
        boolean flag = false;
        
        // Prepare the SQL query
        String selectIdQuery = "SELECT id FROM users WHERE first_name = ? AND last_name = ?";
        String query = String.format("INSERT INTO doctor(doctor_id, medical_license_number, specialization, experience_years) VALUES((%s), ?, ?, ?);", selectIdQuery);
        
        // Database logic to insert data using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setString(1, doctor.getFirstName());
            statement.setString(2, doctor.getLastName());
            statement.setString(3, doctor.getMedicalLicenseNumber());
            statement.setString(4, doctor.getSpecialization());
            statement.setDouble(5, doctor.getExperienceYears());

            statement.executeUpdate();

            flag = true;
        }

        return flag;
    }

    // Define method to get doctor from the database by id
    public static Doctor getDoctorById(int doctorId) throws SQLException {
        String medicalLicenseNumber = null;
        String specialization = null;
        double experienceYears = 0;
    
        // Prepare the SQL query
        String query = "SELECT * FROM doctor WHERE doctor_id = ?;";

        // Database logic to insert data using Prepared Statement        
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setInt(1, doctorId);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                medicalLicenseNumber = resultSet.getString("medical_license_number");
                specialization = resultSet.getString("specialization");
                experienceYears = resultSet.getDouble("experience_years");
            }

            resultSet.close();
        }

        return new Doctor(UserDao.getUserById(doctorId), medicalLicenseNumber, specialization, experienceYears);
    }

    // Define method to get doctor's patient list from the database by doctor id
    public static List<User> getPatientsByDoctorId(int doctorId) throws SQLException {
        int userId = 0;
        List<User> patientList = new ArrayList<>();

        // Prepare the SQL query
        String query = "SELECT * FROM doctor_patient WHERE doctor_id = ?;";

        // Database logic to insert data using Prepared Statement
        try (
                Connection dbConnection = DatabaseConnection.useConnection();
                PreparedStatement statement = dbConnection.prepareStatement(query);
            ) {
            statement.setInt(1, doctorId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                userId = resultSet.getInt("patient_id");
                patientList.add(UserDao.getUserById(userId));
            }
            
            resultSet.close();
        }

        return patientList;
    }

    // Define method to get health data by patient id
    public static List<HealthData> getHealthDataByPatientId(int patientId) throws SQLException {
        return HealthDataDao.getHealthDataByUserId(patientId);
    }
}

