package com.greymatter.sprint.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CalorieBurnedCalculator {

    public static String getCalorieBurned(String weightStr, String heightStr, String stepsCountStr){

        String kcal_burned="0";
        double weight = Double.parseDouble(weightStr); //kg
        double height = Double.parseDouble(heightStr); //cm
        double stepsCount = Double.parseDouble(stepsCountStr);

        double walkingFactor = 0.57;
        double CaloriesBurnedPerMile,strip,stepCountMile,conversationFactor,CaloriesBurned,distance;
        NumberFormat formatter = new DecimalFormat("#0.00");

        CaloriesBurnedPerMile = walkingFactor * (weight * 2.2);
        strip = height * 0.415;
        stepCountMile = 160934.4 / strip;
        conversationFactor = CaloriesBurnedPerMile / stepCountMile;
        CaloriesBurned = stepsCount * conversationFactor;

        kcal_burned = formatter.format(CaloriesBurned) + " kcal";

        distance = (stepsCount * strip) / 100000;

        //System.out.println("Calories burned: "+ formatter.format(CaloriesBurned) + " cal");
        //System.out.println("Distance: " + formatter.format(distance)+ " km");

        return kcal_burned;
    }

}
