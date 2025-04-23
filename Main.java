package Main;

import UserManagement.Administrator;
import UserManagement.Doctor;
import UserManagement.Patient;
import HealthDataHandling.VitalSign;
import HealthDataHandling.VitalsDatabase;
import AppointmentScheduling.Appointment;
import AppointmentScheduling.AppointmentManager;
import DoctorPatientInteraction.Feedback;
import DoctorPatientInteraction.Prescription;
import DoctorPatientInteraction.MedicalHistory;
import ChatVideoConsultation.ChatService;
import ChatVideoConsultation.ChatServer;
import ChatVideoConsultation.ChatMessage;
import ChatVideoConsultation.VideoCall;
import EmergencyAlertSystem.EmergencyAlert;
import EmergencyAlertSystem.AlertService;
import EmergencyAlertSystem.VitalAlert;
import EmergencyAlertSystem.PanicButton;
import NotificationsandReminders.ReminderService;
import NotificationsandReminders.EmailNotification;
import NotificationsandReminders.NotificationException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Administrator> admins = new ArrayList<>();
    private static ArrayList<Doctor> doctors = new ArrayList<>();
    private static ArrayList<Patient> patients = new ArrayList<>();
    private static VitalsDatabase vitalsDb = new VitalsDatabase();
    private static AppointmentManager apptMgr = new AppointmentManager();
    private static ChatService chatServer = new ChatServer();
    private static ReminderService reminderService = new ReminderService(new EmailNotification());
    private static AlertService notificationService;

    static {
        List<String> defaultRecipients = new ArrayList<>();
        defaultRecipients.add("admin@gmail.com");
        notificationService = new AlertService(new EmailNotification(), defaultRecipients);
    }
    private static class AdminCredentials {
        String email;
        String password;

        AdminCredentials(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    public static void main(String[] args) throws NotificationException {
        System.out.println("=== Welcome to Remote Patient Monitoring System ===");
        while (true) {
            Object currentUser = selectUserRole();
            if (currentUser == null) {
                System.out.println("Thanks for using RPMS. Stay healthy!");
                break;
            }
            if (currentUser instanceof Administrator) {
                handleAdministratorMenu((Administrator) currentUser);
            } else if (currentUser instanceof Doctor) {
                handleDoctorMenu((Doctor) currentUser);
            } else if (currentUser instanceof Patient) {
                handlePatientMenu((Patient) currentUser);
            }
        }
        scanner.close();
    }

    private static Object selectUserRole() {
        System.out.println("\n Select your role:");
        System.out.println("1. Administrator");
        System.out.println("2. Doctor️");
        System.out.println("3. Patient");
        System.out.println("4. Exit");
        System.out.print("Enter role number:");
        int role = getIntInput();
        switch (role) {
            case 1: return selectOrCreateAdministrator(scanner, admins);
            case 2: return selectOrCreateDoctor();
            case 3: return selectOrCreatePatient();
            case 4: return null;
            default:
                System.out.println("Invalid role. Try again!");
                return selectUserRole();
        }
    }

    private static int getIntInput() {
        try {
            int input = scanner.nextInt();
            scanner.nextLine();
            return input;
        } catch (Exception e) {
            System.out.println("Please enter a valid number!");
            scanner.nextLine();
            return -1;
        }
    }

    // Updated Administrator Selection/Creation
    private static Administrator selectOrCreateAdministrator(Scanner scanner, ArrayList<Administrator> admins) {
        if (admins.isEmpty()) {
            System.out.println("No administrators exist yet. Creating one...");
            Administrator newAdmin = createAdministrator(scanner);
            admins.add(newAdmin);
            return newAdmin;
        }

        System.out.println("Available Administrators:");
        for (Administrator a : admins) {
            System.out.println(a.getUserID() + ": " + a.getName());
        }
        System.out.println("Enter 'NEW' to create a new administrator or an existing ID to select:");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("NEW")) {
            Administrator newAdmin = createAdministrator(scanner);
            admins.add(newAdmin);
            return newAdmin;
        }

        for (Administrator a : admins) {
            if (a.getUserID().equals(choice)) return a;
        }
        System.out.println("Admin not found. Try again.");
        return selectOrCreateAdministrator(scanner, admins); // Retry
    }

    // New Method to Create Administrator
    private static Administrator createAdministrator(Scanner scanner) {
        System.out.println("\n=== Create a New Administrator ===");
        System.out.print("Enter Administrator ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Administrator Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Administrator Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Administrator Gender: ");
        String gender = scanner.nextLine();
        return new Administrator(id, name, email, gender, LocalDate.now());
    }

    // Administrator Menu
    private static void handleAdministratorMenu(Administrator admin) {
        while (true) {
            System.out.println("\n=== Administrator Menu ===");
            System.out.println("1. Add Doctor️");
            System.out.println("2. Add Patient");
            System.out.println("3. View System Logs");
            System.out.println("4. Send Reminders");
            System.out.println("5. Back ");
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    Doctor doctor = createDoctor();
                    admin.addDoctor(doctor);
                    doctors.add(doctor);
                    System.out.println("Doctor added: " + doctor.getName());
                    break;
                case 2:
                    Patient patient = createPatient();
                    admin.addPatient(patient);
                    patients.add(patient);
                    System.out.println("Patient added: " + patient.getName());
                    break;
                case 3:
                    System.out.println("\nSystem Logs:");
                    for (String log : admin.getSystemLogs()) {
                        System.out.println(log);
                    }
                    break;
                case 4:
                    try {
                        reminderService.sendAppointmentReminder();
                        reminderService.sendMedicationReminder();
                        System.out.println("Reminders sent successfully!");
                    } catch (NotificationException e) {
                        System.out.println("Error sending reminders: " + e.getMessage());
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // Doctor Menu
    private static void handleDoctorMenu(Doctor doctor) throws NotificationException {
        while (true) {
            System.out.println("\n=== Doctor Menu ===");
            System.out.println("1. View Patient Vitals");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. Provide Feedback & Prescription️");
            System.out.println("4. Check Messages");
            System.out.println("5. Start Video Call");
            System.out.println("6. Send Email️");
            System.out.println("7. Back");
            int choice = getIntInput();
            if (patients.isEmpty() && choice != 6) {
                System.out.println("No patients available. Ask an admin to add some!");
                continue;
            }
            switch (choice) {
                case 1:
                    Patient vitalsPatient = selectPatient();
                    System.out.println("Viewing vitals for " + vitalsPatient.getName() + ":");
                    doctor.viewPatientVitals(vitalsPatient.getUserID(), vitalsDb);
                    break;
                case 2:
                    Patient apptPatient = selectPatient();
                    scheduleAppointment(doctor, apptPatient);
                    break;
                case 3:
                    Patient feedbackPatient = selectPatient();
                    provideFeedbackAndPrescription(doctor, feedbackPatient);
                    break;
                case 4:
                    Patient chatPatient = selectPatient();
                    List<ChatMessage> messages = chatServer.getMessagesBetween(chatPatient.getUserID(), doctor.getUserID());
                    if (messages.isEmpty()) {
                        System.out.println("No new messages.");
                    } else {
                        System.out.println("New messages:");
                        for (ChatMessage msg : messages) {
                            System.out.println(msg);
                        }
                    }
                    break;

                case 5:
                    VideoCall videoCall = new VideoCall(doctor, selectPatient());
                    System.out.println("Video call started: " + videoCall.generateMeetingLink());
                    break;
                case 6:
                    sendEmailFromMenu();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // Patient Menu
    private static void handlePatientMenu(Patient patient) throws NotificationException {
        while (true) {
            System.out.println("\n=== Patient Menu ===");
            System.out.println("1. Upload Vitals");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. View Medical History");
            System.out.println("4. Send Message to Doctor");
            System.out.println("5. Start Video Call");
            System.out.println("6. Press Panic Button");
            System.out.println("7. Send email️");
            System.out.println("8. Back");
            int choice = getIntInput();
            if (doctors.isEmpty() && choice != 7) {
                System.out.println("No doctors available. Ask an admin to add some!");
                continue;
            }
            switch (choice) {
                case 1:
                    uploadVitals(patient);
                    break;
                case 2:
                    Doctor apptDoctor = selectDoctor();
                    scheduleAppointment(apptDoctor, patient);
                    break;
                case 3:
                    MedicalHistory history = new MedicalHistory(patient);
                    patient.viewFeedback(history);
                    System.out.println("Medical History:\n" + history);
                    break;
                case 4:
                    Doctor msgDoctor = selectDoctor();
                    System.out.println("Enter message for Dr. " + msgDoctor.getName() + ":");
                    String message = scanner.nextLine();
                    // Create a ChatMessage object first
                    ChatMessage chatMessage = new ChatMessage(patient.getUserID(), msgDoctor.getUserID(), message);
                    chatServer.sendMessage(chatMessage);
                    System.out.println("Message sent!");
                    break;
                case 5:
                    VideoCall videoCall = new VideoCall(selectDoctor(), patient);
                    System.out.println("Video call started: " + videoCall.generateMeetingLink());
                    break;
                case 6:
                    try {
                        Doctor doctor = selectDoctor();
                        AdminCredentials creds = getAdminCredentials();
                        if (creds == null) {
                            System.out.println("Cannot send panic alert: Administrator credentials not provided.");
                            break;
                        }
                        List<String> recipients = new ArrayList<>();
                        recipients.add(doctor.getContactInfo());
                        EmailNotification emailNotification = new EmailNotification();
                        String mesSage = "Emergency! Patient " + patient.getUserID() + " needs immediate attention.";
                        emailNotification.sendNotification(mesSage, doctor.getContactInfo(), creds.email, creds.password);
    // Call pressPanicButton for consistency, ignoring environment variable errors
                        try {
                            AlertService alertService = new AlertService(emailNotification, recipients, creds.email, creds.password);
                            PanicButton panic = new PanicButton(patient, doctor, alertService);
                            panic.pressPanicButton();
                        }
                        catch (NotificationException e) {
                            if (!e.getMessage().equals("SMTP_USERNAME or SMTP_PASSWORD not set.")) {
                                throw e;
                            }
                        }
                        System.out.println("Panic alert sent!");
                    } catch (NotificationException e) {
                        System.out.println("Error triggering panic: " + e.getMessage());
                    }
                    break;
                case 7:
                    sendEmailFromMenu();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // Helper Methods
    private static Doctor selectOrCreateDoctor() {
        if (doctors.isEmpty()) {
            Doctor newDoctor = createDoctor();
            doctors.add(newDoctor);
            return newDoctor;
        }
        return selectDoctor();
    }

    private static Patient selectOrCreatePatient() {
        if (patients.isEmpty()) {
            Patient newPatient = createPatient();
            patients.add(newPatient);
            return newPatient;
        }
        return selectPatient();
    }

    private static Doctor createDoctor() {
        System.out.println("\n=== Add a Doctor️ ===");
        System.out.print("Enter Doctor ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();
        return new Doctor(id, name, email, gender, LocalDate.now());
    }

    private static Patient createPatient() {
        System.out.println("\n=== Add a Patient ===");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter Birth Year (YYYY): ");
        int year = getIntInput();
        return new Patient(id, name, email, gender, LocalDate.of(year, 1, 1));
    }

    private static void uploadVitals(Patient patient) {
        VitalSign vital = createVitalSign(patient);
        patient.uploadVitals(vital, vitalsDb);
        System.out.println("Vitals uploaded successfully!");
        try {
            Doctor doctor = selectDoctor();
            AdminCredentials creds = getAdminCredentials();

            if (creds == null) {
                System.out.println("Cannot send vital alert: Administrator credentials not provided.");
                return;
            }
            List<String> recipients = new ArrayList<>();
            recipients.add(doctor.getContactInfo());
            EmailNotification emailNotification = new EmailNotification();
            if (!EmergencyAlert.isWithinThreshold(vital)) {
                String message = "Alert! Patient " + patient.getUserID() + "'s vital signs are abnormal: " +
                        "HR=" + vital.getHeartRate() + ", BP=" + vital.getBloodPressure() +
                        ", Temp=" + vital.getBodyTemperature() + ", O2=" + vital.getOxygenLevel();
                emailNotification.sendNotification(message, doctor.getContactInfo(), creds.email, creds.password);
            }
            // Call checkVitals for logging or side effects, ignoring environment variable errors
            try {
                AlertService alertService = new AlertService(emailNotification, recipients, creds.email, creds.password);
                VitalAlert alert = new VitalAlert(patient, vital, alertService);
                alert.checkVitals();
            }
            catch (NotificationException e) {
                if (!e.getMessage().equals("SMTP_USERNAME or SMTP_PASSWORD not set.")) {
                    throw e;
                }
            }
        }
        catch (Exception e) {
            System.out.println(" Error checking vitals: " + e.getMessage());
        }
    }

    private static void scheduleAppointment(Doctor doctor, Patient patient) {
        String apptID = getAppointmentID();
        LocalDateTime apptTime = getAppointmentTime();
        doctor.setAvailableTime(apptTime);
        patient.scheduleAppointment(apptID, apptTime, doctor, apptMgr);
        doctor.manageAppointment(apptTime, patient, apptMgr, "approve");
        reminderService.addAppointment(new Appointment(apptID, apptTime, patient, doctor));
        System.out.println("Appointment scheduled!");
    }

    private static void provideFeedbackAndPrescription(Doctor doctor, Patient patient) {
        MedicalHistory history = new MedicalHistory(patient);
        String comments = getFeedbackComments();
        Feedback feedback = new Feedback(patient, doctor, LocalDate.now(), comments);
        doctor.provideFeedback(feedback, history);
        System.out.println("Feedback provided:\n" + feedback);
        Prescription prescription = createPrescription(patient);
        history.addPrescription(prescription);
        reminderService.addPrescription(prescription);
        System.out.println("Prescription issued:\n" + prescription);
    }

    private static VitalSign createVitalSign(Patient patient) {
        System.out.println("\n=== Upload Vitals ===");
        System.out.print("Enter Heart Rate: ");
        double heartRate = scanner.nextDouble();
        System.out.print("Enter Blood Pressure: ");
        double bloodPressure = scanner.nextDouble();
        System.out.print("Enter Body Temperature: ");
        double bodyTemp = scanner.nextDouble();
        System.out.print("Enter Oxygen Level: ");
        double oxygenLevel = scanner.nextDouble();
        scanner.nextLine();
        return new VitalSign(patient, heartRate, bloodPressure, bodyTemp, oxygenLevel, LocalDate.now());
    }

    private static String getAppointmentID() {
        System.out.print("Enter Appointment ID: ");
        return scanner.nextLine();
    }

    private static LocalDateTime getAppointmentTime() {
        System.out.print("Enter Days from Now (e.g., 1 for tomorrow): ");
        int days = getIntInput();
        System.out.print("Enter Hour (0-23): ");
        int hour = getIntInput();
        return LocalDateTime.now().plusDays(days).withHour(hour).withMinute(0);
    }

    private static String getFeedbackComments() {
        System.out.print("Enter Feedback Comments: ");
        return scanner.nextLine();
    }

    private static Prescription createPrescription(Patient patient) {
        System.out.print("Enter Prescription ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Medication: ");
        String medication = scanner.nextLine();
        System.out.print("Enter Dosage: ");
        String dosage = scanner.nextLine();
        System.out.print("Enter Schedule (e.g., Once daily): ");
        String schedule = scanner.nextLine();
        return new Prescription(id, patient, medication, dosage, schedule);
    }

    private static void sendEmailFromMenu() {
        System.out.println("\n=== Send Email ===");
        System.out.print("Enter your Gmail address (sender email): ");
        String senderEmail = scanner.nextLine();
        System.out.print("Enter your Gmail App Password: ");
        String senderPassword = scanner.nextLine();
        System.out.print("Enter recipient email address: ");
        String recipient = scanner.nextLine();
        System.out.print("Do you want to include patient vitals? (yes/no): ");
        String includeVitals = scanner.nextLine();
        String message;
        if (includeVitals.equalsIgnoreCase("yes")) {
            Patient patient = selectPatient();
            List<VitalSign> vitals = vitalsDb.getVitalSignHistory(patient.getUserID());
            StringBuilder vitalsText = new StringBuilder("Vitals for " + patient.getName() + ":\n");
            if (vitals.isEmpty()) {
                vitalsText.append("No vitals recorded.");
            }
            else {
                for (VitalSign vital : vitals) {
                    vitalsText.append(vital.toString()).append("\n");
                }
            }
            message = vitalsText.toString();
        }
        else {
            System.out.print("Enter message content: ");
            message = scanner.nextLine();
        }
        try {
            reminderService.getEmailNotification().sendNotification(message, recipient, senderEmail, senderPassword);
            System.out.println("Email sent successfully to " + recipient);
        }
        catch (NotificationException e) {
            System.out.println(" Error sending email: " + e.getMessage());
        }
    }

    private static AdminCredentials getAdminCredentials() {
        if (admins.isEmpty()) {
            System.out.println("No administrators available. Please add an administrator first.");
            return null;
        }
        System.out.println("Select an Administrator by ID:");
        for (Administrator admin : admins) {
            System.out.println(admin.getUserID() + ": " + admin.getName());
        }
        System.out.print("Enter Administrator ID: ");
        String adminId = scanner.nextLine();
        Administrator selected = null;
        for (Administrator admin : admins) {
            if (admin.getUserID().equals(adminId)) {
                selected = admin;
                break;
            }
        }
        if (selected == null) {
            System.out.println("Invalid Administrator ID!");
            return null;
        }

        System.out.print("Enter Administrator's Gmail address: ");
        String email = scanner.nextLine();
        System.out.print("Enter Administrator's Gmail App Password: ");
        String password = scanner.nextLine();
        return new AdminCredentials(email, password);
    }


    private static Patient selectPatient() {
        System.out.println("Select a Patient by ID:");
        for (Patient p : patients) {
            System.out.println(p.getUserID() + ": " + p.getName());
        }
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine();
        for (Patient p : patients) {
            if (p.getUserID().equals(id)) return p;
        }
        System.out.println("Patient not found. Try again!");
        return selectPatient();
    }

    private static Doctor selectDoctor() {
        System.out.println("Select a Doctor by ID:");
        for (Doctor d : doctors) {
            System.out.println(d.getUserID() + ": " + d.getName());
        }
        System.out.print("Enter Doctor ID: ");
        String id = scanner.nextLine();
        for (Doctor d : doctors) {
            if (d.getUserID().equals(id)) return d;
        }
        System.out.println("Doctor not found. Try again!");
        return selectDoctor();
    }

    private static Administrator selectAdministrator() {
        System.out.println("Select an Administrator by ID:");
        for (Administrator a : admins) {
            System.out.println(a.getUserID() + ": " + a.getName());
        }
        System.out.print("Enter Administrator ID: ");
        String id = scanner.nextLine();
        for (Administrator a : admins) {
            if (a.getUserID().equals(id)) return a;
        }
        System.out.println("Admin not found. Try again!");
        return selectAdministrator();
    }
}