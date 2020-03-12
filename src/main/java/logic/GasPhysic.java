package logic;

import logic.map.MapGas;
import utils.ConsoleManager;
import utils.DoubleBox;

import java.util.ArrayList;

public class GasPhysic {
    public static final double SPREAD_COEFFICIENT = 0.1;
    public static final double RANGE_PHYSIC_LIMIT = 10.0;

    public static void gasesLogicStep(ArrayList<MapGas> maps){
        for (MapGas mapGas : maps) 
            mapGas.resetDeltaMap(0.0);

        for (int cellId = 0; cellId < maps.get(0).getMapSize(); cellId++) {
            //--------------Initialize calculating----------------//
            int cellY = cellId / maps.get(0).getSizeX();
            int cellX = cellId % maps.get(0).getSizeX();

            double localValue = 0.0;
            double neighborValue = 0.0;
            double localPressure = 0.0;
            double neighborPressure = 0.0;

            for (MapGas mapGas : maps)
                localPressure += mapGas.getValue(cellId) * mapGas.massCoefficient;
            //----------------------------------------------------//


            //--------------------Calculating---------------------//
            for (int cellIdAnother = 0; cellIdAnother < maps.get(0).getMapSize(); cellIdAnother++) {
                int cellYAnother = cellIdAnother / maps.get(0).getSizeX();
                int cellXAnother = cellIdAnother % maps.get(0).getSizeX();
//                double range = Math.sqrt((cellX - cellXAnother) * (cellX - cellXAnother) + (cellY - cellYAnother) * (cellY - cellYAnother));
                double range = (cellX - cellXAnother) * (cellX - cellXAnother) + (cellY - cellYAnother) * (cellY - cellYAnother);

                neighborPressure = 0.0;
                for (MapGas mapGas : maps)
                    neighborPressure += mapGas.getValue(cellIdAnother) * mapGas.massCoefficient;

                if(range < RANGE_PHYSIC_LIMIT*RANGE_PHYSIC_LIMIT){
                    //TODO: refactor 124
                    double R = 1 / Math.sqrt(range) / 124;
                    double dP = Math.max(localPressure / neighborPressure - 1, 0);

                    if(dP > 0) {
                        for (MapGas mapGas : maps) {
                            localValue = mapGas.getValue(cellId);
                            double massPercent = localValue * mapGas.massCoefficient / localPressure;
                            double result = localValue * massPercent * dP * R * SPREAD_COEFFICIENT;
                            mapGas.deltaMap.get(cellIdAnother).value += result;
                            mapGas.deltaMap.get(cellId).value -= result;
                        }
                    }
                }
            }
            //----------------------------------------------------//
        }
        for (MapGas mapGas : maps) {
            mapGas.addDeltaMap();
        }
    }
    
