package logic;

import logic.map.MapDouble;
import logic.map.MapFood;
import logic.map.MapGas;
import logic.map.MapLight;
import logic.microbe.Gene;
import logic.microbe.Genome;
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
        mapOxygen = new MapGas(SIZE, SIZE, 600.0, "O2", 0.0, 1000.0, 1.0);
        mapCO2 = new MapGas(SIZE, SIZE, 600.0, "CO2", 0.0, 1000.0, 1.0);
        //------Randomizer--------//
//        Random random = new Random();
//        for (int i = 0; i < 10; i++) {
//            mapCO2.setValue(Math.abs(random.nextInt()%SIZE), Math.abs(random.nextInt()%SIZE), random.nextDouble()*50 + 50);
//        }
        //------------------------//

        mapLight = new MapLight(SIZE, SIZE, "Light");
        mapLight.setTemplate(MapLight.TEMPLATE_50_CENTER_GRADIENT);

        mapFood = new MapFood(SIZE, SIZE, 100, 3.0, "Food", 0.0, 1000.0);
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

        for (int i = 0; i < 2; i++) {
            genes.clear();
            genes.add(Genome.allGenes.get(0));
            genes.add(Genome.allGenes.get(Genome.allGenes.size() - 3));
            microbes.add(new Microbe(new Genome(genes), SIZE / 2, SIZE / 2));
        }
    }

    public void start(int steps){
        long time;
        for (int step = 0; step < steps; step++) {
            time = System.currentTimeMillis();

            //update gasMaps
            GasPhisyc.difusionStep(gasMaps);
            mapFood.grow();
            //Microbes
            ArrayList<Microbe> deleteMicrobes = new ArrayList<>();
            ArrayList<Microbe> newMicrobes = new ArrayList<>();
            for (int i = 0; i < microbes.size(); i++) {
                Microbe microbe = microbes.get(i);
                newMicrobes.addAll(microbe.step(gasMaps, mapLight, mapFood));
                if(microbe.isDead())
                    deleteMicrobes.add(microbe);
            }
            microbes.removeAll(deleteMicrobes);
            microbes.addAll(newMicrobes);

            printGameUI();
            ConsoleManager.writeln("Step time: " + ((System.currentTimeMillis() - time) / 1000.0));
            try { Thread.sleep(100); } catch (InterruptedException ex){ex.printStackTrace();}
        }

        printMaps();
        printStatistic();
        ConsoleManager.writeln();
    }

    private void printStatistic(){
        int microbesCounter = 0;   //Why?
        TreeMap<Gene, Integer> genesCounter = new TreeMap<>();
        TreeMap<String, Integer> genomeCounter = new TreeMap<>();
        for (Gene gene : Genome.allGenes)
            genesCounter.put(gene, 0);

        for (Microbe microbe : microbes) {
            genomeCounter.put(microbe.genome.toString(), genomeCounter.getOrDefault(microbe.genome.toString(), 0) + 1);

            for (Gene gene : microbe.genome.genes)
                genesCounter.put(gene, genesCounter.get(gene) + 1);
        }


        ConsoleManager.writeln();
        for (String genomeIndex : new TreeSet<>(genomeCounter.keySet())) {
            if(genomeCounter.get(genomeIndex) > microbes.size() / 100.0 * 0.1) {
                ConsoleManager.writeln(genomeIndex + " -> " + genomeCounter.get(genomeIndex));
            }
        }

        ConsoleManager.writeln();
        for (Gene gene : new TreeSet<>(genesCounter.keySet())) {
            ConsoleManager.writeln(gene.toString());
            ConsoleManager.writeln("Count: " + genesCounter.get(gene));
        }
    }
    private void printMaps(){
        MapDouble.printMaps(allMaps, SIZE, SIZE);
    }
    private void printGameUI(){
        printMaps();
        TreeMap<Gene, Integer> genesCounter = new TreeMap<>();
        TreeMap<String, Integer> genomeCounter = new TreeMap<>();
        for (Gene gene : Genome.allGenes)
            genesCounter.put(gene, 0);

        for (Microbe microbe : microbes)
            genomeCounter.put(microbe.genome.toString(), genomeCounter.getOrDefault(microbe.genome.toString(), 0) + 1);

        ConsoleManager.writeln();
        for (String genomeIndex : new TreeSet<>(genomeCounter.keySet())) {
            if(genomeCounter.get(genomeIndex) > microbes.size() / 100.0 * 0.5) {
                ConsoleManager.writeln(genomeIndex + " -> " + genomeCounter.get(genomeIndex));
            }
        }

    }

}
