package model;

// Import required packages
import java.time.LocalDate;

public class Doctor extends User {
    // Define attributes
    private String medicalLicenseNumber;
    private String specialization;
    private double experienceYears;

    // Define constructors
    public Doctor(User user, String medicalLicenseNumber, String specialization, double experienceYears) {
        super(user);

        this.medicalLicenseNumber = medicalLicenseNumber;
        this.specialization = specialization;
        this.experienceYears = experienceYears;
    }

    public Doctor(String firstName, String lastName, LocalDate birthDate, char gender, String email, String password, boolean isDoctor, String medicalLicenseNumber, String specialization, double experienceYears) {
        super(firstName, lastName, birthDate, gender, email, password, isDoctor);

        this.medicalLicenseNumber = medicalLicenseNumber;
        this.specialization = specialization;
        this.experienceYears = experienceYears;
    }

    // Define getter methods
    public String getMedicalLicenseNumber() {
        return this.medicalLicenseNumber;
    }

    public String getSpecialization() {
        return this.specialization;
    }

    public double getExperienceYears() {
        return this.experienceYears;
    }

    // Define setter methods
    public void setMedicalLicenseNumber(String medicalLicenseNumber) {
        this.medicalLicenseNumber = medicalLicenseNumber;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setExperienceYears(double experienceYears) {
        this.experienceYears = experienceYears;
    }
}

