package utils.boxes;

public interface IBoxValue<T> {
    public T getValue();
    public void setValue(T newValue);
    public void addValue(T addValue);
    public void multiplyValue(T multiplyValue);
    public void divideValue(T divideValue);
}
