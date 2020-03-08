package logic.microbe;

import logic.map.MapFood;
import logic.map.MapGas;
import logic.map.MapLight;
import utils.ConsoleManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Gene {
    public static final String ENERGY_FOOD_1 = "energy_food_1";
    public static final String ENERGY_FOOD_2 = "energy_food_2";
    public static final String ENERGY_FOOD_3 = "energy_food_3";
    public static final String ENERGY_MAX_1 = "energy_max_1";
    public static final String DUPLICATE_1 = "duplicate_1";

    public String indeks;
    public ArrayList<String> perks;

    public Gene(String indeks, ArrayList<String> perks){
        this.indeks = indeks;
        this.perks = new ArrayList<>(perks);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\n" + indeks + "\n");
        for (String perk : perks)
            str.append("\t" + perk);
        return str.toString();
    }

    //-----------Static methods-------------//
    public static Object getUsability(String label, Microbe microbe, ArrayList<MapGas> mapGases, MapLight mapLight, MapFood mapFood){
        switch(label){
            case ENERGY_FOOD_1:
                if(mapFood.getValue(microbe.x, microbe.y) > 0.5 && microbe.energy + 1 < microbe.energyMax) {
                    microbe.energy += 1;
                    mapFood.increaseValue(microbe.x, microbe.y, -0.5);
                } else {
                    microbe.energy -= 0.5;
                }
                break;
            case ENERGY_FOOD_2:
                if(mapFood.getValue(microbe.x, microbe.y) > 1.5  && microbe.energy + 2 < microbe.energyMax) {
                    microbe.energy += 2;
                    mapFood.increaseValue(microbe.x, microbe.y, -1.5);
                } else {
                    microbe.energy -= 1.0;
                }
                break;
            case ENERGY_FOOD_3:
                if(mapFood.getValue(microbe.x, microbe.y) > 4 && microbe.energy + 4 < microbe.energyMax) {
                    microbe.energy += 4;
                    mapFood.increaseValue(microbe.x, microbe.y, -4);
                } else {
                    microbe.energy -= 2.0;
                }
                break;
            case ENERGY_MAX_1:
                microbe.energyMax = Microbe.ENERGY_MAX + 20;
                if(mapFood.getValue(microbe.x, microbe.y) > 1) {
                    mapFood.increaseValue(microbe.x, microbe.y, -1);
                } else {
                    microbe.energy -= 1;
                }
                break;
            case DUPLICATE_1:
                if(microbe.energy > microbe.energyMax * 0.8) {
                    Random random = new Random();
                    if(random.nextDouble() < 0.1){
                        int x = microbe.x;
                        int y = microbe.y;
                        if(random.nextDouble() < 0.2){
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
                        return microbe.duplicate(x, y, random.nextDouble() < 0.1, random.nextDouble() < 0.05);
                    }

                }
                break;
        }

        return null;
    }
}
