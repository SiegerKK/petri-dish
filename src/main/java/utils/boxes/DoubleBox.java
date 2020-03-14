package utils.boxes;

public class DoubleBox implements IBoxValue<Double>{
    public double value;

    public DoubleBox(double value){
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }
    @Override
    public void setValue(Double newValue) {
        value = newValue;
    }
    @Override
    public void addValue(Double addValue) {
        value += addValue;
    }
    @Override
    public void multiplyValue(Double multiplyValue) {
        value *= multiplyValue;
    }
    @Override
    public void divideValue(Double divideValue) {
        value /= divideValue;
    }
}
