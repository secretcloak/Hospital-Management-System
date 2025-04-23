package ChatVideoConsultation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Central server that handles storing and routing chat messages between clients.
 */
public class ChatServer implements ChatService {
    private Map<String, List<ChatMessage>> messageHistory; // Stores messages using a conversation key

    public ChatServer() {
        this.messageHistory = new HashMap<>();
    }

    /**
     * Stores a message in the history based on a conversation key.
     */
    public synchronized void sendMessage(ChatMessage message) {
        String key = getConversationKey(message.getSenderId(), message.getReceiverId());
        messageHistory.computeIfAbsent(key, k -> new ArrayList<>()).add(message);
    }

    /**
     * Retrieves full conversation history between two users.
     */
    public synchronized List<ChatMessage> getMessagesBetween(String user1, String user2) {
        String key = getConversationKey(user1, user2);
        return messageHistory.getOrDefault(key, new ArrayList<>());
    }

    /**
     * Returns unread messages for a user and marks them as read.
     */
    public synchronized List<ChatMessage> getUnreadMessages(String userId) {
        List<ChatMessage> unread = new ArrayList<>();
        for (List<ChatMessage> conversation : messageHistory.values()) {
            for (ChatMessage msg : conversation) {
                if (msg.getReceiverId().equals(userId) && !msg.isRead()) {
                    unread.add(msg);
                    msg.markAsRead(); // Mark as read once retrieved
                }
            }
        }
        return unread;
    }

    /**
     * Generates a consistent key for a conversation between two users.
     */
    private String getConversationKey(String user1, String user2) {
        return user1.compareTo(user2) < 0 ? user1 + "-" + user2 : user2 + "-" + user1;
    }
}
