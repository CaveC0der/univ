package message;

import java.io.Serializable;

public class Message implements Serializable {
    public final MessageType type;
    protected Object data;

    public Message(Object data) {
        this.type = MessageType.MESSAGE;
        this.data = data;
    }

    public Message(MessageType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public String toString() {
        return "type: " + type + " data: " + data.toString();
    }

    public Object data() {
        return data;
    }
}
