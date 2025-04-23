package EmergencyAlertSystem;
import UserManagement.Patient;
import UserManagement.Doctor;
import NotificationsandReminders.NotificationException;

/**
 * Panic button class that allows a patient to immediately alert a doctor in an emergency.
 */
public class PanicButton implements Alertable {
    private Patient patient; // Patient who presses the panic button
    private Doctor doctor; // Doctor to be notified
    private AlertService notificationService; // Service to send alert

    public PanicButton(Patient patient, Doctor doctor, AlertService notificationService) {
        this.patient = patient;
        this.doctor = doctor;
        this.notificationService = notificationService;
    }

    /**
     * Sends an emergency alert to both notification service and directly to the doctor.
     */
    @Override
    public void triggerAlert(String message) throws NotificationException {
        if (patient == null || doctor == null) {
            throw new NotificationException("Patient or doctor information missing.");
        }
        message = "Emergency! Patient " + patient.getUserID() + " needs immediate attention.";
        notificationService.sendAlert(message); // Notify all recipients
        doctor.receiveAlert(message); // Directly notify the doctor
    }

    /**
     * Method to simulate pressing the panic button.
     */
    public void pressPanicButton() throws NotificationException {
        triggerAlert(""); // Message is constructed inside triggerAlert
    }
}
