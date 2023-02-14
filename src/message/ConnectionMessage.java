package message;

public class ConnectionMessage extends Message {
    public ConnectionMessage(String ID) {
        super(MessageType.CONNECTION, ID, null);
    }

    public String toString() {
        return "CONNECTION";
    }
}
