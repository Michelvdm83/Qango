package qango.fielddata;

public class Field {
    private final FieldColor color;

    private String player;

    public Field(FieldColor color){
        this.color = color;
    }

    public boolean hasPlayer(){
        return player != null;
    }

    public String getPlayer() {
        return player;
    }

    public FieldColor getColor() {
        return color;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public String toString(){
        return player==null? color.apply("   ") : color.apply(player)/*COLOR.apply(" " + player + " ")*/;
    }
}
