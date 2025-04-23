package ChatVideoConsultation;
import java.util.List;

/**
 * Represents a chat client that can send and receive messages through the ChatServer.
 */
public class ChatClient implements Runnable {
    private String userId; // ID of the user using this chat client
    private ChatService service; // Reference to the central chat server
    private boolean active; // Controls the background thread
    private Thread messageListener; // Background thread for listening to messages

    public ChatClient(String userId, ChatService service) {
        this.userId = userId;
        this.service = service;
        this.active = true;
        this.messageListener = new Thread(this); // Start listening on instantiation
        this.messageListener.start();
    }

    /**
     * Sends a message to another user via the server.
     */
    public void sendMessage(String receiverId, String message) {
        ChatMessage msg = new ChatMessage(userId, receiverId, message);
        service.sendMessage(msg);
    }

    /**
     * Retrieves unread messages for this user from the server.
     */
    public List<ChatMessage> getUnreadMessages() {
        return service.getUnreadMessages(userId);
    }

    /**
     * Displays chat history between this user and another user.
     */
    public void viewChatHistory(String otherUserId) {
        List<ChatMessage> history = service.getMessagesBetween(userId, otherUserId);
        if (history.isEmpty()) {
            System.out.println("No messages found.");
            return;
        }
        System.out.println("\nCHAT HISTORY WITH " + otherUserId + ":");
        for (ChatMessage msg : history) {
            String prefix = msg.getSenderId().equals(userId) ? "You: " : "Them: ";
            System.out.println(prefix + msg.getContent() + " (" + msg.getTimestamp() + ")");
        }
    }

    /**
     * Stops the background message listener.
     */
    public void stop() {
        this.active = false;
    }

    /**
     * Background task for periodically checking new messages.
     */
    @Override
    public void run() {
        while (active) {
            try {
                List<ChatMessage> newMessages = getUnreadMessages();
                if (!newMessages.isEmpty()) {
                    System.out.println("\n=== NEW MESSAGES ===");
                    for (ChatMessage msg : newMessages) {
                        System.out.println("From " + msg.getSenderId() + ": " +
                                          msg.getContent() + " (" + msg.getTimestamp() + ")");
                    }
                    System.out.println("===================");
                }
                Thread.sleep(3000); // Check every 3 seconds
            } catch (InterruptedException e) {
                System.out.println("Message listener interrupted");
            }
        }
    }
}
