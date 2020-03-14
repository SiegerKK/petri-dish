package logic.map;

import utils.boxes.DoubleBox;

import java.util.ArrayList;

public class MapGas extends MapDouble {
    private ArrayList<DoubleBox> deltaMap;
    public double massCoefficient;

    public MapGas(int x, int y, double initValue, String name, double minLimit, double maxLimit, double massCoefficient){
        super(x, y, initValue, name, minLimit, maxLimit);

        this.massCoefficient = massCoefficient;

        deltaMap = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            deltaMap.add(new DoubleBox(0.0));
        }
    }


    public double getDeltaMapValue(int x, int y){
        return getDeltaMapValue(y * sizeX + x);
    }
    public double getDeltaMapValue(int id){
        return deltaMap.get(id).getValue();
    }
    public void setDeltaMapValue(int x, int y, double value){
        setDeltaMapValue(y * sizeX + x, value);
    }
    public void setDeltaMapValue(int id, double value){
        deltaMap.get(id).setValue(value);
    }
    public double increaseDeltaMapValue(int x, int y, double value){
        return increaseDeltaMapValue(y * sizeX + x, value);
    }
    public double increaseDeltaMapValue(int id, double value){
        deltaMap.get(id).addValue(value);
        return deltaMap.get(id).getValue();
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
