package com.simats.pcos;

public class GetPatientFeedbackResponse {
    private int id;
    private String patient_name;
    private String meal_plan;
    private float meal_percentage_completed;
    private String exercise_plan;
    private float exercise_percentage_completed;
    private String meditation_plan;
    private float medication_percentage_completed;
    private String created_on;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getMeal_plan() {
        return meal_plan;
    }

    public void setMeal_plan(String meal_plan) {
        this.meal_plan = meal_plan;
    }

    public float getMeal_percentage_completed() {
        return meal_percentage_completed;
    }

    public void setMeal_percentage_completed(float meal_percentage_completed) {
        this.meal_percentage_completed = meal_percentage_completed;
    }

    public String getExercise_plan() {
        return exercise_plan;
    }

    public void setExercise_plan(String exercise_plan) {
        this.exercise_plan = exercise_plan;
    }

    public float getExercise_percentage_completed() {
        return exercise_percentage_completed;
    }

    public void setExercise_percentage_completed(float exercise_percentage_completed) {
        this.exercise_percentage_completed = exercise_percentage_completed;
    }

    public String getMeditation_plan() {
        return meditation_plan;
    }

    public void setMeditation_plan(String meditation_plan) {
        this.meditation_plan = meditation_plan;
    }

    public float getMedication_percentage_completed() {
        return medication_percentage_completed;
    }

    public void setMedication_percentage_completed(float medication_percentage_completed) {
        this.medication_percentage_completed = medication_percentage_completed;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }
}

