package message;

import java.util.Arrays;

public class CalculationRequest extends Message {
    public CalculationRequest(double[] data) {
        super(MessageType.CALCULATION_REQUEST, data);
    }

    public String toString() {
        return "type: " + type + " data: " + Arrays.toString((double[]) data);
    }

    public double[] data() {
        return (double[]) super.data();
    }
}
