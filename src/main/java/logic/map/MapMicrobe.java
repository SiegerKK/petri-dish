package logic.map;

import logic.microbe.Genome;

@Deprecated
public class MapMicrobe extends MapInteger {
    private Genome genome;
    private String color;

    public MapMicrobe(int x, int y, int initValue, String name, int minLimit, int maxLimit, Genome genome, String color) {
        super(x, y, initValue, name, minLimit, maxLimit);
        this.genome = new Genome(genome);
        this.color = color;
    }

    public Genome getGenome() {
        return genome;
    }
    public String getColor(){
        return color;
    }
}
