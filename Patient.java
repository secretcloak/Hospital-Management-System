package UserManagement;
import AppointmentScheduling.AppointmentManager;
import HealthDataHandling.VitalSign;
import HealthDataHandling.VitalsDatabase;
import DoctorPatientInteraction.MedicalHistory;
import DoctorPatientInteraction.Feedback;
import ChatVideoConsultation.ChatClient;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class Patient extends User {
    private LocalDate birthDate;
    private LocalDate admissionDate;
    private LocalDateTime appointmentDateTime;
    private ChatClient c;

    // Patient constructor
    public Patient(String userID, String Name, String contactInfo,
            String gender, LocalDate birthDate) {
        super(userID, Name, contactInfo, gender);
        this.birthDate = birthDate;
    }
    
    // Setters and Getters
    public void setBirthDate(LocalDate birthDate){
        this.birthDate = birthDate;
    }
    public void setAdmissionDate(LocalDate admissionDate){
        this.admissionDate = admissionDate;
    }
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime){
        this.appointmentDateTime = appointmentDateTime;
    }
    public LocalDate getBirthDate(){
        return birthDate;
    }
    public LocalDate getAdmissionDate(){
        return admissionDate;
    }
    public LocalDateTime getAppointmentDateTime(){
        return appointmentDateTime;
    }
    
    public void uploadVitals(VitalSign vital, VitalsDatabase database) {
    database.addVitalSign(vital);
    }
    
    // Method for Patient to view his/her feedback from doctor
    public void viewFeedback(MedicalHistory history) {
        for (Feedback f : history.getFeedbacks()) {
            System.out.println("Feedback: " + f.getComments() +
                    " (Date: " + f.getDate() + ")");
        }
    }
    
    // Method for patient to scedule an appointment with
    // a certain doctor at respective date
    public void scheduleAppointment(String appointmentID, LocalDateTime time, Doctor d,
            AppointmentManager manager) {
        manager.requestAppointment(appointmentID, time, this, d);
    }
    
    public void sendMessage(String receiverID, String message) {
            c.sendMessage(receiverID, message);
    }

    
    // Patient's toString method
    @Override
    public String toString(){
        return super.toString() + String.format("\nBirth Date:"
                + " %s\nAdmission Date: %s\nAppointment Date: %s",
            birthDate, admissionDate != null ? admissionDate : "Not set",
            appointmentDateTime != null ? appointmentDateTime : "Not set");
    }
}
