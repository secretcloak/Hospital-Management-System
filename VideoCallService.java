package ChatVideoConsultation;

public interface VideoCallService {
    String generateMeetingLink();
    String startCall() throws Exception;
}