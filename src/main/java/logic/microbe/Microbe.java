package logic.microbe;

import logic.map.MapFood;
import logic.map.MapGas;
import logic.map.MapLight;
import logic.map.MapMicrobe;
import utils.coord.CoordInt;

import java.util.ArrayList;

public class Microbe {
    public static final double ENERGY_MAX = 100.0;
    public static final double O2_MAX = 10.0;
    public static final double CO2_MAX = 10.0;

    public Genome genome;

    public CoordInt coord;

    public double energyMax = 100;
    public double o2Max = 10;
    public double co2Max = 10;

    public double energy = energyMax / 2;
    public double o2 = 0.0;
    public double co2 = 0.0;

    public Microbe(Genome genome, int x, int y){
        this.genome = new Genome(genome);
        coord = new CoordInt(x, y);
    }
    public ArrayList<Microbe> step(ArrayList<MapGas> mapGases, MapLight mapLight, MapFood mapFood){
        ArrayList<Microbe> newMicrobes = new ArrayList<>();
        for (Gene gene : genome.genes) {
            for (String label : gene.perks) {
                Object result = Gene.getUsability(label, this, mapGases, mapLight, mapFood);
                if (result != null) {
                    newMicrobes.add((Microbe) result);
                }
            }
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
        str.append("\n" + genome + " (" + coord.getX() + ", " + coord.getY() + ")");
        str.append("\n\tEnergy: " + energy + "/" + energyMax);
        str.append("\n\tO2:     " + o2 + "/" + o2Max);
        str.append("\n\tCO2:    " + co2 + "/" + co2Max);
        return str.toString();
    }
}