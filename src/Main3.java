import qango.Coordinate;
import qango.Player;
import qango.Qango6Board;

import static generic.CommandLine.*;

public class Main3 {
    private static final Player PLAYER1 = Player.PLAYER1;
    private static final Player PLAYER2 = Player.PLAYER2;
    private static final Qango6Board game = new Qango6Board();

    public static void main(String[] args){

        System.out.println(game);
        System.out.println();
        game.placePlayer(PLAYER2, new Coordinate(0,0));
        game.placePlayer(PLAYER2, new Coordinate(1,0));
        game.placePlayer(PLAYER1, new Coordinate(0,1));
        game.placePlayer(PLAYER1, new Coordinate(0,2));
        game.placePlayer(PLAYER2, new Coordinate(0,3));
        game.placePlayer(PLAYER1, new Coordinate(0,4));
        game.placePlayer(PLAYER1, new Coordinate(0,5));
        game.placePlayer(PLAYER2, new Coordinate(1,4));
        game.placePlayer(PLAYER2, new Coordinate(2,5));
        game.placePlayer(PLAYER1, new Coordinate(1,2));
        game.placePlayer(PLAYER1, new Coordinate(2,2));
        game.placePlayer(PLAYER1, new Coordinate(1,3));
        game.placePlayer(PLAYER1, new Coordinate(2,3));
        System.out.println(game);

        System.out.println("Player1 move 2,3 win?" + game.playerWon(PLAYER1, new Coordinate(2, 3)));
        System.out.println("Player2 move 0,3 win?" + game.playerWon(PLAYER2, new Coordinate(0, 3)));

        System.out.println(askForMove(PLAYER1));
        System.out.println(askForMove(PLAYER2));
    }

    private static Coordinate askForMove(Player player){
        var freeLocations = game.freeLocations();
        String[] choices = freeLocations.stream().map(Coordinate::toString).toArray(String[]::new);

        int choice = askForIntFromMenu(String.format("%s, what is your next move? ", player.name()), choices);

        return freeLocations.get(choice-1);
    }
}