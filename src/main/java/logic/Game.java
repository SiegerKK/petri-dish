package logic;

import logic.map.MapDouble;
import logic.map.MapFood;
import logic.map.MapGas;
import logic.map.MapLight;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    MapGas mapOxygen;
    MapGas mapCO2;
    MapLight mapLight;
    MapFood mapFood;

    ArrayList<MapGas> gasMaps;
    ArrayList<MapDouble> allMaps;

    public final int SIZE = 40;

    public void init(){
        mapOxygen = new MapGas(SIZE, SIZE, 50.0, "Oxygen", 0.0, 100.0, 1.0);
        mapCO2 = new MapGas(SIZE, SIZE, 0.0, "CO2", 0.0, 100.0, 1.0);
        //------Randomizer--------//
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            mapCO2.setValue(Math.abs(random.nextInt()%SIZE), Math.abs(random.nextInt()%SIZE), random.nextDouble()*1500+500);
        }
        //------------------------//

        mapLight = new MapLight(SIZE, SIZE, "Light");
        mapLight.setTemplate(MapLight.TEMPLATE_50_CENTER);

        mapFood = new MapFood(SIZE, SIZE, 500, 1, "Food", 0.0, 1000.0);
        mapFood.setMapLight(mapLight, 2.0);



        gasMaps = new ArrayList<>();
        gasMaps.add(mapOxygen);
        gasMaps.add(mapCO2);

        allMaps = new ArrayList<>();
        allMaps.add(mapOxygen);
        allMaps.add(mapCO2);
        allMaps.add(mapLight);
        allMaps.add(mapFood);
    }

    public void start(int steps){
        mapOxygen.printConsole(SIZE, SIZE);
        mapCO2.printConsole(SIZE, SIZE);

        long time, totalTime = System.currentTimeMillis();
        for (int step = 0; step < steps; step++) {
            time = System.currentTimeMillis();

            //update gasMaps
            GasPhisyc.difusionStep(gasMaps);
            mapFood.grow();

            MapDouble.printMaps(allMaps, SIZE, SIZE);
//            mapOxygen.printConsole(SIZE, SIZE);
//            mapCO2.printConsole(SIZE, SIZE);
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
