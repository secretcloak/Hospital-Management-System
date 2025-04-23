package DoctorPatientInteraction;
import UserManagement.Patient;

public class Prescription {
    private String prescriptionID;
    private Patient patient;
    private String medication;
    private String dosage;
    private String schedule;
    
    // Constructor
    public Prescription(String prescriptionID, Patient patient
            , String medication, String dosage, String schedule) {
        this.prescriptionID = prescriptionID;
        this.patient = patient;
        this.medication = medication;
        this.dosage = dosage;
        this.schedule = schedule;
    }
    
    // Setters and Gettters
    public void setPrescriptionID(String prescriptionID){
        this.prescriptionID = prescriptionID;
    }
    public void setPatient(Patient patient){
        this.patient = patient;
    }
    public void setMedication(String medication){
        this.medication = medication;
    }
    public void setDosage(String dosage){
        this.dosage = dosage;
    }
    public void setSchedule(String schedule){
        this.schedule = schedule;
    }
    public String getPrescriptionID(){
        return prescriptionID;
    }
    public Patient getPatient(){
        return patient;
    }
    public String getMedication(){
        return medication;
    }
    public String getDosage(){
        return  dosage;
    }
    public String getSchedule(){
        return schedule;
    }
    
    // Prescription toString method
    @Override
    public String toString() {
        return String.format("Prescription ID: %s\nPatient: %s\nMedication:"
                + " %s\nDosage: %s\nSchedule: %s",
                prescriptionID, patient.getName(), medication, dosage, schedule);
    }
}
