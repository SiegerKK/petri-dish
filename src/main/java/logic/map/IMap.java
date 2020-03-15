package logic.map;

import utils.ConsoleManager;

import java.util.ArrayList;

public interface IMap<T> {
    public T getValue(int x, int y);
    public T getValue(int id);
    public void setValue(int x, int y, T value);
    public void setValue(int id, T value);
    public T increaseValue(int x, int y, T value);
    public T increaseValue(int id, T value);

    public int getMapSize();

    public void printConsole(int x, int y);

    public void reset(T value);

    //---------------------Static methods-------------------//

    public static void printMaps(ArrayList<IMap> maps, int sizeX, int sizeY){
        for (int i = 0; i < maps.size(); i++) {
            IMap map = maps.get(i);
            map.printConsole(sizeX, sizeY);
            ConsoleManager.shift += sizeX + 2;
            ConsoleManager.moveCursorUp(sizeY + 2);
        }
        ConsoleManager.shift = 0;
        ConsoleManager.moveCursorDown(sizeY + 2);
    }
}
