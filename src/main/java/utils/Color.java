package utils;

public class Color {
    static public final int MAX_VALUE = 5;

    static public final String COLOR_DEFAULT = "\033[39m";

    static public final String COLOR_BLACK = "\033[30m";
    static public final String COLOR_RED = "\033[31m";
    static public final String COLOR_GREEN = "\033[32m";
    static public final String COLOR_YELLOW = "\033[33m";
    static public final String COLOR_BLUE = "\033[34m";
    static public final String COLOR_MAGENTA = "\033[35m";
    static public final String COLOR_CYAN = "\033[36m";
    static public final String COLOR_WHITE = "\033[37m";
    static public final String COLOR_BLACK_BRIGHT = "\033[30;1m";
    static public final String COLOR_RED_BRIGHT = "\033[31;1m";
    static public final String COLOR_GREEN_BRIGHT = "\033[32;1m";
    static public final String COLOR_YELLOW_BRIGHT = "\033[33;1m";
    static public final String COLOR_BLUE_BRIGHT = "\033[34;1m";
    static public final String COLOR_MAGENTA_BRIGHT = "\033[35;1m";
    static public final String COLOR_CYAN_BRIGHT = "\033[36;1m";
    static public final String COLOR_WHITE_BRIGHT = "\033[37;1m";

    private double r, g, b;

    public Color(){
        this(0.0, 0.0, 0.0);
    }
    public Color(Color color){
        this(color.r, color.g, color.b);
    }
    public Color(double r, double g, double b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void add(Color color){
        add(color.r, color.g, color.b);
    }
    public void add(double r2, double g2, double b2){
        r += r2;
        g += g2;
        b += b2;
    }
    public void divide(double divider){
        r /= divider;
        g /= divider;
        b /= divider;
    }

    public double getR() {
        return r;
    }
    public double getG() {
        return g;
    }
    public double getB() {
        return b;
    }
    public void setR(double r) {
        this.r = r;
    }
    public void setG(double g) {
        this.g = g;
    }
    public void setB(double b) {
        this.b = b;
    }

    public String getColor(){
        int code = ((int)(r * MAX_VALUE + 0.5) * 36 + (int)(g * MAX_VALUE + 0.5) * 6 + (int)(b * MAX_VALUE + 0.5) + 16);
        return "\u001b[38;5;" + code + "m";
    }

    //---------------------Static methods-------------------//

    public static String getColor(double r, double g, double b){
        int code = ((int)(r * MAX_VALUE + 0.5) * 36 + (int)(g * MAX_VALUE + 0.5) * 6 + (int)(b * MAX_VALUE + 0.5) + 16);
        return "\u001b[38;5;" + code + "m";
    }
}
