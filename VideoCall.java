package ChatVideoConsultation;
import UserManagement.Doctor;
import UserManagement.Patient;
import java.util.UUID;

/**
 * Represents a video call session between a doctor and a patient.
 */
public class VideoCall implements VideoCallService {
    private String meetingLink; // URL/link for the video call
    private Doctor doctor;
    private Patient patient;

    public VideoCall(Doctor doctor, Patient patient) {
        this.doctor = doctor;
        this.patient = patient;
        this.meetingLink = "https://hms-video.example.com/" + UUID.randomUUID().toString();

    }
    
    /**
     * Generates meeting link for a video call
     */
    public String generateMeetingLink(){
        return this.meetingLink;
    }

    /**
     * Starts the video call and returns a confirmation message.
     */
    public String startCall() throws Exception {
        if (doctor == null || patient == null) {
            throw new Exception("Invalid doctor or patient for video call.");
        }
        return "Video call started: " + meetingLink;
    }
}
