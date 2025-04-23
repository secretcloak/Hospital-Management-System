package AppointmentScheduling;
import UserManagement.Patient;
import UserManagement.Doctor;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AppointmentManager {
    private ArrayList<Appointment> appointments = new ArrayList<>();
    private static int appointmentCounter = 0;
    
    // Constructor
    public AppointmentManager(){
        
    }
    
    // Method to request an appointment from doctor on a certain date
    public void requestAppointment(String appointmentID, LocalDateTime time, Patient p, Doctor d){
        Appointment newAppointment = new Appointment(appointmentID,time, p, d);
        newAppointment.setAppointmentStatus("Requested");
        appointments.add(newAppointment);
        System.out.println("Requested " + newAppointment.getAppointmentID());
    }
    
    // Method to approve the requested appointment of patient
    public void approveAppointment(LocalDateTime time, Patient p, Doctor d){
        for (Appointment a : appointments){
            if (a.getAppointmentTime().equals(time) && a.getPatient().equals(p)
                    && a.getDoctor().equals(d)) {
                if (!a.getDoctor().getAvailableTime().contains(time)) {
                    a.setAppointmentStatus("Rejected");
                    System.out.println("Doctor not available at this time.");
                    return;
                }
                for (Appointment other : appointments) {
                    if (other != a && other.getDoctor().equals(d)
                            && other.getAppointmentTime().equals(time) &&
                            !other.getAppointmentStatus().equals("Canceled")){
                        a.setAppointmentStatus("Rejected");
                        System.out.println("Doctor has a conflicting"
                                + " appointment.");
                        return;
                    }
                }
                a.setAppointmentStatus("Approved");
                System.out.println("Appointment approved: " +
                        a.getAppointmentID());
                return;
            }
        }
        System.out.println("No requested appointment to approve.");
    }
    
    // Method to cancel a certain appointment
    public void cancelAppointment(Appointment app){
        if (appointments.remove(app)) {
            System.out.println("Appointment canceled: "
                    + app.getAppointmentID());
        }
        else {
            System.out.println("Appointment not found.");
        }
    }
}
