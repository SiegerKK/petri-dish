package utils.coord;

public abstract class Coord<T extends Number> {
    private T x, y;

    /**
     *
     * @param x must be new value, not pointer
     * @param y must be new value, not pointer
     */
    public Coord(T x, T y){
        this.x = x;
        this.y = y;
    }

    public double range(T x2, T y2){
        return Math.sqrt((x.doubleValue() - x2.doubleValue()) * (x.doubleValue() - x2.doubleValue())
                + (y.doubleValue() - y2.doubleValue()) * (y.doubleValue() - y2.doubleValue()));
    }

    public T getX() {
        return x;
    }
    public T getY() {
        return y;
    }
    public void setX(T x) {
        this.x = x;
    }
    public void setY(T y) {
        this.y = y;
    }
}
