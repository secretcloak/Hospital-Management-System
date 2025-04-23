package NotificationsandReminders;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import org.springframework.stereotype.Component;

/**
 * Implementation of the Notifiable interface that sends email notifications
 * using the JavaMail API.
 */
@Component
public class EmailNotification implements Notifiable {

    // Configuration for Gmail SMTP server
    private String smtpHost = "smtp.gmail.com";
    private String smtpPort = "587";
    private String defaultUsername = System.getenv("SMTP_USERNAME");
    private String defaultPassword = System.getenv("SMTP_PASSWORD");

    /**
     * Sends an email notification to the specified recipient using the provided credentials.
     *
     * @param message   The message content to send.
     * @param recipient The recipient's email address.
     * @param username  The sender's email address (e.g., gmail address).
     * @param password  The sender's App Password for SMTP authentication.
     * @throws NotificationException if the email fails to send.
     */
    @Override
    public void sendNotification(String message, String recipient) throws NotificationException {
        if (recipient == null || recipient.isEmpty()) {
            throw new NotificationException("Invalid recipient for email.");
        }
        if (defaultUsername == null || defaultPassword == null) {
            throw new NotificationException("SMTP_USERNAME or SMTP_PASSWORD environment variables are not set.");
        }
        sendEmail(message, recipient, defaultUsername, defaultPassword);
    }

    @Override
    public void sendNotification(String message, String recipient, String username, String password) throws NotificationException {
        if (recipient == null || recipient.isEmpty()) {
            throw new NotificationException("Invalid recipient for email.");
        }
        if (username == null || username.isEmpty()) {
            throw new NotificationException("Sender email (username) cannot be empty.");
        }
        if (password == null || password.isEmpty()) {
            throw new NotificationException("Sender password cannot be empty.");
        }
        sendEmail(message, recipient, username, password);
    }
    @Override
    public boolean requiresCredentials() {
        return defaultUsername == null || defaultPassword == null;
    }

    private void sendEmail(String message, String recipient, String username, String password) throws NotificationException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Construct the email message
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            mimeMessage.setSubject("RPMS Notification");
            mimeMessage.setText(message);

            // Send email
            Transport.send(mimeMessage);
            System.out.println("Real email sent to " + recipient + ": " + message);
        } catch (MessagingException e) {
            throw new NotificationException("Error sending email: " + e.getMessage());
        }
    }
}