package logic.microbe;

import logic.map.MapFood;
import logic.map.MapGas;
import logic.map.MapLight;
import logic.map.MapMicrobe;
import utils.Color;

import java.util.ArrayList;
import java.util.Random;

public class Gene implements Comparable{
    public static final double DUPLICATE_COST_BY_GENE = 30;

    public static final String ENERGY_CO2_LIGHT_1 = "energy_co2_light_1";
    public static final String ENERGY_CO2_LIGHT_2 = "energy_co2_light_2";
    public static final String ENERGY_O2_FOOD_1 = "energy_o2_food_1";
    public static final String ENERGY_O2_FOOD_2 = "energy_o2_food_2";
    public static final String ENERGY_FOOD_1 = "energy_food_1";
    public static final String ENERGY_FOOD_2 = "energy_food_2";
    public static final String ENERGY_FOOD_3 = "energy_food_3";
    public static final String ENERGY_MAX_1 = "energy_max_1";
    public static final String ENERGY_MAX_2 = "energy_max_2";
    public static final String DUPLICATE_1 = "duplicate_1";
    public static final String DUPLICATE_2 = "duplicate_2";
    public static final String DUPLICATE_3 = "duplicate_3";

    String index;
    ArrayList<String> perks;
    Color color;

    public Gene(String index, ArrayList<String> perks, Color color){
        this.index = index;
        this.perks = new ArrayList<>(perks);
        this.color = color;
    }

    public String getIndex() {
        return index;
    }
    public ArrayList<String> getPerks() {
        return perks;
    }
    public Color getColor() {
        return color;
    }

    public void setIndex(String index) {
        this.index = index;
    }
    public void setPerks(ArrayList<String> perks) {
        this.perks = perks;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public String getSubtype(){
        return index.substring(0, 1);
    }
    public boolean isSameSubtype(Gene gene){
        return gene.getSubtype().equals(getSubtype());
    }
    public boolean isSameSubtype(String subtype){
        return subtype.equals(getSubtype());
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\n" + index + "\n Usability: ");
        for (String perk : perks)
            str.append(" " + perk);
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Gene))
            return false;

        Gene anotherGene = (Gene)obj;
        if(!index.equals(anotherGene.index))
            return false;

