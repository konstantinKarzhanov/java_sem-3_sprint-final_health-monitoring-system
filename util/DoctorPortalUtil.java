package util;

// Import required packages
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Import custom packages
import dao.DoctorPortalDao;
import model.Doctor;
import model.HealthData;
import model.User;

public class DoctorPortalUtil {
    public static void displayDoctorsInfo(Scanner inScanner) throws InputMismatchException, SQLException {
        // Enter the doctor's id
        System.out.println("Enter the doctor's id:");
        int doctorId = inScanner.nextInt();
        inScanner.nextLine();

        Doctor doctor = DoctorPortalDao.getDoctorById(doctorId);
        
        if (doctor.getFirstName() == null || doctor.getMedicalLicenseNumber() == null) {
            System.out.printf("Doctor with id \"%d\" wasn't found\n", doctorId);
            return;
        }

        // Display doctor's info
        System.out.println("");
        System.out.printf("Medical License Number: \"%s\"\n", doctor.getMedicalLicenseNumber());
        System.out.printf("First Name: \"%s\"\n", doctor.getFirstName());
        System.out.printf("Last Name: \"%s\"\n", doctor.getLastName());
        System.out.printf("Specialization: \"%s\"\n", doctor.getSpecialization());
        System.out.printf("Experience years: \"%.1f\"\n", doctor.getExperienceYears());
        System.out.printf("Day of Birth: \"%s\"\n", doctor.getBirthDate());
        System.out.printf("Gender: \"%s\"\n", doctor.getGender() == 'M' ? "Male" : doctor.getGender() == 'F' ? "Female" : Character.toString(doctor.getGender()));
        System.out.printf("Email: \"%s\"\n", doctor.getEmail());
        System.out.printf("Hashed Password: \"%s\"\n", doctor.getPassword());
        System.out.println("");
    }

    public static void displayPatientsList(Scanner inScanner) throws InputMismatchException, SQLException {
        // Enter the doctor's id
        System.out.println("Enter the doctor's id:");
        int doctorId = inScanner.nextInt();
        inScanner.nextLine();
        
        List<User> patientList = DoctorPortalDao.getPatientsByDoctorId(doctorId);

        if (patientList.size() == 0) {
            System.out.printf("Doctor with id \"%d\" does not have patients yet\n", doctorId);
            return;
        } 

        System.out.printf("Doctor \"%d\", patients:\n", doctorId);

        for (User patient : patientList) {
            // Display patient's info
            System.out.println("Patient:");
            System.out.printf("First Name: \"%s\"\n", patient.getFirstName());
            System.out.printf("Last Name: \"%s\"\n", patient.getLastName());
            System.out.printf("Day of Birth: \"%s\"\n", patient.getBirthDate());
            System.out.printf("Gender: \"%s\"\n", patient.getGender() == 'M' ? "Male" : patient.getGender() == 'F' ? "Female" : Character.toString(patient.getGender()));
            System.out.printf("Email: \"%s\"\n", patient.getEmail());
            System.out.printf("Hashed Password: \"%s\"\n", patient.getPassword());
            System.out.println("");
        }
    }

    public static void displayPatientHealthData(Scanner inScanner) throws InputMismatchException, SQLException {
        // Enter the patient id
        System.out.println("Enter the patient id:");
        int patientId = inScanner.nextInt();
        inScanner.nextLine();

        List<HealthData> healthDataList = DoctorPortalDao.getHealthDataByPatientId(patientId);

        System.out.printf("Patient: \"%d\"\n", patientId);

        // Display patient's health data
        for (HealthData healthData : healthDataList) {
            System.out.printf("Weight: \"%s\"\n", healthData.getWeight());
            System.out.printf("Height: \"%s\"\n", healthData.getHeight());
            System.out.printf("Steps: \"%s\"\n", healthData.getSteps());
            System.out.printf("Heart Rate: \"%s\"\n", healthData.getHeartRate());
            System.out.printf("Date: \"%s\"\n", healthData.getDate());
            System.out.println("");
        }
    }
}
