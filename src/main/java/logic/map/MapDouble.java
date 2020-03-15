package logic.map;

import utils.Color;
import utils.ConsoleManager;
import utils.boxes.DoubleBox;

import java.util.ArrayList;
import java.util.Random;

public abstract class MapDouble implements IMap<Double> {
    protected ArrayList<DoubleBox> map;
    protected int sizeX, sizeY;
    protected double maxLimit, minLimit;
    public String name;

    public MapDouble(int x, int y, double initValue, String name, double minLimit, double maxLimit){
        init(x, y, initValue, name, minLimit, maxLimit);
    }
    public void init(int x, int y, double value, String name, double minLimit, double maxLimit){
        map = new ArrayList<>();
        this.name = name;
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
        sizeX = x;
        sizeY = y;
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                map.add(new DoubleBox(value));
            }
        }
    }

    @Override
    public Double getValue(int x, int y){
        return getValue(y * sizeX + x);
    }
    @Override
    public Double getValue(int id){
        return map.get(id).value;
    }
    @Override
    public void setValue(int x, int y, Double value){
        setValue(y * sizeX + x, value);
    }
    @Override
    public void setValue(int id, Double value){
//        if(value > maxLimit) value = maxLimit;
//        if(value < minLimit) value = minLimit;
        map.get(id).value = value;
    }
    @Override
    public Double increaseValue(int x, int y, Double value){
        return increaseValue(y * sizeX + x, value);
    }
    @Override
    public Double increaseValue(int id, Double value){
        if(map.get(id).value + value > maxLimit) value = maxLimit - map.get(id).value;
        if(map.get(id).value + value < minLimit) value = minLimit - map.get(id).value;

        map.get(id).value += value;

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
        /*if(noiseCoefficient > 1.0 || noiseCoefficient < 0.0) {
            System.out.println("ERROR: noiseCoefficient not in range " + noiseCoefficient);
            return;
        }*/

        Random random = new Random();
        for (DoubleBox doubleBox : map)
            doubleBox.value *= (1.0 + (noiseCoefficient * (random.nextDouble() - 0.5) * 2));
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
                    ConsoleManager.write("*", Color.COLOR_RED);
                else if(table[i][j] >= 8)
                    ConsoleManager.write(table[i][j], Color.COLOR_RED);
                else if(table[i][j] >= 5)
                    ConsoleManager.write(table[i][j], Color.COLOR_YELLOW);
                else if(table[i][j] >= 3)
                    ConsoleManager.write(table[i][j], Color.COLOR_GREEN);
                else if(table[i][j] >= 1)
                    ConsoleManager.write(table[i][j], Color.COLOR_GREEN);
                else if(table[i][j] == 0)
                    ConsoleManager.write(table[i][j], Color.COLOR_WHITE);
                else if(table[i][j] < 0)
                    ConsoleManager.write("*", Color.COLOR_DEFAULT);
            }
            ConsoleManager.writeln();
        }
    }

    @Override
    public void reset(Double value){
        for (DoubleBox doubleBox : map) {
            doubleBox.value = value;
        }
    }
    public void reset(){
        reset(0.0);
    }
}
