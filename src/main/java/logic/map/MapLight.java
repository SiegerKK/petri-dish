package logic.map;

public class MapLight extends MapDouble {
    public static final String TEMPLATE_100 = "100";
    public static final String TEMPLATE_50_RIGHT = "50R";
    public static final String TEMPLATE_25_RIGHT = "25R";
    public static final String TEMPLATE_50_CENTER = "50C";
    public static final String TEMPLATE_50_CENTER_GRADIENT = "50CG";

    public MapLight(int x, int y, String name) {
        super(x, y, 0, name, 0.0, 100.0);
    }

    public void setTemplate(String template){
        switch (template){
            case TEMPLATE_100:
                for (int i = 0; i < map.size(); i++)
                    map.get(i).value = 100.0;
                break;
            case TEMPLATE_50_RIGHT:
                for (int i = 0; i < getSizeY(); i++)
                    for (int j = getSizeX() / 2; j < getSizeX(); j++)
                        setValue(j, i, 100.0);
                break;
            case TEMPLATE_25_RIGHT:
                for (int i = 0; i < getSizeY(); i++)
                    for (int j = getSizeX() / 4 * 3; j < getSizeX(); j++)
                        setValue(j, i, 100.0);
                break;
            case TEMPLATE_50_CENTER:
                for (int i = getSizeY() / 8; i < getSizeY() / 8 * 7; i++)
                    for (int j = getSizeX() / 8; j < getSizeX() / 8 * 7; j++)
                        setValue(j, i, 50.0);
                for (int i = getSizeY() / 4; i < getSizeY() / 4 * 3; i++)
                    for (int j = getSizeX() / 4; j < getSizeX() / 4 * 3; j++)
                        setValue(j, i, 100.0);
                break;
            case TEMPLATE_50_CENTER_GRADIENT:
                int centerX = getSizeX() / 2;
                int centerY = getSizeY() / 2;


                for (int i = 0; i < getSizeY(); i++) {
                    for (int j = 0; j < getSizeX(); j++) {
                        double range = Math.sqrt((j - centerX)*(j - centerX) + (i - centerY)*(i - centerY));
                        setValue(j, i, maxLimit * ((getSizeX() - range) / getSizeX()));
                    }
                }
                break;
        }
    }

    public double getIllumination(int id){
        return getValue(id) / maxLimit;
    }
    public double getIllumination(int x, int y){
        return getValue(x, y) / maxLimit;
    }
}
