package message;

import java.io.Serializable;

public class Message implements Serializable {
    public final MessageType type;
    public final String ID;
    protected final Object data;

    public Message(String ID, Object data) {
        this.type = MessageType.MESSAGE;
        this.ID = ID;
        this.data = data;
    }

    public Message(MessageType type, String ID, Object data) {
        this.type = type;
        this.ID = ID;
        this.data = data;
    }

    public String toString() {
        return "type: " + type + " ID: " + ID + " data: " + data.toString();
    }

    public Object data() {
        return data;
    }
}
