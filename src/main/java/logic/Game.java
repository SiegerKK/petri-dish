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
    public boolean DEBUG_MODE = false;
    public final int SIZE = 40;

    MapGas mapO2;
    MapGas mapCO2;
    MapLight mapLight;
    MapFood mapFood;
    ArrayList<MapGas> gasMaps;
    ArrayList<MapDouble> allMaps;

    ArrayList<Microbe> microbes;

    public void init(){
        mapO2 = new MapGas(SIZE, SIZE, 500.0, "O2", 0.0, 1000.0, 1.0);
        mapCO2 = new MapGas(SIZE, SIZE, 0.0, "CO2", 0.0, 1000.0, 5.0);
        //------Randomizer--------//
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            mapCO2.setValue(Math.abs(random.nextInt(SIZE)), Math.abs(random.nextInt(SIZE)), 5000);
        }
        //------------------------//

        mapLight = new MapLight(SIZE, SIZE, "Light");
        mapLight.setTemplate(MapLight.TEMPLATE_50_CENTER_GRADIENT);

        mapFood = new MapFood(SIZE, SIZE, 100, 3.0, "Food", 0.0, 1000.0);
        mapFood.setMapLight(mapLight, 2.0);

        //Setup lists
        gasMaps = new ArrayList<>();
        gasMaps.add(mapO2);
        gasMaps.add(mapCO2);

        allMaps = new ArrayList<>();
        allMaps.add(mapO2);
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
        for (int step = 0; step < steps; step++)
            gameLogicStep(true, 100);

        printMaps();
        printStatistic();
        ConsoleManager.writeln();
    }

    public void gameLogicStep(boolean printUI, int timePause){
        long stepTime = System.currentTimeMillis();

        //Physic update
        //TODO: update gases, update food
        GasPhysic.gasesLogicStep(gasMaps);

        //Microbes logic step
        //TODO: code

/*
        //update gasMaps
        GasPhysic.difusionStep(gasMaps);
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
*/

        //Print UI
        if(printUI) printGameUI(stepTime);

        //Pause before next step
        if(timePause > 0) try { Thread.sleep(timePause); } catch (InterruptedException ex){ex.printStackTrace();}
    }

    private void printGameUI(long stepTimeDebug){
        int heightUI = printMaps();

        TreeMap<Gene, Integer> genesCounter = new TreeMap<>();
        TreeMap<String, Integer> genomeCounter = new TreeMap<>();
        for (Gene gene : Genome.allGenes)
            genesCounter.put(gene, 0);

        for (Microbe microbe : microbes)
            genomeCounter.put(microbe.genome.toString(), genomeCounter.getOrDefault(microbe.genome.toString(), 0) + 1);

        for (String genomeIndex : new TreeSet<>(genomeCounter.keySet())) {
            if(genomeCounter.get(genomeIndex) > microbes.size() / 100.0 * 0.5) {
                ConsoleManager.writeln(genomeIndex + " -> " + genomeCounter.get(genomeIndex));
                heightUI++;
            }
        }

        //Print debug info
        if(DEBUG_MODE) {
            ConsoleManager.writeln("Step time: " + ((System.currentTimeMillis() - stepTimeDebug) / 1000.0));
            heightUI++;
        }

        ConsoleManager.moveCursorUp(heightUI);
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

    /**
     *
     * @return height of printed space
     */
    private int printMaps(){
        ArrayList<MapDouble> maps = new ArrayList<>();
        maps.add(mapO2);
        maps.add(mapCO2);
        MapDouble.printMaps(maps, SIZE, SIZE);
        return SIZE + 2;
    }

}
