package qango.fielddata;

import qango.Player;

public class Field {
    private final FieldColor color;

    private Player player;

    public Field(FieldColor color){
        this.color = color;
        player = null;
    }

    public boolean hasPlayer(){
        return player != null;
    }

    public Player getPlayer() {
        return player;
    }

    public FieldColor getColor() {
        return color;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString(){
        return color.apply(player==null? "   " : player.toString());
    }
}
