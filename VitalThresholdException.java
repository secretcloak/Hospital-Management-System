package EmergencyAlertSystem;

/**
 * Custom exception thrown when a patient's vital signs are outside of safe thresholds.
 */
public class VitalThresholdException extends Exception {
    public VitalThresholdException(String message) {
        super(message);
    }
}
