package logic.map;

import utils.boxes.DoubleBox;

import java.util.ArrayList;

public class MapFood extends MapDouble {
    public ArrayList<DoubleBox> incomeMap;

    private boolean lightBoost = false;
    private MapLight mapLight;
    public double growCoefficient = 2.0;

    public MapFood(int x, int y, double initValue, double initIncomeValue, String name, double minLimit, double maxLimit) {
        super(x, y, initValue, name, minLimit, maxLimit);
        incomeMap = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            map.get(i).value = initValue;
            incomeMap.add(new DoubleBox(initIncomeValue));
        }
    }

    public void setMapLight(MapLight mapLight, double growCoefficient) {
        this.mapLight = mapLight;
        lightBoost = true;
        this.growCoefficient = growCoefficient;
    }

    public void grow(){
        for (int i = 0; i < map.size(); i++) {
            map.get(i).value += incomeMap.get(i).value * growCoefficient * mapLight.getIllumination(i);
        }
    }
}
