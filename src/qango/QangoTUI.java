package qango;

import java.util.ArrayList;
import java.util.function.Function;

import static generic.CommandLine.*;
import static qango.Player.*;

public class QangoTUI {
    private final Qango6Board board;
    private final Function<Boolean, String> showBoard;
    private boolean zoomed = false;

    public static void main(String[] args) {
        QangoTUI qango = new QangoTUI();
        qango.start();
    }

    public QangoTUI(){
        board = new Qango6Board();
        showBoard = zoomed -> zoomed? board.toBigString() : board.toString();
    }

    public void start(){
        var options = new ArrayList<String>();
        options.add("input playernames");
        options.add("start game");
        options.add("show gameboard");
        options.add("enable zoomed view");
        options.add("quit game");

        boolean keepPlaying = true;
        do{
            switch(askForIntFromMenu(options.toArray(String[]::new))){
                case 1 -> setPlayerNames();
                case 2 -> play();
                case 3 -> System.out.println(showBoard.apply(zoomed));
                case 4 -> {
                    zoomed = !zoomed;
                    options.set(3, zoomed? "disable zoomed view" : "enable zoomed view");
                }
                case 5 -> keepPlaying = false;
            }
        }while(keepPlaying);
    }

    private void play(){
        board.emptyBoard();
        Player currentPlayer = null;
        Coordinate lastMove;

        do{
            System.out.println(showBoard.apply(zoomed));
            currentPlayer = (currentPlayer == PLAYER1)? PLAYER2 : PLAYER1;
            lastMove = askForMove(currentPlayer);
            board.placePlayer(currentPlayer, lastMove);

        }while( !(board.playerWon(currentPlayer, lastMove) || board.freeLocations().isEmpty()) );

        System.out.println(showBoard.apply(zoomed));
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

    private void setPlayerNames(){
        board.setPlayerName(PLAYER1, askForString("input name for player 1(white): "));
        board.setPlayerName(PLAYER2, askForString("input name for player 2(black): "));
    }
}

/*
to do:
logische plek maken voor de ON_FIELD string en de string wanneer er geen player op een field staat
keuze toevoegen voor normale grote of "ingezoomd" bord >>> adv deze keuze value setten, zodat toString automatisch het juiste teruggeeft
"lengte" van een veld als variabele opslaan: is afhankelijk van bovenstaande keuze
coordinaten laten lopen van 1 tot 6 ipv 0 tot 5
mogelijk toBigString ipv StringBuilder[] met stream hetzelfde te krijgen?
 */