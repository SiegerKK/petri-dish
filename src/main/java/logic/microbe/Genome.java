package logic.microbe;

import utils.Color;
import utils.ConsoleManager;

import java.util.*;

import static logic.microbe.Gene.*;

public class Genome {
    public static final String SYMBOL = "X";
    public static ArrayList<Gene> allGenes;
    public static ArrayList<Gene> allGenesReverse;

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
    public Genome(String genomeIndex) throws Exception{
        StringBuilder str = new StringBuilder(genomeIndex);
        genes = new LinkedList<>();
        while(str.length() > 0){
            boolean isBad = true;

            for (Gene gene : allGenesReverse){
                if(str.toString().startsWith(gene.index)){
                    genes.add(gene);
                    str = str.replace(0, gene.index.length(), "");
                    isBad = false;
                    break;
                }
            }

            if(isBad) throw new Exception("Wrong genome format");
        }
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

    public String getColor(){
        Color color = new Color();
        int divider = 0;
        for (Gene gene : genes){
            if(gene.color != null) {
                color.add(gene.getColor());
                divider++;
            }
        }
        color.divide(divider);

        return color.getColor();
    }

    //-----------------Static methods--------------------//

    public static void init(){
        allGenes = new ArrayList<>();
        Gene gene;
        ArrayList<String> perks = new ArrayList<>();

        //---------A1---------//
        perks.clear();
        perks.add(ENERGY_FOOD_1);
        gene = new Gene("A1", perks, new Color(0.2, 0.0, 0.0));
        allGenes.add(gene);

        //--------A1m1--------//
        perks.clear();
        perks.add(ENERGY_FOOD_1);
        perks.add(ENERGY_MAX_1);
        gene = new Gene("A1m1", perks, new Color(0.3, 0.0, 0.0));
        allGenes.add(gene);

        //--------A1m2--------//
        perks.clear();
        perks.add(ENERGY_FOOD_1);
        perks.add(ENERGY_MAX_2);
        gene = new Gene("A1m2", perks, new Color(0.4, 0.0, 0.0));
        allGenes.add(gene);

        //---------A2---------//
        perks.clear();
        perks.add(ENERGY_FOOD_2);
        gene = new Gene("A2", perks, new Color(0.5, 0.0, 0.0));
        allGenes.add(gene);

        //--------A2m1--------//
        perks.clear();
        perks.add(ENERGY_FOOD_2);
        perks.add(ENERGY_MAX_1);
        gene = new Gene("A2m1", perks, new Color(0.6, 0.0, 0.0));
        allGenes.add(gene);

        //--------A2m2--------//
        perks.clear();
        perks.add(ENERGY_FOOD_2);
        perks.add(ENERGY_MAX_2);
        gene = new Gene("A2m2", perks, new Color(0.7, 0.0, 0.0));
        allGenes.add(gene);

        //---------A3---------//
        perks.clear();
        perks.add(ENERGY_FOOD_3);
        gene = new Gene("A3", perks, new Color(0.8, 0.0, 0.0));
        allGenes.add(gene);

        //--------A3m1-------//
        perks.clear();
        perks.add(ENERGY_FOOD_3);
        perks.add(ENERGY_MAX_1);
        gene = new Gene("A3m1", perks, new Color(0.9, 0.0, 0.0));
        allGenes.add(gene);

        //--------A3m2-------//
        perks.clear();
        perks.add(ENERGY_FOOD_3);
        perks.add(ENERGY_MAX_2);
        gene = new Gene("A3m2", perks, new Color(1.0, 0.0, 0.0));
        allGenes.add(gene);

        //---------B1---------//
        perks.clear();
        perks.add(ENERGY_O2_FOOD_1);
        gene = new Gene("B1", perks, new Color(0.0, 0.0, 0.5));
        allGenes.add(gene);

        //---------B2---------//
        perks.clear();
        perks.add(ENERGY_O2_FOOD_2);
        gene = new Gene("B2", perks, new Color(0.0, 0.0, 1.0));
        allGenes.add(gene);

        //---------C1---------//
        perks.clear();
        perks.add(ENERGY_CO2_LIGHT_1);
        gene = new Gene("C1", perks, new Color(0.0, 0.2, 0.0));
        allGenes.add(gene);

        //--------C1m1--------//
        perks.clear();
        perks.add(ENERGY_CO2_LIGHT_1);
        perks.add(ENERGY_MAX_1);
        gene = new Gene("C1m1", perks, new Color(0.0, 0.4, 0.0));
        allGenes.add(gene);

        //--------C1m2--------//
        perks.clear();
        perks.add(ENERGY_CO2_LIGHT_1);
        perks.add(ENERGY_MAX_2);
        gene = new Gene("C1m2", perks, new Color(0.0, 0.6, 0.0));
        allGenes.add(gene);

        //---------C2---------//
        perks.clear();
        perks.add(ENERGY_CO2_LIGHT_2);
        gene = new Gene("C2", perks, new Color(0.0, 0.8, 0.0));
        allGenes.add(gene);

        //--------C2m1--------//
        perks.clear();
        perks.add(ENERGY_CO2_LIGHT_2);
        perks.add(ENERGY_MAX_1);
        gene = new Gene("C2m1", perks, new Color(0.0, 1.0, 0.0));
        allGenes.add(gene);

        //---------G----------//
        perks.clear();
        perks.add(ENERGY_FOOD_1);
        perks.add(ENERGY_CO2_LIGHT_1);
        perks.add(ENERGY_O2_FOOD_1);
        perks.add(ENERGY_MAX_1);
        gene = new Gene("G", perks, new Color(1.0, 1.0, 1.0));
        allGenes.add(gene);

        //---------D1---------//
        perks.clear();
        perks.add(DUPLICATE_1);
        gene = new Gene("D1", perks, null);
        allGenes.add(gene);

        //---------D2---------//
        perks.clear();
        perks.add(DUPLICATE_2);
        gene = new Gene("D2", perks, null);
        allGenes.add(gene);

        //---------D3---------//
        perks.clear();
        perks.add(DUPLICATE_3);
        gene = new Gene("D3", perks, null);
        allGenes.add(gene);

        allGenes.trimToSize();

        allGenesReverse = (ArrayList<Gene>) (allGenes.clone());
        allGenesReverse.sort(((o1, o2) -> {return -o1.index.compareTo(o2.index);}));
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Gene gene : genes)
            str.append(gene.index);
        return str.toString();
    }

    public static String getSymbol(String index){
        try {
            Genome genome = new Genome(index);
            return genome.getColor() + SYMBOL + Color.COLOR_DEFAULT;
        } catch (Exception ex){
            return index;
        }
    }
}
