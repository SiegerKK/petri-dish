package logic.microbe;

import logic.map.MapFood;
import logic.map.MapGas;
import logic.map.MapLight;
import utils.ConsoleManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Microbe {
    public static final double ENERGY_MAX = 100.0;
    public static final double O2_MAX = 100.0;
    public static final double CO2_MAX = 100.0;

    public Genom genom;

    public int x, y;

    public double energyMax = 100;
    public double o2Max = 100;
    public double co2Max = 100;

    public double energy = energyMax / 2;
    public double o2 = 0.0;
    public double co2 = 0.0;

    public Microbe(Genom genom, int x, int y){
        this.genom = genom;
        this.x = x;
        this.y = y;
    }
    public Microbe duplicate(int x, int y, boolean mutate, boolean grow){
        Microbe microbe = new Microbe(new Genom(genom), x, y);
        energy /= 2;
        microbe.energy /= 2;

        Random random = new Random();
        if(mutate){
            int oldGeneId = random.nextInt(genom.genes.size());
            microbe.genom.genes.remove(oldGeneId);
            Gene newGene = Genom.allGenes.get(random.nextInt(Genom.allGenes.size()));
            if(!microbe.genom.genes.contains(newGene))
                microbe.genom.genes.add(newGene);
        }
        if(grow){
            Gene newGene = Genom.allGenes.get(random.nextInt(Genom.allGenes.size()));
            if(!microbe.genom.genes.contains(newGene))
                microbe.genom.genes.add(newGene);
        }

        return microbe;
    }
    public ArrayList<Microbe> step(ArrayList<MapGas> mapGases, MapLight mapLight, MapFood mapFood){
        ArrayList<Microbe> newMicrobes = new ArrayList<>();
        for (Gene gene : genom.genes)
            for(String label : gene.perks)
                if(label.equals(Gene.DUPLICATE_1)) {
                    Object result = Gene.getUsability(label, this, mapGases, mapLight, mapFood);
                    if(result != null)
                        newMicrobes.add((Microbe) result);
                } else {
                    Gene.getUsability(label, this, mapGases, mapLight, mapFood);
                }
        return newMicrobes;
    }
    public boolean isDead(){
        if(energy < 0)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\n" + genom + " (" + x + ", " + y + ")");
        str.append("\n\tEnergy: " + energy + "/" + energyMax);
        str.append("\n\tO2:     " + o2 + "/" + o2Max);
        str.append("\n\tCO2:    " + co2 + "/" + co2Max);
        return str.toString();
    }
}