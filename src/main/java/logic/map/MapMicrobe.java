package logic.map;

import logic.microbe.Genome;
import logic.microbe.Microbe;
import utils.Color;
import utils.ConsoleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MapMicrobe implements IMap<HashSet<Microbe>>{
    private ArrayList<HashSet<Microbe>> map;
    private List<Microbe> microbes;

    private String name;
    private int maxLimit, sizeX, sizeY;
    private final int minLimit = 0;


    public MapMicrobe(int sizeX, int sizeY, String name, int maxLimit, List<Microbe> microbes) {
        this.name = name;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.maxLimit = maxLimit;

        this.microbes = microbes;

        map = new ArrayList<>();
        for (int i = 0; i < sizeY; i++)
            for (int j = 0; j < sizeX; j++)
                map.add(new HashSet<Microbe>());

    }

    @Override
    public HashSet<Microbe> getValue(int x, int y) {
        return getValue(y * sizeX + x);
    }
    @Override
    public HashSet<Microbe> getValue(int id) {
        return map.get(id);
    }
    @Override
    public void setValue(int x, int y, HashSet<Microbe> value) {
        setValue(y * sizeX + x, value);
    }
    @Override
    public void setValue(int id, HashSet<Microbe> value) {
        this.map.get(id).clear();
        this.map.get(id).addAll(value);
    }
    @Override
    public HashSet<Microbe> increaseValue(int x, int y, HashSet<Microbe> value) {
        return increaseValue(y * sizeX + x, value);
    }
    @Override
    public HashSet<Microbe> increaseValue(int id, HashSet<Microbe> value) {
        map.get(id).addAll(value);
        return map.get(id);
    }
    public HashSet<Microbe> increaseValue(int x, int y, Microbe value) {
        return increaseValue(y * sizeX + x, value);
    }
    public HashSet<Microbe> increaseValue(int id, Microbe value) {
        map.get(id).add(value);
        return map.get(id);
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

    @Override
    public void printConsole(int x, int y) {
        if(x > sizeX)
            x = sizeX;
        if(y > sizeY)
            y = sizeY;

        String[][] tableColor = new String[y][x];

        reset(null);
        for (Microbe microbe : microbes){
            increaseValue(microbe.coord.getX(), microbe.coord.getY(), microbe);
        }

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                HashSet<Microbe> microbes = new HashSet<>();
                HashMap<Genome, Integer> genomes = new HashMap<>();

                //Getting all microbes in region
                for (int locali = sizeY / y * i; locali < sizeY / y * (i + 1); locali++) {
                    for (int localj = sizeX / x * j; localj < sizeX / x * (j + 1); localj++) {
                        microbes.addAll(getValue(localj, locali));
                    }
                }

                if(microbes.size() == 0) {
                    tableColor[i][j] = Color.COLOR_BLACK;
                    continue;
                }

                //Calculating count of microbes by genome
                for (Microbe microbe : microbes)
                    genomes.put(microbe.genome, genomes.getOrDefault(microbe.genome, 0) + 1);

                //Finding max counter
                int max = 0;
                Genome maxGenome = null;
                for (Genome genome : genomes.keySet()) {
                    if (max < genomes.get(genome)) {
                        max = genomes.get(genome);
                        maxGenome = genome;
                    }
                }

                //Set color of region
                tableColor[i][j] = maxGenome.getColor();
            }
        }

        //Printing
        ConsoleManager.writeln(name);
        ConsoleManager.writeln();
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                ConsoleManager.write("X", tableColor[i][j]);
            }
            ConsoleManager.writeln();
        }
    }

    /**
     *
     * @param value not used
     */
    @Override
    public void reset(HashSet<Microbe> value) {
        for (HashSet<Microbe> set : map)
            set.clear();
    }
}
