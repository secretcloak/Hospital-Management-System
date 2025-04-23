# Remote Patient Monitoring System (RPMS)

## Overview

The **Remote Patient Monitoring System (RPMS)** is a Java-based application designed to facilitate healthcare delivery through remote monitoring, communication, and emergency response. It supports administrators, doctors, and patients, enabling vital sign monitoring, appointment scheduling, chat and video consultations, emergency alerts, and email notifications. The system integrates various modules to ensure seamless interaction between users and efficient management of health data.

## Features

- **User Management**: Supports roles for Administrators, Doctors, and Patients with distinct functionalities.
- **Vital Sign Monitoring**: Patients can upload vital signs (heart rate, blood pressure, temperature, oxygen level), which are checked against safe thresholds to trigger alerts if abnormal.
- **Emergency Alerts**: Includes a `PanicButton` for immediate alerts and automated `VitalAlert` for abnormal vitals, notifying doctors and administrators via email or SMS.
- **Chat and Video Consultations**: Real-time chat between doctors and patients, with video call support using unique meeting links.
- **Notifications and Reminders**: Sends email-based appointment and medication reminders, with configurable SMTP settings.
- **Appointment Scheduling**: Allows doctors and patients to schedule and manage appointments.
- **Medical History and Prescriptions**: Doctors can provide feedback and issue prescriptions, stored in the patient’s medical history.

## System Architecture

The system is organized into several packages, each handling specific functionality:

- **EmergencyAlertSystem**: Manages emergency alerts, including `PanicButton` and `VitalAlert` for immediate and vital-based notifications.
- **ChatVideoConsultation**: Handles chat (`ChatServer`, `ChatClient`) and video call (`VideoCall`) functionalities.
- **NotificationsandReminders**: Implements email (`EmailNotification`) and SMS (`SMSNotification`) notifications, plus reminder services for appointments and medications.
- **Main**: The entry point (`Main.java`) with a console-based interface for user interaction.

### Key Classes and Interfaces

- **Alertable (Interface)**: Defines the contract for triggering alerts (`triggerAlert`).
- **AlertService**: Sends notifications to multiple recipients using a `Notifiable` mechanism (e.g., email or SMS).
- **EmergencyAlert (Abstract)**: Base class for alerts, with `VitalAlert` checking vital signs against thresholds.
- **PanicButton**: Triggers immediate alerts for emergencies.
- **ChatClient**: Manages sending/receiving messages with a background thread for real-time updates.
- **ChatServer**: Stores and routes chat messages, supporting conversation history and unread message retrieval.
- **VideoCall**: Generates meeting links and initiates video calls.
- **EmailNotification**: Sends emails using JavaMail API with Gmail SMTP.
- **ReminderService**: Manages appointment and medication reminders.
- **Main**: Console-based UI for role selection (Administrator, Doctor, Patient) and menu-driven operations.

## Prerequisites

- **Java 8 or higher**: Required to compile and run the application.
- **JavaMail API**: Used for email notifications (included in dependencies).
- **Gmail SMTP Credentials**:
  - Set environment variables `SMTP_USERNAME` and `SMTP_PASSWORD` for default email notifications, or provide credentials manually.
  - Use a Gmail App Password for authentication (not the regular Gmail password).
- **Dependencies**:
  - `javax.mail` for email functionality.

## Setup Instructions

1. **Clone the Repository**:

   ```bash
   git clone <repository-url>
   cd rpms
   ```

2. **Set Environment Variables** (for email notifications):

   ```bash
   export SMTP_USERNAME="your-gmail-address@gmail.com"
   export SMTP_PASSWORD="your-gmail-app-password"
   ```

3. **Compile the Code**:

   ```bash
   javac -cp .:lib/* Main/*.java
   ```

4. **Run the Application**:

   ```bash
   java -cp .:lib/* Main.Main
   ```

5. **Interact with the Console**:

   - Select a role (Administrator, Doctor, Patient) or exit.
   - Follow the menu prompts to perform actions like adding users, uploading vitals, scheduling appointments, or sending messages.

## Usage

### Administrator

- Add doctors and patients.
- View system logs.
- Send appointment and medication reminders.

### Doctor

- View patient vitals.
- Schedule appointments.
- Provide feedback and prescriptions.
- Check messages and start video calls.
- Send emails (with optional vital sign data).

### Patient

- Upload vital signs (triggers alerts if abnormal).
- Schedule appointments.
- View medical history.
- Send messages to doctors.
- Start video calls.
- Press the panic button for emergencies.
- Send emails (with optional vital sign data).

## Example Workflow

1. **Administrator**:

   - Creates a doctor (`ID: D001, Name: Dr. Smith`) and a patient (`ID: P001, Name: John Doe`).
   - Sends reminders for upcoming appointments.

2. **Patient**:

   - Uploads vitals (e.g., Heart Rate: 120, Blood Pressure: 150, Temperature: 37.5, Oxygen: 92).
   - System detects abnormal vitals and sends an email alert to the doctor.
   - Presses the panic button, notifying the doctor and administrator.

3. **Doctor**:

   - Views patient vitals and medical history.
   - Schedules an appointment with the patient.
   - Starts a video call using the generated meeting link.
   - Sends a prescription via email.

4. **Chat**:

   - Patient sends a message to the doctor: “Feeling dizzy.”
   - Doctor responds: “Please rest and take your medication.”

## Limitations

- **Console-Based UI**: No graphical interface; interaction is via command-line prompts.
- **Mock SMS**: `SMSNotification` only simulates sending SMS (prints to console).
- **Video Calls**: Generates mock meeting links; actual video call implementation requires integration with a third-party service.
- **Environment Variables**: Email notifications fail if `SMTP_USERNAME` or `SMTP_PASSWORD` are not set, unless credentials are provided manually.
- **Thread Safety**: `ChatServer` uses synchronized methods, but heavy concurrent usage may require further optimization.

## Future Improvements

- Add a graphical user interface (e.g., JavaFX or web-based).
- Integrate a real SMS gateway (e.g., Twilio) for `SMSNotification`.
- Implement a third-party video call service (e.g., Zoom, WebRTC).
- Enhance security for credentials (e.g., encrypted storage instead of environment variables).
- Add database persistence for users, vitals, and messages (currently in-memory).

## Contributing

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/your-feature`).
3. Commit changes (`git commit -m "Add your feature"`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.
