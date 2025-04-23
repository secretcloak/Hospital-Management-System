package DoctorPatientInteraction;
import UserManagement.Patient;

import java.util.ArrayList;

public class MedicalHistory {
    private Patient patient;
    private ArrayList<Feedback> feedbacks = new ArrayList<>();
    private ArrayList<Prescription> prescriptions = new ArrayList<>();
    
    // MedicalHistory Constructor
    public MedicalHistory(Patient patient) {
        this.patient = patient;
    }
    
    // Method to add feedback in records list
    public void addFeedback(Feedback feedback){
        feedbacks.add(feedback);
    }
    
    // Method to add prescription in records list
    public void addPrescription(Prescription prescription){
        prescriptions.add(prescription);
    }
    
    // Method to get feedback from records list
    public ArrayList<Feedback> getFeedbacks(){
        return feedbacks;
    }
    
    // Method to get prescription from records list
    public ArrayList<Prescription> getPrescriptions(){
        return prescriptions;
    }
    
    // MedicalHistory toString method
    @Override
    public String toString() {
        return String.format("Medical History for Patient %s:\nFeedbacks:"
                + " %s\nPrescriptions: %s",
                patient.getName(), feedbacks, prescriptions);
    }
}
