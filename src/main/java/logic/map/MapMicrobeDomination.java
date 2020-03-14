package logic.map;

import logic.microbe.Genome;

import java.util.ArrayList;

@Deprecated
public class MapMicrobeDomination {
    private ArrayList<MapMicrobe> microbeMaps;

    public MapMicrobeDomination(ArrayList<MapMicrobe> maps){
        this.microbeMaps.addAll(maps);
    }

    public void printConsole(){
        ArrayList<Genome> dominatedGenomes = new ArrayList<>();
        ArrayList<Integer> dominatedGenomesValue = new ArrayList<>();

        for (int i = 0; i < microbeMaps.size(); i++) {

        }
    }
}
