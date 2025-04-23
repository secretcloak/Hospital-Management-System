package HealthDataHandling;
import java.util.ArrayList;

public class VitalsDatabase {
    private ArrayList<VitalSign> vitalsList = new ArrayList<>();
    
    // VitalDatabase constructor
    public VitalsDatabase(){
        
    }
    
    // Method to add VitalSign in the list
    public void addVitalSign(VitalSign vital){
        vitalsList.add(vital);
    }
    
    // Method to remove vitalSign from the list
    public void removeVitalSign(VitalSign vital){
        vitalsList.remove(vital);
    }
    
    // Method to get the most recent VitalSign of patient present in syste,
    public VitalSign getLatestVitalSign(String patientID){
        VitalSign latest = null;
        for (VitalSign v : vitalsList){
            if(v.getPatient().getUserID().equals(patientID)){
                latest = v;
            }
        }
        if(latest == null){
            System.out.println("No vital sign with this patient ID found.");
        }
        return latest;
    }
    
    // Method to get the history of Vital signs of a certain patient
    public ArrayList<VitalSign> getVitalSignHistory(String patientID){
        ArrayList <VitalSign> history = new ArrayList<>();
        for(VitalSign v : vitalsList){
            if(v.getPatient().getUserID().equals(patientID)){
                history.add(v);
            }
        }
        return history;
    }
}
