package HealthDataHandling;
import UserManagement.Patient;

import java.time.LocalDate;

public class VitalSign {
    private Patient patient;
    private double heartRate;
    private double bloodPressure;
    private double bodyTemperature;
    private double oxygenLevel;
    private LocalDate checkupDate;
    
    // VitalSign constructor
    public VitalSign(Patient patient, double heartRate, double bloodPressure,
            double bodyTemperature, double oxygenLevel, LocalDate checkupDate){
        this.patient = patient;
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.bodyTemperature = bodyTemperature;
        this.oxygenLevel = oxygenLevel;
        this.checkupDate = checkupDate;
    }
    
    // Setters and Getters
    public void setPatient(Patient patient){
        this.patient = patient;
    }
    public void setHeartRate(double heartRate){
        this.heartRate = heartRate;
    }
    public void setBloodPressure(double bloodPressure){
        this.bloodPressure = bloodPressure;
    }
    public void setBodyTemperature(double bodyTemperature){
        this.bodyTemperature = bodyTemperature;
    }
    public void setOxygenLevel(double oxygenLevel){
        this.oxygenLevel = oxygenLevel;
    }
    public void setCheckupDate(LocalDate checkupDate){
        this.checkupDate = checkupDate;
    }
    public Patient getPatient(){
        return patient;
    }
    public double getHeartRate(){
        return heartRate;
    }
    public double getBloodPressure(){
        return bloodPressure;
    }
    public double getBodyTemperature(){
        return bodyTemperature;
    }
    public double getOxygenLevel(){
        return oxygenLevel;
    }
    public LocalDate getCheckupDate(){
        return checkupDate;
    }
    
    // VitalSign toString method
    @Override
    public String toString(){
        return String.format("Patient: %s\nHeart Rate:"
                + " %.2f\nBlood Pressure:"
                + " %.2f\nBody Temperature: %.2f\nOxygen Level:"
                + " %.2f\nCheckup Date: %s\n",
                patient.getName(), heartRate, bloodPressure, bodyTemperature,
                oxygenLevel, checkupDate.toString());
    }
}
