package logic.microbe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static logic.microbe.Gene.*;

public class Genome {
    static Random random = new Random();

    public LinkedList<Gene> genes;

    public Genome(LinkedList<Gene> genes){
        this.genes = new LinkedList<>();
        this.genes.addAll(genes);
        this.genes.sort(((o1, o2) -> {return o1.index.compareTo(o2.index);}));
    }
    public Genome(Genome genome){
        genes = new LinkedList<>();
        genes.addAll(genome.genes);
        genes.sort(((o1, o2) -> {return o1.index.compareTo(o2.index);}));
    }

    public Gene mutate(){
        int oldGeneId = random.nextInt(genes.size());
        Gene oldGene = genes.get(oldGeneId);

        Gene newGene = null;
        for (int i = 0; i < 100; i++) {
            newGene = Genome.allGenes.get(random.nextInt(Genome.allGenes.size()));
            if(newGene.isSameSubtype(oldGene) && !newGene.equals(oldGene))
                break;
            newGene = null;
        }

        if(newGene != null) {
            genes.remove(oldGeneId);
            genes.add(newGene);

            genes.sort(((o1, o2) -> {return o1.index.compareTo(o2.index);}));

            return newGene;
        } else {
            return null;
        }
    }
    public Gene grow(){
        Gene newGene = null;
        boolean end = false;

        for (int i = 0; i < 100; i++) {
            newGene = Genome.allGenes.get(random.nextInt(Genome.allGenes.size()));

            end = true;
            for (Gene gene : genes)
                if(gene.isSameSubtype(newGene))
                    end = false;

            if(end)
                break;
        }

        if(end) {
            genes.add(newGene);

            genes.sort(((o1, o2) -> {return o1.index.compareTo(o2.index);}));

            return newGene;
        } else {
            return null;
        }
    }
    public void degrade(){
        int oldGeneId = random.nextInt(genes.size());
        genes.remove(oldGeneId);

        genes.sort(((o1, o2) -> {return o1.index.compareTo(o2.index);}));

    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Genome))
            return false;

        Genome genome = (Genome)obj;

        boolean result = false;
        for (Gene gene : genes){
            result = false;
            for (Gene geneAnother : genome.genes){
                if(gene.equals(geneAnother)) {
                    result = true;
                    break;
                }
            }

            if(!result)
                return false;
        }

        return true;
    }
    public boolean containsGene(Gene gene){
        for (Gene geneTemp : genes)
            if(gene.equals(geneTemp))
                return true;
        return false;
    }

    //-----------------Static methods--------------------//

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

        //--------A1m1--------//
        perks.clear();
        perks.add(ENERGY_FOOD_1);
        perks.add(ENERGY_MAX_1);
        gene = new Gene("A1m1", perks);
        allGenes.add(gene);

        //--------A1m2--------//
        perks.clear();
        perks.add(ENERGY_FOOD_1);
        perks.add(ENERGY_MAX_2);
        gene = new Gene("A1m2", perks);
        allGenes.add(gene);

        //---------A2---------//
        perks.clear();
        perks.add(ENERGY_FOOD_2);
        gene = new Gene("A2", perks);
        allGenes.add(gene);

        //--------A2m1--------//
        perks.clear();
        perks.add(ENERGY_FOOD_2);
        perks.add(ENERGY_MAX_1);
        gene = new Gene("A2m1", perks);
        allGenes.add(gene);

        //--------A2m2--------//
        perks.clear();
        perks.add(ENERGY_FOOD_2);
        perks.add(ENERGY_MAX_2);
        gene = new Gene("A2m2", perks);
        allGenes.add(gene);

        //---------A3---------//
        perks.clear();
        perks.add(ENERGY_FOOD_3);
        gene = new Gene("A3", perks);
        allGenes.add(gene);

        //--------A3m1-------//
        perks.clear();
        perks.add(ENERGY_FOOD_3);
        perks.add(ENERGY_MAX_1);
        gene = new Gene("A3m1", perks);
        allGenes.add(gene);

        //--------A3m2-------//
        perks.clear();
        perks.add(ENERGY_FOOD_3);
        perks.add(ENERGY_MAX_2);
        gene = new Gene("A3m2", perks);
        allGenes.add(gene);

        //---------B1---------//
        perks.clear();
        perks.add(ENERGY_O2_FOOD_1);
        gene = new Gene("B1", perks);
        allGenes.add(gene);

        //---------B2---------//
        perks.clear();
        perks.add(ENERGY_O2_FOOD_2);
        gene = new Gene("B2", perks);
        allGenes.add(gene);

        //---------C1---------//
        perks.clear();
        perks.add(ENERGY_CO2_LIGHT_1);
        gene = new Gene("C1", perks);
        allGenes.add(gene);

        //--------C1m1--------//
        perks.clear();
        perks.add(ENERGY_CO2_LIGHT_1);
        perks.add(ENERGY_MAX_1);
        gene = new Gene("C1m1", perks);
        allGenes.add(gene);

        //--------C1m2--------//
        perks.clear();
        perks.add(ENERGY_CO2_LIGHT_1);
        perks.add(ENERGY_MAX_2);
        gene = new Gene("C1m2", perks);
        allGenes.add(gene);

        //---------C2---------//
        perks.clear();
        perks.add(ENERGY_CO2_LIGHT_2);
        gene = new Gene("C2", perks);
        allGenes.add(gene);

        //--------C2m1--------//
        perks.clear();
        perks.add(ENERGY_CO2_LIGHT_2);
        perks.add(ENERGY_MAX_1);
        gene = new Gene("C2m1", perks);
        allGenes.add(gene);

        //---------G----------//
        perks.clear();
        perks.add(ENERGY_FOOD_1);
        perks.add(ENERGY_CO2_LIGHT_1);
        perks.add(ENERGY_O2_FOOD_1);
        perks.add(ENERGY_MAX_1);
        gene = new Gene("G", perks);
        allGenes.add(gene);

        //---------D1---------//
        perks.clear();
        perks.add(DUPLICATE_1);
        gene = new Gene("D1", perks);
        allGenes.add(gene);

        //---------D2---------//
        perks.clear();
        perks.add(DUPLICATE_2);
        gene = new Gene("D2", perks);
        allGenes.add(gene);

        //---------D3---------//
        perks.clear();
        perks.add(DUPLICATE_3);
        gene = new Gene("D3", perks);
        allGenes.add(gene);

        allGenes.trimToSize();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Gene gene : genes)
            str.append(gene.index);
        return str.toString();
    }
}
