package NotificationsandReminders;

import org.springframework.stereotype.Component;
import AppointmentScheduling.Appointment;
import DoctorPatientInteraction.Prescription;

@Component
public class ReminderService {
    private final EmailNotification emailNotification;
    private String smtpUsername = System.getenv("SMTP_USERNAME");
    private String smtpPassword = System.getenv("SMTP_PASSWORD");

    public ReminderService(EmailNotification emailNotification) {
        this.emailNotification = emailNotification;
    }

    public EmailNotification getEmailNotification() {
        return emailNotification;
    }

    public void sendAppointmentReminder() throws NotificationException {
        if (smtpUsername == null || smtpPassword == null) {
            throw new NotificationException("SMTP_USERNAME or SMTP_PASSWORD environment variables are not set for reminders.");
        }
        // Example: Send reminders to all appointments
        // Replace with actual logic
        emailNotification.sendNotification("Appointment reminder", "recipient@example.com", smtpUsername, smtpPassword);
    }

    public void sendMedicationReminder() throws NotificationException {
        if (smtpUsername == null || smtpPassword == null) {
            throw new NotificationException("SMTP_USERNAME or SMTP_PASSWORD environment variables are not set for reminders.");
        }
        // Example: Send medication reminders
        // Replace with actual logic
        emailNotification.sendNotification("Medication reminder", "recipient@example.com", smtpUsername, smtpPassword);
    }

    public void addAppointment(Appointment appointment) {
    }

    public void addPrescription(Prescription prescription) {
    }
}