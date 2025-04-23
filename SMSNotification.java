package NotificationsandReminders;

/**
 * Implementation of the Notifiable interface that simulates sending SMS messages.
 */
public class SMSNotification implements Notifiable {

    /**
     * Simulates sending an SMS to the recipient.
     *
     * @param message   The SMS content.
     * @param recipient The recipient's phone number.
     * @throws NotificationException if recipient is invalid.
     */
    @Override
    public void sendNotification(String message, String recipient) throws NotificationException {
        if (recipient == null || recipient.isEmpty()) {
            throw new NotificationException("Invalid recipient for SMS.");
        }
        System.out.println("SMS sent to " + recipient + ": " + message);
    }

    @Override
    public void sendNotification(String message, String recipient, String username, String password) throws NotificationException {
        // SMS doesn't require credentials, so delegate to the default method
        sendNotification(message, recipient);
    }
    @Override
    public boolean requiresCredentials() {
        return false;
    }
}
