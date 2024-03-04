package qango;

import java.util.function.UnaryOperator;

import static generic.AnsiColors.RESET;
import static generic.CommandLine.white;
import static generic.CommandLine.black;

public enum Player{
    PLAYER1(white, "Player1"),
    PLAYER2(black, "Player2");

    Player(UnaryOperator<String> playerColor, String defaultPlayerName){
        this.playerColor = playerColor;
        this.defaultPlayerName = defaultPlayerName;
    }

    private final UnaryOperator<String> playerColor;
    private final String defaultPlayerName;
    private final static String ON_FIELD_SIGN = " O ";

    public String getDefaultPlayerName() {
        return defaultPlayerName;
    }

    public UnaryOperator<String> asBackgroundColor(){
        if(this == PLAYER2) return original -> "\u001B[48;2;0;0;0m" + original + RESET.getColor();
        else return original -> "\u001B[48;2;255;255;255m" + original + RESET.getColor();
    }

    @Override
    public String toString(){
        return playerColor.apply(ON_FIELD_SIGN);
    }
}