        /*boolean isSamePerk = false;
        for (String perkMain : perks){
            isSamePerk = false;
            for (String perkAnother : anotherGene.perks){
                if(perkMain.equals(perkAnother)){
                    isSamePerk = true;
                    break;
                }
            }
        }

        return isSamePerk;*/
        return true;
    }

    //-----------Static methods-------------//
    public static Object getUsability(String label, Microbe microbe, ArrayList<MapGas> mapGases, MapLight mapLight, MapFood mapFood){
        Microbe resultMicrobe = null;
        switch(label){
            case ENERGY_CO2_LIGHT_1:
                energyCO2Light(microbe, mapGases, mapLight, 0.3, 0.5, 0.2, -0.2, -0.5);
                break;
            case ENERGY_CO2_LIGHT_2:
                energyCO2Light(microbe, mapGases, mapLight, 0.7, 1.5, 0.5, -0.5, -1.0);
                break;
            case ENERGY_O2_FOOD_1:
                energyO2Food(microbe, mapGases, mapFood, 2.0, -0.5, -0.5, -1.0);
                break;
            case ENERGY_O2_FOOD_2:
                energyO2Food(microbe, mapGases, mapFood, 3.0, -0.5, -1.0, -2.0);
                break;
            case ENERGY_FOOD_1:
                energyFood(microbe, mapFood, 1.0, -0.5, -0.2);
                break;
            case ENERGY_FOOD_2:
                energyFood(microbe, mapFood, 2.0, -1.5, -0.5);
                break;
            case ENERGY_FOOD_3:
                energyFood(microbe, mapFood, 4.0, -4.0, -2.0);
                break;
            case ENERGY_MAX_1:
                energyMax(microbe, mapFood, mapGases, 50, -0.3, -0.0, -0.5);
                break;
            case ENERGY_MAX_2:
                energyMax(microbe, mapFood, mapGases, 100, -0.5, -0.5, -1.0);
                break;
            case DUPLICATE_1:
                resultMicrobe = duplicate(microbe, mapFood,
                        0.8, -0.3,
                        0.1, 0.2,
                        0.01, 0.001, 0.001);
                break;
            case DUPLICATE_2:
                resultMicrobe = duplicate(microbe, mapFood,
                        0.8, -0.5,
                        0.1, 0.5,
                        0.01, 0.001, 0.001);
                break;
            case DUPLICATE_3:
                resultMicrobe = duplicate(microbe, mapFood,
                        0.8, -1.0,
                        0.1, 0.2,
                        0.02, 0.003, 0.001);
                break;
        }

        return resultMicrobe;
    }

    private static void energyMax(Microbe microbe, MapFood mapFood, ArrayList<MapGas> mapGases,
                                  double energyMaxSummand, double foodIncomeSuccess, double o2IncomeSuccess, double energyIncomeFail){
        MapGas mapO2 = null;
        for (MapGas mapGas : mapGases)
            if(mapGas.name.equals("O2"))
                mapO2 = mapGas;

        microbe.energyMax = Microbe.ENERGY_MAX + energyMaxSummand;
        if(mapFood.getValue(microbe.coord.getX(), microbe.coord.getY()) > -foodIncomeSuccess
                && mapO2.getValue(microbe.coord.getX(), microbe.coord.getY()) > -o2IncomeSuccess) {
            mapFood.increaseValue(microbe.coord.getX(), microbe.coord.getY(), foodIncomeSuccess);
            mapO2.increaseValue(microbe.coord.getX(), microbe.coord.getY(), o2IncomeSuccess);
        } else {
            microbe.energy += energyIncomeFail;
        }
    }
    private static void energyFood(Microbe microbe, MapFood mapFood,
                                   double energyIncomeSuccess, double foodIncomeSuccess, double energyIncomeFail){
        if(mapFood.getValue(microbe.coord.getX(), microbe.coord.getY()) > -foodIncomeSuccess
                && microbe.energy + energyIncomeSuccess < microbe.energyMax) {
            microbe.energy += energyIncomeSuccess;
            mapFood.increaseValue(microbe.coord.getX(), microbe.coord.getY(), foodIncomeSuccess);
        } else {
            microbe.energy += energyIncomeFail;
        }
    }
    private static void energyO2Food(Microbe microbe, ArrayList<MapGas> mapGases, MapFood mapFood,
                                     double energyIncomeSuccess, double foodIncomeSuccess, double o2IncomeSuccess,
                                     double energyIncomeFail){
        MapGas mapO2 = null;
        for (MapGas mapGas : mapGases)
            if(mapGas.name.equals("O2"))
                mapO2 = mapGas;

        if(mapFood.getValue(microbe.coord.getX(), microbe.coord.getY()) > foodIncomeSuccess
                && microbe.energy + energyIncomeSuccess < microbe.energyMax
                && mapO2.getValue(microbe.coord.getX(), microbe.coord.getY()) > o2IncomeSuccess) {
            microbe.energy += energyIncomeSuccess;
            mapFood.increaseValue(microbe.coord.getX(), microbe.coord.getY(), foodIncomeSuccess);
            mapO2.increaseValue(microbe.coord.getX(), microbe.coord.getY(), o2IncomeSuccess);
        } else {
            microbe.energy += energyIncomeFail;
        }

    }
    private static void energyCO2Light(Microbe microbe, ArrayList<MapGas> mapGases, MapLight mapLight,
                                     double lightRequired, double energyIncomeSuccess, double o2OutcomeSuccess, double co2IncomeSuccess,
                                     double energyIncomeFail){
        MapGas mapCO2 = null;
        MapGas mapO2 = null;
        for (MapGas mapGas : mapGases) {
            if (mapGas.name.equals("CO2"))
                mapCO2 = mapGas;
            else if(mapGas.name.equals("O2"))
                mapO2 = mapGas;
        }

        if(mapLight.getIllumination(microbe.coord.getX(), microbe.coord.getY()) > lightRequired
                && microbe.energy + energyIncomeSuccess < microbe.energyMax
                && mapCO2.getValue(microbe.coord.getX(), microbe.coord.getY()) > -co2IncomeSuccess) {
            microbe.energy += energyIncomeSuccess *
                    ((mapLight.getIllumination(microbe.coord.getX(), microbe.coord.getY()) - lightRequired)
                    / (1.0 - lightRequired));
            mapCO2.increaseValue(microbe.coord.getX(), microbe.coord.getY(), co2IncomeSuccess);
            mapO2.increaseValue(microbe.coord.getX(), microbe.coord.getY(), o2OutcomeSuccess);
        } else {
            microbe.energy += energyIncomeFail;
        }

    }
    private static Microbe duplicate(Microbe microbe, MapFood mapFood,
                                     double energyStockPercentToAllowDuplicate, double energyRegularIncome,
                                     double chanceToDuplicate, double chaneToDuplicateAnotherPlace,
                                     double chanceToMutate, double chanceToGrow, double chanceToDegrade){
        Microbe resultMicrobe = null;

        microbe.energy += energyRegularIncome;
        if(microbe.energy > microbe.energyMax * energyStockPercentToAllowDuplicate
                && microbe.energy > microbe.genome.genes.size() * DUPLICATE_COST_BY_GENE) {
            Random random = new Random();
            if(random.nextDouble() < chanceToDuplicate){
                int x = microbe.coord.getX();
                int y = microbe.coord.getY();
                if(random.nextDouble() < chaneToDuplicateAnotherPlace){
                    switch (random.nextInt(4)){
                        case 0:
                            if(x + 1 < mapFood.getSizeX()) x++;
                            break;
                        case 1:
                            if(x - 1 > 0) x--;
                            break;
                        case 2:
                            if(y + 1 < mapFood.getSizeY()) y++;
                            break;
                        case 3:
                            if(y - 1 > 0) y--;
                            break;
                    }
                }

                resultMicrobe = new Microbe(microbe.genome, x, y);
                microbe.energy -= microbe.genome.genes.size() * DUPLICATE_COST_BY_GENE;
                microbe.energy /= 2;
                resultMicrobe.energy = microbe.energy;

                if(random.nextDouble() < chanceToMutate) {
                    resultMicrobe.genome.mutate();
                }
                if(random.nextDouble() < chanceToGrow) {
                    resultMicrobe.genome.grow();
                }
                if(random.nextDouble() < chanceToDegrade) {
                    resultMicrobe.genome.degrade();
                }
            }
        }

        return resultMicrobe;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Gene)
            return index.compareTo(((Gene)o).index);
        return -1;
    }
}
