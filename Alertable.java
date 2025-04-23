package EmergencyAlertSystem;
import NotificationsandReminders.NotificationException;

/**
 * Interface defining an entity that can trigger alerts.
 */
public interface Alertable {
    /**
     * Trigger an alert with a given message.
     */
    void triggerAlert(String message) throws NotificationException;
}
