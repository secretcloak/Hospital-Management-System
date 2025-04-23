package DoctorPatientInteraction;
import UserManagement.Patient;
import UserManagement.Doctor;

import java.time.LocalDate;

public class Feedback {
    private Patient patient;
    private Doctor doctor;
    private LocalDate date;
    private String comments;
    
    // Constructor
    public Feedback(Patient patient, Doctor doctor, LocalDate date, String comments){
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.comments = comments;
    }
    
    // Setters and Getters
    public void setComments(String comments){
        this.comments = comments;
    }
    public void setDate(LocalDate date){
        this.date = date;
    }
    public String getComments(){
        return comments;
    }
    public LocalDate getDate(){
        return date;
    }
    
    // Feedback toString method
    @Override
    public String toString(){
        return String.format("Doctor's feedback: \n%s", comments);
    }
}
