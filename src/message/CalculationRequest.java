package message;

import java.util.Arrays;

public class CalculationRequest extends Message {
    public CalculationRequest(String ID, double[] data) {
        super(MessageType.CALCULATION_REQUEST, ID, data);
    }

    public String toString() {
        return "type: " + type + " ID: " + ID + " data: " + Arrays.toString((double[]) data);
    }

    public double[] data() {
        return (double[]) super.data();
    }
}
