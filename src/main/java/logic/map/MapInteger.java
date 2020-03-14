package logic.map;

import utils.ConsoleManager;
import utils.boxes.IntegerBox;

import java.util.ArrayList;
import java.util.Random;

public abstract class MapInteger implements IMap<Integer> {
    protected ArrayList<IntegerBox> map;
    protected int sizeX, sizeY;
    protected int maxLimit, minLimit;
    public String name;

    public MapInteger(int x, int y, int initValue, String name, int minLimit, int maxLimit){
        init(x, y, initValue, name, minLimit, maxLimit);
    }
    public void init(int x, int y, int value, String name, int minLimit, int maxLimit){
        map = new ArrayList<>();
        this.name = name;
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
        sizeX = x;
        sizeY = y;
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                map.add(new IntegerBox(value));
            }
        }
    }

    public int getValue(int x, int y){
        return getValue(y * sizeX + x);
    }
    @Override
    public Integer getValue(int id) {
        return map.get(id).getValue();
    }

    public void setValue(int x, int y, int value){
        setValue(y * sizeX + x, value);
    }
    @Override
    public void setValue(int id, Integer value) {
        map.get(id).setValue(value);
    }

    public double increaseValue(int x, int y, int value){
        return increaseValue(y * sizeX + x, value);
    }
    @Override
    public Integer increaseValue(int id, Integer value){
        if(map.get(id).getValue() + value > maxLimit) value = maxLimit - map.get(id).getValue();
        if(map.get(id).getValue() + value < minLimit) value = minLimit - map.get(id).getValue();

        map.get(id).addValue(value);

        return value;
    }
    public int getSizeX(){
        return sizeX;
    }
    public int getSizeY() {
        return sizeY;
    }
    @Override
    public int getMapSize(){
        return map.size();
    }
    public double getMaxLimit() {
        return maxLimit;
    }
    public double getMinLimit() {
        return minLimit;
    }

    /**
     *
     * @param noiseCoefficient must be in range 0.0 - 1.0
     */
    public void addNoise(double noiseCoefficient){
        Random random = new Random();
        for (IntegerBox integerBox : map)
            integerBox.multiplyValue(1 + (int) ((noiseCoefficient * (random.nextDouble() - 0.5) * 2) + 0.5));
    }

    @Override
    public void printConsole(int x, int y){
        if(x > sizeX)
            x = sizeX;
        if(y > sizeY)
            y = sizeY;

        int[][] table = new int[y][x];

        //Calculate statistic
        double total = 0.0;
        for (int i = 0; i < getMapSize(); i++) {
            total += map.get(i).getValue();
        }


        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                double avarageValue = 0.0;
                int counter = 0;

                for (int locali = sizeY / y * i; locali < sizeY / y * (i + 1); locali++) {
                    for (int localj = sizeX / x * j; localj < sizeX / x * (j + 1); localj++) {
                        avarageValue += getValue(localj, locali);
                        counter++;
                    }
                }
                table[i][j] = (int)(((avarageValue / counter) - minLimit) / (maxLimit - minLimit) * 10);
            }
        }

        //Printing
        ConsoleManager.writeln(name + " | " + total);
        ConsoleManager.writeln();
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if(table[i][j] > 9)
                    ConsoleManager.write("*", ConsoleManager.COLOR_RED);
                else if(table[i][j] >= 8)
                    ConsoleManager.write(table[i][j], ConsoleManager.COLOR_RED);
                else if(table[i][j] >= 5)
                    ConsoleManager.write(table[i][j], ConsoleManager.COLOR_YELLOW);
                else if(table[i][j] >= 3)
                    ConsoleManager.write(table[i][j], ConsoleManager.COLOR_GREEN);
                else if(table[i][j] >= 1)
                    ConsoleManager.write(table[i][j], ConsoleManager.COLOR_GREEN);
                else if(table[i][j] == 0)
                    ConsoleManager.write(table[i][j], ConsoleManager.COLOR_WHITE);
                else if(table[i][j] < 0)
                    ConsoleManager.write("*", ConsoleManager.COLOR_DEFAULT);
            }
            ConsoleManager.writeln();
        }
    }

    @Override
    public void reset(Integer value){
        for (IntegerBox integerBox : map) {
            integerBox.setValue(value);
        }
    }
    public void reset(){
        reset(0);
    }
}
