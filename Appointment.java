package AppointmentScheduling;
import UserManagement.Patient;
import UserManagement.Doctor;

import java.time.LocalDateTime;

public class Appointment {
     private String appointmentID;
    private LocalDateTime appointmentTime;
    private Patient patient;
    private Doctor doctor;
    private String appointmentStatus;
    
    // Appointment constructor
    public Appointment(String appointmentID, LocalDateTime appointmentTime, Patient patient, Doctor doctor){
        this.appointmentID = appointmentID;
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentStatus = "Pending";
    }
    
    // Setters and Getters
    public void setAppointmentID(String appointmentID){
        this.appointmentID = appointmentID;
    }
    public void setAppointmentTime(LocalDateTime appointmentTime){
        this.appointmentTime = appointmentTime;
    }
    public void setPatient(Patient patient){
        this.patient = patient;
    }
    public void setDoctor(Doctor doctor){
        this.doctor = doctor;
    }
    public void setAppointmentStatus(String appointmentStatus){
        this.appointmentStatus = appointmentStatus;
    }
    public String getAppointmentID(){
        return appointmentID;
    }
    public LocalDateTime getAppointmentTime(){
        return appointmentTime;
    }
    public Patient getPatient(){
        return patient;
    }
    public Doctor getDoctor(){
        return doctor;
    }
    public String getAppointmentStatus(){
        return appointmentStatus;
    }
    
    // Appointment toString method
    @Override
    public String toString() {
        return String.format("\nAppointment ID: %s\nAppointment Time:"
                + " %s\nPatient: %s\nDoctor: %s\nStatus: %s",
                appointmentID, appointmentTime, patient.getName(),
                doctor.getName(), appointmentStatus);
    }
}
