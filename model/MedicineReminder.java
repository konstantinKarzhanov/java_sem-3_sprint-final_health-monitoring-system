package model;

// Import required packages
import java.time.LocalDate;

// Define class
public class MedicineReminder {
    // Define attributes
    private String medicineName;
    private String dosage;
    private String schedule;
    private LocalDate startDate;
    private LocalDate endDate;

    // Define constructors
    public MedicineReminder(String medicineName, String dosage, String schedule, LocalDate startDate, LocalDate endDate) {
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.schedule = schedule;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Define getter methods
    public String getMedicineName() {
        return this.medicineName;
    }

    public String getDosage() {
        return this.dosage;
    }

    public String getSchedule() {
        return this.schedule;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    // Define setter methods
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
