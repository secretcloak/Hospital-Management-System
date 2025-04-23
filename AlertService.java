package EmergencyAlertSystem;
import NotificationsandReminders.Notifiable;
import NotificationsandReminders.NotificationException;
import java.util.List;

/**
 * Handles sending notifications to a list of recipients using a notifier.
 */
public class AlertService {
    private Notifiable notifier; // Mechanism to send notifications
    private List<String> recipients; // List of recipient identifiers (e.g., emails, IDs)
    private String username;
    private String password;

    public AlertService(Notifiable notifier, List<String> recipients) {
        this(notifier, recipients, null, null);
    }
    public AlertService(Notifiable notifier, List<String> recipients, String username, String password) {
        this.notifier = notifier;
        this.recipients = recipients;
        this.username = username;
        this.password = password;
    }

    /**
     * Sends the alert message to all recipients.
     */
    public void sendAlert(String message) throws NotificationException {
    if (notifier == null || recipients == null || recipients.isEmpty()) {
        throw new NotificationException("Notification service misconfigured.");
    }

    for (String recipient : recipients) {
        if (username != null && password != null) {
            notifier.sendNotification(message, recipient, username, password);
        } else {
            notifier.sendNotification(message, recipient); // fallback, still works for SMS
        }
    }
}
}
