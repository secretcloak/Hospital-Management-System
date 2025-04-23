package NotificationsandReminders;

/**
 * Interface for sending notifications to recipients.
 */
public interface Notifiable {
    void sendNotification(String message, String recipient) throws NotificationException;
    void sendNotification(String message, String recipient, String username, String password) throws NotificationException;
    boolean requiresCredentials();
}
