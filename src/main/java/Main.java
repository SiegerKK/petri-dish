import logic.Game;
import logic.microbe.Genome;

public class Main {
    public static void main(String args[]){
        System.out.println("Game started.");

        Genome.init();

        testCode();
    }

    public static void testCode(){
        Game game = new Game();
        game.init();
        game.start(2500);
    }
}
