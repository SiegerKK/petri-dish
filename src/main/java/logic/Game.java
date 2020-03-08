package logic;

import logic.map.MapDouble;
import logic.map.MapFood;
import logic.map.MapGas;
import logic.map.MapLight;
import logic.microbe.Gene;
import logic.microbe.Genom;
import logic.microbe.Microbe;
import utils.ConsoleManager;

import java.util.*;

public class Game {
    MapGas mapOxygen;
    MapGas mapCO2;
    MapLight mapLight;
    MapFood mapFood;
    ArrayList<MapGas> gasMaps;
    ArrayList<MapDouble> allMaps;

    ArrayList<Microbe> microbes;

    public final int SIZE = 40;

    public void init(){
        mapOxygen = new MapGas(SIZE, SIZE, 50.0, "O2", 0.0, 100.0, 1.0);
        mapCO2 = new MapGas(SIZE, SIZE, 0.0, "CO2", 0.0, 100.0, 1.0);
        //------Randomizer--------//
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            mapCO2.setValue(Math.abs(random.nextInt()%SIZE), Math.abs(random.nextInt()%SIZE), random.nextDouble()*1500+500);
        }
        //------------------------//

        mapLight = new MapLight(SIZE, SIZE, "Light");
        mapLight.setTemplate(MapLight.TEMPLATE_50_CENTER);

        mapFood = new MapFood(SIZE, SIZE, 500, 0.0, "Food", 0.0, 1000.0);
        mapFood.setMapLight(mapLight, 2.0);

        //Setup lists
        gasMaps = new ArrayList<>();
        gasMaps.add(mapOxygen);
        gasMaps.add(mapCO2);

        allMaps = new ArrayList<>();
        allMaps.add(mapOxygen);
        allMaps.add(mapCO2);
        allMaps.add(mapLight);
        allMaps.add(mapFood);

        //Setup microbes
        microbes = new ArrayList<>();
        LinkedList<Gene> genes = new LinkedList<>();
        genes.add(Genom.allGenes.get(0));
        genes.add(Genom.allGenes.get(4));
        microbes.add(new Microbe(new Genom(genes), 25, 25));
        genes.clear();
        genes.add(Genom.allGenes.get(2));
        genes.add(Genom.allGenes.get(4));
        microbes.add(new Microbe(new Genom(genes), 15, 15));
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
            //Microbes
            Iterator<Microbe> iterator = microbes.listIterator();
            while(iterator.hasNext()){
                Microbe microbe = iterator.next();
                for(Microbe microbe1 : microbe.step(gasMaps, mapLight, mapFood)){
                    ((ListIterator<Microbe>) iterator).add(microbe1);
                }
                if(microbe.isDead())
                    iterator.remove();
            }

            MapDouble.printMaps(allMaps, SIZE, SIZE);
            for (Microbe microbe : microbes) {
                ConsoleManager.writeln(microbe);
            }
            System.out.println("Step time: " + ((System.currentTimeMillis() - time) / 1000.0));
            try { Thread.sleep(100); } catch (InterruptedException ex){ex.printStackTrace();}
        }
        System.out.println("Totatl time: " + ((System.currentTimeMillis() - totalTime) / 1000.0));


        System.out.println(mapOxygen.getValue(SIZE/2, SIZE/2));
        System.out.println(mapCO2.getValue(SIZE/2, SIZE/2));
    }

}
