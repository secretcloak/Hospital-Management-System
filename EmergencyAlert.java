package EmergencyAlertSystem;
import HealthDataHandling.VitalSign;
import UserManagement.Patient;
import NotificationsandReminders.NotificationException;

/**
 * Abstract base class for emergency alerts triggered by abnormal vital signs.
 */
public abstract class EmergencyAlert implements Alertable {
    protected VitalSign vital; // The vital signs associated with the alert
    protected AlertService alertService; // Service used to send notifications
    protected Patient patient; // Patient whose vitals are being monitored

    public EmergencyAlert(Patient patient, VitalSign vital, AlertService alertService) {
        this.patient = patient;
        this.vital = vital;
        this.alertService = alertService;
    }

    public VitalSign getVital() { return vital; }

    public Patient getPatient() { return patient; }

    /**
     * Checks if the provided vital signs are within safe thresholds.
     */
    public static boolean isWithinThreshold(VitalSign vital) {
        if (vital == null) return true;
        return (vital.getHeartRate() >= 60 && vital.getHeartRate() <= 100) &&
               (vital.getBloodPressure() >= 90 && vital.getBloodPressure() <= 140) &&
               (vital.getBodyTemperature() >= 36.1 && vital.getBodyTemperature() <= 37.2) &&
               (vital.getOxygenLevel() >= 95);
    }

    /**
     * Checks the patient's vitals and triggers an alert if they are out of range.
     */
    public void checkVitals() throws VitalThresholdException, NotificationException {
        if (vital == null || patient == null) {
            throw new VitalThresholdException("Vital or patient information missing.");
        }
        if (!isWithinThreshold(vital)) {
            String message = "Alert! Patient " + patient.getUserID() + "'s vital signs are abnormal: " +
                             "HR=" + vital.getHeartRate() + ", BP=" + vital.getBloodPressure() +
                             ", Temp=" + vital.getBodyTemperature() + ", O2=" + vital.getOxygenLevel();
            triggerAlert(message);
        }
    }
}
