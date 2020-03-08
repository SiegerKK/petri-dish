package logic.map;

import utils.DoubleBox;

import java.util.ArrayList;

public class MapGas extends MapDouble {
    public ArrayList<DoubleBox> deltaMap;
    public double massCoefficient;

    public MapGas(int x, int y, double initValue, String name, double minLimit, double maxLimit, double massCoefficient){
        super(x, y, initValue, name, minLimit, maxLimit);

        this.massCoefficient = massCoefficient;

        deltaMap = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            deltaMap.add(new DoubleBox(0.0));
        }
    }

    public void addDeltaMap(){
        for (int i = 0; i < map.size(); i++) {
            map.get(i).value += deltaMap.get(i).value;
        }
    }

    public void resetDeltaMap(double value){
        for(DoubleBox doubleBox : deltaMap)
            doubleBox.value = value;
    }
}
