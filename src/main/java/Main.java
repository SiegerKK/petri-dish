import logic.Game;
import logic.microbe.Genome;
import utils.Color;
import utils.ConsoleManager;

public class Main {
    public static void main(String args[]){
        System.out.println("Game started.");

        Genome.init();

        testCode();
//        ConsoleManager.writeln(circleTestCode(5));
//        colorTest();
//        ConsoleManager.writeln(Genome.getSymbol("A1D1"));
//        ConsoleManager.writeln(Genome.getSymbol("A1B1D1"));
//        ConsoleManager.writeln(Genome.getSymbol("A3m2D1"));

        ConsoleManager.writeln();
    }

    private static void testCode(){
        Game game = new Game();
        game.DEBUG_MODE = true;
        game.init();
        game.start(2500);
    }

    private static int circleTestCode(int radius){
        int counter = 0;
        for (int i = 0; i < radius*2 + 1; i++) {
            for (int j = 0; j < radius*2 + 1; j++) {
                double range = Math.sqrt((i-radius)*(i-radius)+(j-radius)*(j-radius));
                if(range > radius) {
                    ConsoleManager.write(".");
                    counter++;
                } else
                    ConsoleManager.write("X");
            }
            ConsoleManager.writeln();
        }
        return counter;
    }
    private static void colorTest(){
        int spaceCounter = 0;
        for (int code = 16; code < 256; code++) {
            System.out.print("\u001b[38;5;" + code + "m\t" + code);

            spaceCounter++;
            if(spaceCounter >= 6) {
                System.out.println();
                spaceCounter = 0;
            }
        }

        ConsoleManager.writeln("X", Color.getColor(0, 1.0, 0));
    }
}
