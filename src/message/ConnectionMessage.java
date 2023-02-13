package message;

public class ConnectionMessage extends Message {
    public ConnectionMessage() {
        super(MessageType.CONNECTION, null);
    }

    public String toString() {
        return "CONNECTION";
    }
}
