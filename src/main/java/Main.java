import logic.Game;

public class Main {
    public static void main(String args[]){
        System.out.println("Game started.");

        testCode();
    }

    public static void testCode(){
        Game game = new Game();
        game.start(300);
    }
}
