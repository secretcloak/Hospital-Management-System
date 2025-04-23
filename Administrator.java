package UserManagement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Administrator extends User {
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<String> systemLogs = new ArrayList<>();
    private LocalDate joiningDate;
    
    // Administrator's Constructor
    public Administrator(String userID, String userName, String contactInfo,
            String gender, LocalDate joiningDate) {
        super(userID, userName, contactInfo, gender);
        this.joiningDate = joiningDate;
    }
    
    // Setter and Getter
    public void setJoiningDate(LocalDate JoiningDate){
        this.joiningDate = joiningDate;
    }
    public LocalDate getJoiningDate(){
        return joiningDate;
    }
    
    // Method to add patient in System Log
    public void addPatient(Patient patient){
        patients.add(patient);
        systemLogs.add("Added patient " + patient.getUserID() +
                " on " + LocalDate.now());
    }
    
    // Method to add doctor in System Log
    public void addDoctor(Doctor doctor){
        doctors.add(doctor);
        systemLogs.add("Added doctor " + doctor.getUserID() +
                " on " + LocalDate.now());
    }
    
    // Method to remove patient from system
    public void removePatient(Patient patient) {
        patients.remove(patient);
        systemLogs.add("Removed patient " + patient.getUserID() +
                " on " + LocalDate.now());
    }
    
    // Method to remove doctor from System
    public void removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
        systemLogs.add("Removed doctor " + doctor.getUserID() +
                " on " + LocalDate.now());
    }
    
    // Method to get System Logs
    public ArrayList<String> getSystemLogs() {
        return new ArrayList<>(systemLogs);
    }
    
    // Administrator toString method
    @Override
    public String toString(){
        return super.toString() + String.format("\nJoining Date: %s",
                joiningDate);
    }
}
