// Import required packages
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

// Define class
public class DoctorPortalDao {
    // Define attributes
    private UserDao userDao;
    private HealthDataDao healthDataDao;

    // Define constructors
    public DoctorPortalDao() {
        this.userDao = new UserDao();
        this.healthDataDao = new HealthDataDao();
    }

    // Define method to insert doctor into the database 
    public boolean createDoctor(Doctor doctor) {
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return flag;
    }

    // Define method to get doctor from the database by id
    public Doctor getDoctorById(int doctorId) {
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return new Doctor(this.userDao.getUserById(doctorId), medicalLicenseNumber, specialization, experienceYears);
    }

    // Define method to get doctor's patient list from the database by doctor id
    public List<User> getPatientsByDoctorId(int doctorId) {
        int userId = 0;
        List<User> patientArr = new ArrayList<>();

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
                patientArr.add(this.userDao.getUserById(userId));
            }
            
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return patientArr;
    }

    // Define method to get health data by patient id
    public List<HealthData> getHealthDataByPatientId(int patientId) {
        return healthDataDao.getHealthDataByUserId(patientId);
    }
}

