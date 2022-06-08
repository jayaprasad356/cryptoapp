package com.greymatter.sprint.model;

public class StepHistory {

    private String step,calorie,amount,date;

    public StepHistory(String step, String calorie, String amount, String date) {
        this.step = step;
        this.calorie = calorie;
        this.amount = amount;
        this.date = date;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
