import logic.Game;
import logic.microbe.Genome;
import utils.ConsoleManager;

public class Main {
    public static void main(String args[]){
        System.out.println("Game started.");

        Genome.init();

        testCode();
//        ConsoleManager.writeln(circleTestCode(5));

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
}
