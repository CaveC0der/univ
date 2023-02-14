package message;

public class IDMessage extends Message {
    public IDMessage(String ID) {
        super(MessageType.ID, ID, ID);
    }

    public String toString() {
        return (String) data;
    }

    public String data() {
        return (String) data;
    }
}
