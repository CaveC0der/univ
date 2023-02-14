package message;

public class CalculationResult extends Message {
    public CalculationResult(double data) {
        super(MessageType.CALCULATION_RESULT, data);
    }

    public Double data() {
        return (double) super.data();
    }
}
