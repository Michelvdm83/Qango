package qango;

import qango.fielddata.Field;

import java.util.ArrayList;

import static generic.CommandLine.*;
import static qango.Player.*;

public class QangoTUI {
    private Qango6Board board;

    public static void main(String[] args) {
        QangoTUI qango = new QangoTUI();
        qango.start();
    }

    public QangoTUI(){
        board = new Qango6Board();
    }

    public void start(){
        boolean keepPlaying = true;
        do{
            switch(getChoiceFromMenu()){
                case 1 -> setPlayerNames();
                case 2 -> play();
                case 3 -> System.out.println(board);
                default-> keepPlaying = false;
            }
        }while(keepPlaying);
    }

    private void play(){
        Player currentPlayer = null;
        Coordinate lastMove;
        do{
            System.out.println(board);
            currentPlayer = (currentPlayer == PLAYER1)? PLAYER2 : PLAYER1;
            lastMove = askForMove(currentPlayer);
            board.placePlayer(currentPlayer, lastMove);

        }while( !(board.playerWon(currentPlayer, lastMove) || board.freeLocations().isEmpty()) );

        if(board.playerWon(currentPlayer, lastMove)){
            System.out.printf("Congratulation %s, you won!\n", board.getPlayerName(currentPlayer));
        }else{
            System.out.println("It's a draw, well played!");
        }
    }

    private Coordinate askForMove(Player player){
//        var locations = board.freePlaces();

        var freeLocations = board.freeLocations();
        String[] choices = freeLocations.stream().map(Coordinate::toString).toArray(String[]::new);
        //String[] choices = locations.entrySet().stream().map(e -> e.getValue().getColor().andThen(black).apply(e.getKey().toString())).toArray(String[]::new);

//        locations.
        int choice = askForIntFromMenu(String.format("%s, what is your next move? ", board.getPlayerName(player)), choices);

        return freeLocations.get(choice-1);
    }

    private int getChoiceFromMenu(){
        var options = new ArrayList<String>();
        options.add("input playernames");
        options.add("start game");
        options.add("show gameboard");
        options.add("quit game");

        return askForIntFromMenu(options.toArray(String[]::new));
    }

    private void setPlayerNames(){
        board.setPlayerName(PLAYER1, askForString("input name for player 1(white): "));
        board.setPlayerName(PLAYER2, askForString("input name for player 2(black): "));
    }
}
/*
Coordinates in schaaknotatie maken: bvb a1 a2 b1
askForMove aanpassen naar String van lengte 2 vragen, dus de a1 a2 b1 wijze
 */