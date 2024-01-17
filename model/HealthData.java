package model;

// Import required packages
import java.time.LocalDate;

public class HealthData {
    // Define attributes
    private double weight;
    private double height;
    private int steps;
    private int heartRate;
    private LocalDate date;

    // Define constructors
    public HealthData(double weight, double height, int steps, int heartRate, LocalDate date) {
        this.weight = weight;
        this.height = height;
        this.steps = steps;
        this.heartRate = heartRate;
        this.date = date;
    }

    // Define getter methods
    public double getWeight() {
        return this.weight;
    }

    public double getHeight() {
        return this.height;
    }

    public int getSteps() {
        return this.steps;
    }

    public int getHeartRate() {
        return this.heartRate;
    }

    public LocalDate getDate() {
        return this.date;
    }

    // Define setter methods
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
