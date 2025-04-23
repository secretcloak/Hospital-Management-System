package EmergencyAlertSystem;
import UserManagement.Patient;
import HealthDataHandling.VitalSign;
import NotificationsandReminders.NotificationException;

/**
 * Implementation of EmergencyAlert specifically for vital sign threshold violations.
 */
public class VitalAlert extends EmergencyAlert {

    public VitalAlert(Patient patient, VitalSign vital, AlertService alertService) {
        super(patient, vital, alertService);
    }

    /**
     * Sends the alert message using the configured NotificationService.
     */
    @Override
    public void triggerAlert(String message) throws NotificationException {
        if (alertService == null) {
            throw new NotificationException("Notification service not set.");
        }
        alertService.sendAlert(message);
    }
}
