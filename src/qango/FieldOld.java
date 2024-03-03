package qango;

import static generic.CommandLine.black;

public class FieldOld {
    private final FieldColorOld COLOR;

    private String player;

    public FieldOld(FieldColorOld color){
        COLOR = color;
    }

    public boolean hasPlayer(){
        return player != null;
    }

    public String getPlayer() {
        return player;
    }

    public FieldColorOld getCOLOR() {
        return COLOR;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public String toString(){
        return player==null? COLOR.apply("   ") : COLOR.apply(black.apply(" " + player + " "));
    }
}
