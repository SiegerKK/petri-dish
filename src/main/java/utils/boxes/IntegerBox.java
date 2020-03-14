package utils.boxes;

public class IntegerBox implements IBoxValue<Integer> {
    private int value;

    public IntegerBox(int value){
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
    @Override
    public void setValue(Integer newValue) {
        value = newValue;
    }
    @Override
    public void addValue(Integer addValue) {
        value += addValue;
    }
    @Override
    public void multiplyValue(Integer multiplyValue) {
        value *= multiplyValue;
    }
    @Override
    public void divideValue(Integer divideValue) {
        value /= divideValue;
    }
}
