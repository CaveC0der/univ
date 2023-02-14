package message;

public class CalculationResult extends Message {
    public CalculationResult(String ID, double data) {
        super(MessageType.CALCULATION_RESULT, ID, data);
    }

    public Double data() {
        return (double) super.data();
    }
}
