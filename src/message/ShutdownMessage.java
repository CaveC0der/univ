package message;

public class ShutdownMessage extends Message {
    public ShutdownMessage() {
        super(MessageType.SHUTDOWN, null);
    }

    public String toString() {
        return "SHUTDOWN";
    }
}
