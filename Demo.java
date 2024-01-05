import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        User u1 = new User("testFname1", "testLname1", LocalDate.of(2023, 12, 20), 'M', "test1@test.com", "avadakedavra", false);
        User u2 = new User("testFname2", "testLname2", LocalDate.of(2023, 12, 20), 'F', "test2@test.com", "avadakedavra", false);
        User u3 = new User("testFname3", "testLname3", LocalDate.of(2023, 12, 20), 'M', "test3@test.com", "avadakedavra", false);
        
        // System.out.println(u1.getFirstName());
        // System.out.println(u1.getLastName());
        // System.out.println(u1.getBirthDate());
        // System.out.println(u1.getGender());
        // System.out.println(u1.getEmail());
        // System.out.println(u1.getPassword());
        // System.out.println(u1.isDoctor());

        // -----------------------------------
        // Test User Data Access Object
        // -----------------------------------
        UserDao uDAO = new UserDao();

        // createUser
        // UserDao.createUser(u1);
        // UserDao.createUser(u2);
        // UserDao.createUser(u3);
        
        // getUserById    
        // System.out.println(uDAO.getUserById(1).getFirstName());
        // System.out.println(uDAO.getUserById(2).getLastName());
        // System.out.println(uDAO.getUserById(3).getBirthDate());

        // getUserByEmail
        // System.out.println(uDAO.getUserByEmail("test1@test.com").getGender());
        // System.out.println(uDAO.getUserByEmail("test2@test.com").getGender());
        // System.out.println(uDAO.getUserByEmail("test3@test.com").getGender());

        // updateUser
        // System.out.println(UserDao.updateUser(1, new User("updFname", "updLname", LocalDate.of(1999, 1, 10), 'U', "upd@test.com", "upd", true)));

        // deleteUser
        // System.out.println(UserDao.deleteUser(1));

        // verifyPassword
        // System.out.println(UserDao.verifyPassword("test2@test.com", "avadakedavra"));

        // -----------------------------------------
        // Test HealthData Data Access Object
        // -----------------------------------------
        // HealthDataDao hDAO = new HealthDataDao();

        // createHealthData
        // HealthDataDao.createHealthData(u1, new HealthData(100.5, 1.7, 5000, 50, LocalDate.of(2023, 12, 20)));
        // HealthDataDao.createHealthData(u2, new HealthData(70.5, 1.75, 15000, 75, LocalDate.of(2023, 12, 20)));
        // HealthDataDao.createHealthData(u3, new HealthData(30.5, 1.70, 13000, 300, LocalDate.of(2023, 12, 20)));
        
        // getHealthDataById
        // System.out.println(HealthDataDao.getHealthDataById(1).getSteps());
        // System.out.println(HealthDataDao.getHealthDataById(2).getHeartRate());
        // System.out.println(HealthDataDao.getHealthDataById(3).getWeight());

        // getHealthDataByUserId
        // hDAO.getHealthDataByUserId(1).forEach(item -> {
        //   System.out.println(item.getSteps());
        // });

        // updateHealthData
        // System.out.println(HealthDataDao.updateHealthData(2, u3, new HealthData(70.55, 1.71, 13000, 77, LocalDate.of(2023, 12, 22))));
    
        // deleteHealthData
        // System.out.println(HealthDataDao.deleteHealthData(2));

        // --------------------------------------------
        // Test Doctor Portal Data Access Object
        // --------------------------------------------
        // DoctorPortalDao dpDAO = new DoctorPortalDao();

        // getDoctorById        
        // System.out.println(dpDAO.getDoctorById(1).getMedicalLicenseNumber());
        // System.out.println(dpDAO.getDoctorById(1).getSpecialization());
        // System.out.println(dpDAO.getDoctorById(1).getExperienceYears());
        // System.out.println(dpDAO.getDoctorById(1).getEmail());

        // getPatientsByDoctorId
        // dpDAO.getPatientsByDoctorId(1).forEach(item -> {
        //   System.out.println(item.getFirstName() + " " + item.getLastName());
        // });

        // getHealthDataByPatientId
        // dpDAO.getHealthDataByPatientId(2).forEach(item -> {
        //     System.out.println(item.getHeartRate());
        //     System.out.println(item.getSteps());
        // });

        // --------------------------------------
        // Medicine Reminder Data Access Object
        // --------------------------------------
        // MedicineReminderDao mrDAO = new MedicineReminderDao();
        
        // createReminder
        // System.out.println(mrDAO.createReminder(u2, new MedicineReminder("testMedicine1", "130 mg", "4 times/day", LocalDate.now(), LocalDate.of(2024, 01, 30))));
        // System.out.println(mrDAO.createReminder(u3, new MedicineReminder("testMedicine", "120 mg", "3 times/day", LocalDate.now(), LocalDate.of(2024, 01, 30))));
    
        // getRemindersById
        // System.out.println(mrDAO.getReminderById(2).getSchedule());

        // getRemindersByUserId
        // System.out.println(mrDAO.getRemindersByUserId(2).size());
        // System.out.println(mrDAO.getRemindersByUserId(2).get(0).getDosage());
        // mrDAO.getRemindersByUserId(2).forEach(item -> {
        //     System.out.println(item.getMedicineName());
        // });

        // getDueReminders
        // System.out.println(mrDAO.getDueReminders(2).get(0).getMedicineName());

        // updateReminder
        // System.out.println(mrDAO.updateReminder(2, u2, new MedicineReminder("updTestMedicine", "990 mg", "99 times/day", LocalDate.of(2022, 01, 30), LocalDate.of(2023, 12, 20))));

        // deleteReminder
        // System.out.println(mrDAO.deleteReminder(2));


        // ----------------------------------------
        // Test Recommendation System
        // ----------------------------------------
        // RecommendationSystem rs1 = new RecommendationSystem();
        
        // List<String> r1 = rs1.generateRecommendations(HealthDataDao.getHealthDataById(3));
        
        // System.out.println(r1.size());

        // r1.forEach(item -> {
        //     System.out.println(item + "\n");
        // });

        // ----------------------------------------
        // Test Recommendations Data Access Object
        // ----------------------------------------
        // RecommendationDao rDAO = new RecommendationDao();

        // createRecommendation
        // System.out.println(rDAO.createRecommendation(u3, r1));

        // ----------------------------------------
        // Create recommendations for mock data
        // ----------------------------------------
        // RecommendationSystem rs1 = new RecommendationSystem();
        // RecommendationDao rDAO = new RecommendationDao();

        // // createRecommendation
        // for (int i = 1; i <= 15; i++) {
        //     System.out.println(rDAO.createRecommendation(uDAO.getUserById(i), rs1.generateRecommendations(HealthDataDao.getHealthDataById(i))));
        // }
    }
}
