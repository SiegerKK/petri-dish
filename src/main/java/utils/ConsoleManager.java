package utils;

import java.util.*;

import static utils.Color.*;

public class ConsoleManager {
    static public final String MOVE_UP = "\u001b[1A";
    static public final String MOVE_DOWN = "\u001b[1B";
    static public final String MOVE_RIGHT = "\u001b[1C";
    static public final String MOVE_LEFT = "\u001b[1D";
    static public final String MOVE_BEGIN = "\u001b[1000D";

    static public int shift = 0;

    /**
     * Write str to terminal
     * @param str
     */
    static public synchronized void write(Object str){
        System.out.print(str);
    }
    /**
     * Write colored str to terminal
     * @param str
     * @param color
     */
    static public synchronized void write(Object str, String color){
        System.out.print(color + str + COLOR_DEFAULT);
    }
    /**
     * Write new line
     */
    static public synchronized void writeln(){
        System.out.print("\n");
        if(shift > 0)
            moveCursorRight(shift);
    }
    /**
     * Write str to terminal
     * @param str
     */
    static public synchronized void writeln(Object str){
        System.out.println();
        if(shift > 0)
            moveCursorRight(shift);
        System.out.print(str);
    }
    /**
     * Write colored str to terminal
     * @param str
     * @param color
     */
    static public synchronized void writeln(Object str, String color){
        System.out.println();
        if(shift > 0)
            moveCursorRight(shift);
        System.out.print(color + str + COLOR_DEFAULT);
    }
    /**
     * Write str to terminal from begin of line
     * @param str
     */
    static public synchronized void writeFromBegin(Object str){
        write(MOVE_BEGIN);
        write(str);
    }
    /**
     * Write colored str to terminal from begin of line
     * @param str
     * @param color
     */
    static public synchronized void writeFromBegin(Object str, String color){
        write(MOVE_BEGIN);
        write(str, color);
    }

    /**
     * Move cursor in terminal up(only terminal)
     * @param n
     */
    static public synchronized void moveCursorUp(int n){
        write("\u001b[" + n + "A");
    }
    /**
     * Move cursor in terminal down(only terminal)
     * @param n
     */
    static public synchronized void moveCursorDown(int n){
        write("\u001b[" + n + "B");
    }
    /**
     * Move cursor in terminal right(only terminal)
     * @param n
     */
    static public synchronized void moveCursorRight(int n){
        write("\u001b[" + n + "C");
    }
    /**
     * Move cursor in terminal left(only terminal)
     * @param n
     */
    static public synchronized void moveCursorLeft(int n){
        write("\u001b[" + n + "D");
    }

    @Deprecated
    static public void printHistogram(HashMap<Long, Double> histogram, int height, int length){
        ArrayList<Long> sortedKeys = new ArrayList<Long>(histogram.keySet());
        Collections.sort(sortedKeys);

        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for (Long key : sortedKeys) {
            if(max < histogram.get(key))
                max = histogram.get(key);
            if(min > histogram.get(key))
                min = histogram.get(key);
        }
        double totalHeight = max - min;

        ConsoleManager.writeln("Max: " + max + " | Min: " + min);

        for (int page = 0; page < (histogram.size() / length) + 1; page++) {
            for (int y = height - 1; y > -1; y--) {
                for (int x = page*length; x < page*length + length && x < histogram.size(); x++){
                    Long key = sortedKeys.get(x);

                    if ((histogram.get(key) > 0) && (totalHeight * y/(double)height + min > 0))
                        if(histogram.get(key) > (totalHeight * (y/(double)height) + min))
                            ConsoleManager.write("+", COLOR_GREEN);
                        else
                            ConsoleManager.write(" ");
                    else if ((histogram.get(key) < 0) && (totalHeight * y/(double)height + min < 0))
                        if(histogram.get(key) < (totalHeight * (y/(double)height) + min))
                            ConsoleManager.write("-", COLOR_RED);
                        else
                            ConsoleManager.write(" ");
                    else
                        ConsoleManager.write(" ");

                }
                ConsoleManager.writeln("");
            }
            for (int i = 0; i < length; i++)
                ConsoleManager.write("-");
            ConsoleManager.writeln("");
        }


        ConsoleManager.writeln(new Date(sortedKeys.get(0)) + " - " + new Date(sortedKeys.get(histogram.size()-1)));
    }
}
