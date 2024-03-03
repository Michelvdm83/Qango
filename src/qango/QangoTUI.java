package qango;

import java.util.ArrayList;
import static generic.CommandLine.*;
import static qango.Player.*;

public class QangoTUI {
    private final Qango6Board board;

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
        board.emptyBoard();
        Player currentPlayer = null;
        Coordinate lastMove;

        do{
            System.out.println(board);
            currentPlayer = (currentPlayer == PLAYER1)? PLAYER2 : PLAYER1;
            lastMove = askForMove(currentPlayer);
            board.placePlayer(currentPlayer, lastMove);

        }while( !(board.playerWon(currentPlayer, lastMove) || board.freeLocations().isEmpty()) );

        System.out.println(board);
        if(board.playerWon(currentPlayer, lastMove)){
            System.out.printf("Congratulation %s, you won!\n", board.getPlayerName(currentPlayer));
        }else{
            System.out.println("It's a draw, well played!");
        }
    }

    private Coordinate askForMove(Player player){
        do{
            String moveChosen = askForString(String.format("%s, what is your next move? ", board.getPlayerName(player))).trim().toLowerCase();
            if(moveChosen.length() != 2){
                System.out.println("Please enter the location in a notation like: a1");
            }else{
                int row = moveChosen.charAt(0)-'a';
                int column = moveChosen.charAt(1)-'0';
                Coordinate chosenCoordinate = new Coordinate(row, column);

                try {
                    if (board.locationTaken(chosenCoordinate)) {
                        System.out.println("Please enter an empty location");
                    } else {
                        return chosenCoordinate;
                    }
                }catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                    System.out.println("Please enter valid locations only!");
                }
            }
        }while(true);
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