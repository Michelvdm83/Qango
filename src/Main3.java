import qango.Coordinate;
import qango.Qango6Board;

import static generic.CommandLine.black;
import static generic.CommandLine.white;

public class Main3 {
    private static final String PLAYER1 = white.apply(" O ");
    private static final String PLAYER2 = black.apply(" O ");
    private static final Qango6Board game = new Qango6Board();

    public static void main(String[] args){

        System.out.println(game);
        System.out.println();
        game.placePlayer(PLAYER1, new Coordinate(0,0));
        game.placePlayer(PLAYER2, new Coordinate(1,0));
        game.placePlayer(PLAYER1, new Coordinate(0,1));
        game.placePlayer(PLAYER1, new Coordinate(0,2));
        game.placePlayer(PLAYER1, new Coordinate(0,3));
        game.placePlayer(PLAYER2, new Coordinate(0,4));
        game.placePlayer(PLAYER1, new Coordinate(0,5));
        System.out.println(game);

        System.out.println(game.hasPlayerWonByMove(PLAYER1, new Coordinate(0, 5)));
        System.out.println(game.hasPlayerWonByMove(PLAYER2, new Coordinate(0, 5)));
    }
}
