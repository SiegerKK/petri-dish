import logic.Game;
import logic.microbe.Genom;

public class Main {
    public static void main(String args[]){
        System.out.println("Game started.");

        Genom.init();

        testCode();
    }

    public static void testCode(){
        Game game = new Game();
        game.init();
        game.start(300);
    }
}
