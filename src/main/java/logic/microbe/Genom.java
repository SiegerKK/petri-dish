package logic.microbe;

import utils.ConsoleManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

import static logic.microbe.Gene.*;

public class Genom {
    LinkedList<Gene> genes;

    public Genom(LinkedList<Gene> genes){
        this.genes = new LinkedList<>();
        this.genes.addAll(genes);
    }
    public Genom(Genom genom){
        genes = new LinkedList<>();
        genes.addAll(genom.genes);
    }

    public static ArrayList<Gene> allGenes;
    public static void init(){
        allGenes = new ArrayList<>();
        Gene gene;
        ArrayList<String> perks = new ArrayList<>();

        //---------A1---------//
        perks.clear();
        perks.add(ENERGY_FOOD_1);
        gene = new Gene("A1", perks);
        allGenes.add(gene);

        //---------A11---------//
        perks.clear();
        perks.add(ENERGY_FOOD_1);
        perks.add(ENERGY_MAX_1);
        gene = new Gene("A11", perks);
        allGenes.add(gene);

        //---------A2---------//
        perks.clear();
        perks.add(ENERGY_FOOD_2);
        gene = new Gene("A2", perks);
        allGenes.add(gene);

        //---------A3---------//
        perks.clear();
        perks.add(ENERGY_FOOD_3);
        gene = new Gene("A3", perks);
        allGenes.add(gene);

        //---------D1---------//
        perks.clear();
        perks.add(DUPLICATE_1);
        gene = new Gene("D1", perks);
        allGenes.add(gene);

        allGenes.trimToSize();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Gene gene : genes)
            str.append(gene.indeks);
        return str.toString();
    }
}
