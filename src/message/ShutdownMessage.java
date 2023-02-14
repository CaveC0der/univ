package message;

public class ShutdownMessage extends Message {
    public ShutdownMessage(String ID) {
        super(MessageType.SHUTDOWN, ID, null);
    }

    public String toString() {
        return "SHUTDOWN";
    }
}
