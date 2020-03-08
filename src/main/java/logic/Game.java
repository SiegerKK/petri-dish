package logic;

import logic.map.MapGas;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    MapGas mapOxygen;
    MapGas mapCO2;
    ArrayList<MapGas> gasMaps;

    public final int SIZE = 40;

    private void init(){
        mapOxygen = new MapGas(SIZE, SIZE, 50.0, "Oxygen", 0.0, 100.0, 1.0);
        mapCO2 = new MapGas(SIZE, SIZE, 0.0, "CO2", 0.0, 100.0, 1.0);

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            mapCO2.setValue(Math.abs(random.nextInt()%SIZE), Math.abs(random.nextInt()%SIZE), random.nextDouble()*1500+500);
        }

        gasMaps = new ArrayList<>();
        gasMaps.add(mapOxygen);
        gasMaps.add(mapCO2);
    }


    public void start(int steps){
        init();
        mapOxygen.printConsole(SIZE, SIZE);
        mapCO2.printConsole(SIZE, SIZE);

        long time, totalTime = System.currentTimeMillis();
        for (int step = 0; step < steps; step++) {
            time = System.currentTimeMillis();

            //update gasMaps
            GasPhisyc.difusionStep(gasMaps);
            mapOxygen.printConsole(SIZE, SIZE);
            mapCO2.printConsole(SIZE, SIZE);
            System.out.println("Step time: " + ((System.currentTimeMillis() - time) / 1000.0));
            try { Thread.sleep(100); } catch (InterruptedException ex){ex.printStackTrace();}
        }
        mapOxygen.printConsole(SIZE, SIZE);
        mapCO2.printConsole(SIZE, SIZE);
        System.out.println("Totatl time: " + ((System.currentTimeMillis() - totalTime) / 1000.0));


        System.out.println(mapOxygen.getValue(SIZE/2, SIZE/2));
        System.out.println(mapCO2.getValue(SIZE/2, SIZE/2));
    }

}