    @Deprecated
    public static void difusionStep(ArrayList<MapGas> maps){
        for (MapGas mapGas : maps) {
            mapGas.resetDeltaMap(0.0);
        }

        for (int i = 0; i < maps.get(0).getMapSize(); i++) {
            int y = i / maps.get(0).getSizeX();
            int x = i % maps.get(0).getSizeX();

            double localPressure = 0.0;
            double neighborPressure = 0.0;
            double localValue = 0.0;
            double neighborValue = 0.0;
            //Local pressure
            for (MapGas mapGas : maps){
                localPressure += mapGas.getValue(i) * mapGas.massCoefficient;
            }

            //Up neighbor
            if(y - 1 >= 0){
                //Calculate neighbor pressure
                int id = maps.get(0).getSizeX() * (y - 1) + x;
                neighborPressure = 0.0;
                neighborValue = 0.0;
                for (MapGas mapGas : maps){
                    neighborPressure += mapGas.getValue(id) * mapGas.massCoefficient;
                    neighborValue += mapGas.getValue(id);
                }

                double massPartPercent;
                double gasUnitsToTranslate;
                for (MapGas mapGas : maps){
                    if(localPressure > neighborPressure) {
                        massPartPercent = mapGas.getValue(i) * mapGas.massCoefficient / localPressure;
                        gasUnitsToTranslate = localValue * massPartPercent * (localPressure - neighborPressure) / localPressure * SPREAD_COEFFICIENT;
                        mapGas.deltaMap.get(i).value -= gasUnitsToTranslate;
                        mapGas.deltaMap.get(id).value += gasUnitsToTranslate;
                    } else if(localPressure < neighborPressure) {
                        massPartPercent = mapGas.getValue(id) * mapGas.massCoefficient / neighborPressure;
                        gasUnitsToTranslate = neighborValue * massPartPercent * (neighborPressure - localPressure) / neighborPressure * SPREAD_COEFFICIENT;
                        mapGas.deltaMap.get(i).value += gasUnitsToTranslate;
                        mapGas.deltaMap.get(id).value -= gasUnitsToTranslate;
                    }
                }
            }
            //down neighbor
            if(y + 1 < maps.get(0).getSizeY()){
                //Calculate neighbor pressure
                int id = maps.get(0).getSizeX() * (y + 1) + x;
                neighborPressure = 0.0;
                neighborValue = 0.0;
                for (MapGas mapGas : maps){
                    neighborPressure += mapGas.getValue(id) * mapGas.massCoefficient;
                    neighborValue += mapGas.getValue(id);
                }

                double massPartPercent;
                double gasUnitsToTranslate;
                for (MapGas mapGas : maps){
                    if(localPressure > neighborPressure) {
                        massPartPercent = mapGas.getValue(i) * mapGas.massCoefficient / localPressure;
                        gasUnitsToTranslate = localValue * massPartPercent * (localPressure - neighborPressure) / localPressure * SPREAD_COEFFICIENT;
                        mapGas.deltaMap.get(i).value -= gasUnitsToTranslate;
                        mapGas.deltaMap.get(id).value += gasUnitsToTranslate;
                    } else if(localPressure < neighborPressure) {
                        massPartPercent = mapGas.getValue(id) * mapGas.massCoefficient / neighborPressure;
                        gasUnitsToTranslate = neighborValue * massPartPercent * (neighborPressure - localPressure) / neighborPressure * SPREAD_COEFFICIENT;
                        mapGas.deltaMap.get(i).value += gasUnitsToTranslate;
                        mapGas.deltaMap.get(id).value -= gasUnitsToTranslate;
                    }
                }
            }
            //left neighbor
            if(x - 1 >= 0){
                //Calculate neighbor pressure
                int id = maps.get(0).getSizeX() * y + (x - 1);
                neighborPressure = 0.0;
                neighborValue = 0.0;
                for (MapGas mapGas : maps){
                    neighborPressure += mapGas.getValue(id) * mapGas.massCoefficient;
                    neighborValue += mapGas.getValue(id);
                }

                double massPartPercent;
                double gasUnitsToTranslate;
                for (MapGas mapGas : maps){
                    if(localPressure > neighborPressure) {
                        massPartPercent = mapGas.getValue(i) * mapGas.massCoefficient / localPressure;
                        gasUnitsToTranslate = localValue * massPartPercent * (localPressure - neighborPressure) / localPressure * SPREAD_COEFFICIENT;
                        mapGas.deltaMap.get(i).value -= gasUnitsToTranslate;
                        mapGas.deltaMap.get(id).value += gasUnitsToTranslate;
                    } else if(localPressure < neighborPressure) {
                        massPartPercent = mapGas.getValue(id) * mapGas.massCoefficient / neighborPressure;
                        gasUnitsToTranslate = neighborValue * massPartPercent * (neighborPressure - localPressure) / neighborPressure * SPREAD_COEFFICIENT;
                        mapGas.deltaMap.get(i).value += gasUnitsToTranslate;
                        mapGas.deltaMap.get(id).value -= gasUnitsToTranslate;
                    }
                }
            }
            //right neighbor
            if(x + 1 < maps.get(0).getSizeX()){
                //Calculate neighbor pressure
                int id = maps.get(0).getSizeX() * y + (x + 1);
                neighborPressure = 0.0;
                neighborValue = 0.0;
                for (MapGas mapGas : maps){
                    neighborPressure += mapGas.getValue(id) * mapGas.massCoefficient;
                    neighborValue += mapGas.getValue(id);
                }

                double massPartPercent;
                double gasUnitsToTranslate;
                for (MapGas mapGas : maps){
                    if(localPressure > neighborPressure) {
                        massPartPercent = mapGas.getValue(i) * mapGas.massCoefficient / localPressure;
                        gasUnitsToTranslate = localValue * massPartPercent * (localPressure - neighborPressure) / localPressure * SPREAD_COEFFICIENT;
                        mapGas.deltaMap.get(i).value -= gasUnitsToTranslate;
                        mapGas.deltaMap.get(id).value += gasUnitsToTranslate;
                    } else if(localPressure < neighborPressure) {
                        massPartPercent = mapGas.getValue(id) * mapGas.massCoefficient / neighborPressure;
                        gasUnitsToTranslate = neighborValue * massPartPercent * (neighborPressure - localPressure) / neighborPressure * SPREAD_COEFFICIENT;
                        mapGas.deltaMap.get(i).value += gasUnitsToTranslate;
                        mapGas.deltaMap.get(id).value -= gasUnitsToTranslate;
                    }
                }
            }
        }

        for (MapGas mapGas : maps){
            mapGas.addDeltaMap();
        }
    }
}
