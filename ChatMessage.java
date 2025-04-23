package ChatVideoConsultation;
import java.time.LocalDateTime;

/**
 * Represents a chat message exchanged between two users.
 */
public class ChatMessage {
    private String senderId;
    private String receiverId;
    private String content;
    private LocalDateTime timestamp;
    private boolean read;

    public ChatMessage(String senderId, String receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.read = false;
    }

    public String getSenderId() { return senderId; }

    public String getReceiverId() { return receiverId; }

    public String getContent() { return content; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public boolean isRead() { return read; }

    public void markAsRead() { this.read = true; }
    
    public String toString(){
        return String.format("%nSender ID: %s%nReceiver ID: %s%nContent: %s%n", senderId, receiverId, content);
    }
}
