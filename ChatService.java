package ChatVideoConsultation;

import java.util.List;

public interface ChatService {
    void sendMessage(ChatMessage message);
    List<ChatMessage> getMessagesBetween(String user1, String user2);
    List<ChatMessage> getUnreadMessages(String userId);
}