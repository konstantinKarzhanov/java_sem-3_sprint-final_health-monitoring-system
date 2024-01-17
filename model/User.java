package model;

// Import required packages
import java.time.LocalDate;

public class User {
    // Define attributes
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private char gender;
    private String email;
    private String password;
    private boolean isDoctor;

    // Define constructors
    public User(String firstName, String lastName, LocalDate birthDate, char gender, String email, String password, boolean isDoctor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = Character.toUpperCase(gender);
        this.email = email;
        this.password = password;
        this.isDoctor = isDoctor;
    }

    // Define copy constructor
    public User(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthDate = user.getBirthDate();
        this.gender = user.getGender();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.isDoctor = user.isDoctor();
    }

    // Define getter methods
    public String getFirstName() {
        return this.firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public char getGender() {
        return this.gender;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isDoctor() {
        return this.isDoctor;
    }

    // Define setter methods
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(char gender) {
        this.gender = Character.toUpperCase(gender);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsDoctor(boolean doctor) {
        this.isDoctor = doctor;
    }
}
