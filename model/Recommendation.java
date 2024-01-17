package model;

// Import required packages
import java.util.ArrayList;
import java.util.List;

public class Recommendation {
    // Define attributes
    private static final int[] HEART_RATE_ARR = { 60, 100 };
    private static final int MIN_STEPS = 10000;
    private static final double[] BMI_ARR = { 18.5, 24.9, 29.9 };

    // Define method to generate recommendations based on current health data
    public static List<String> generateRecommendationList(HealthData healthData) {
        List<String> recommendationList = new ArrayList<>();

        // Analyze heart rate
        int heartRate = healthData.getHeartRate();

        if (heartRate < HEART_RATE_ARR[0]) {
            recommendationList.add(
                "Your heart rate is lower than the recommended range. " +
                "Consider increasing your physical activity to improve your cardiovascular health."
            );
        } else if (heartRate > HEART_RATE_ARR[1]) {
            recommendationList.add(
                "Your heart rate is higher than the recommended range. " + 
                "It's important to monitor your cardiovascular health. " + 
                "Consider engaging in relaxation techniques or consulting with a healthcare professional to ensure optimal well-being."
            );
        } else {
            recommendationList.add(
                "Your current heart rate falls within the normal range. " +
                "This is a positive indicator of a healthy cardiovascular system. " + 
                "Keep up with your regular physical activity and maintain a balanced lifestyle to support overall heart health."
            );
        }

        // Analyze steps
        int steps = healthData.getSteps();

        if (steps < MIN_STEPS) {
            recommendationList.add(
                "You're not reaching the recommended daily step count. " +
                "Try to incorporate more walking or other physical activities into your daily routine."
            );
        } else {
            recommendationList.add(
                "Your dedication to physical activity is commendable. " +
                "Keep up the great work, and consider maintaining this active lifestyle for overall health and well-being."
            );
        }

        // Analize BMI (Body Mass Index)
        double bmi = healthData.getWeight() / Math.pow(healthData.getHeight(), 2);
        
        if (bmi < BMI_ARR[0]) {
            recommendationList.add(
                "Being underweight can impact your health. " + 
                "Focus on consuming a variety of foods rich in proteins, fats, and carbohydrates to promote a healthy weight."
            );
        } else if (bmi <= BMI_ARR[1]) {
            recommendationList.add(
                "Great job on maintaining a healthy weight! " + 
                "Keep up with regular exercise and nutritious eating to support your overall well-being"
            );
        } else if (bmi <= BMI_ARR[2]) {
            recommendationList.add(
                "Addressing excess weight is important for your health. " + 
                "Focus on portion control, incorporate more fruits and vegetables, and engage in regular exercise to achieve a healthier weight."
            );
        } else if (bmi > BMI_ARR[2]) {
            recommendationList.add(
                "Obesity can lead to various health issues. " + 
                "Seek support from a healthcare provider to develop a comprehensive plan for weight loss, including dietary changes and regular exercise."
            );
        }

        return recommendationList;
    }
}
