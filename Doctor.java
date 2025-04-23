package UserManagement;
import AppointmentScheduling.Appointment;
import HealthDataHandling.VitalSign;
import HealthDataHandling.VitalsDatabase;
import DoctorPatientInteraction.Feedback;
import DoctorPatientInteraction.MedicalHistory;
import AppointmentScheduling.AppointmentManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Doctor extends User {
    private LocalDate joiningDate;
    private ArrayList <LocalDateTime> availableTime = new ArrayList<>();
    ArrayList<Appointment> app = new ArrayList<>();    
    // Doctor's Constructor
    public Doctor(String userID, String Name, String contactInfo,
            String gender, LocalDate joiningDate) {
        super(userID, Name, contactInfo, gender);
        this.joiningDate = joiningDate;
        }
    
    // Setters and Getters
    public void setJoiningDate(LocalDate joiningDate){
        this.joiningDate = joiningDate;
    }
    public void setAvailableTime(LocalDateTime time){
        availableTime.add(time);
    }
    public LocalDate getJoiningDate(){
        return joiningDate;
    }
    public ArrayList<LocalDateTime> getAvailableTime(){
        return availableTime;
    }
    
    // Method for doctor to view Vital Signs of patient
    public void viewPatientVitals(String patientID, VitalsDatabase database) {
        VitalSign latest = database.getLatestVitalSign(patientID);
        if (latest != null) System.out.println(latest);
        else System.out.println("No vitals found for patient " + patientID);
    }
    
    // Method for doctor to provide feedback to a certain patient
    public void provideFeedback(Feedback feedback, MedicalHistory history) {
        history.addFeedback(feedback);
    }
    
    // Method for doctor to approve or deny appointmentss
    public void manageAppointment(LocalDateTime time, Patient p, AppointmentManager manager, String action) {
        if (action.equals("approve")) {
            manager.approveAppointment(time, p, this);
        }
        else if (action.equals("cancel")) {
            System.out.println("Cancellation not fully implemented in this demo.");
        }
    }
    
    // Method for doctor to receive alerts
    public void receiveAlert(String message) {
    System.out.println("Dr. " + getName() + " received alert: " + message);
}
    
    // Doctor's toString method
    @Override
    public String toString() {
        return super.toString() + String.format("\nJoining Date:"
                + " %s\nAvailable Time: %s",
            joiningDate, availableTime);
    }
}
