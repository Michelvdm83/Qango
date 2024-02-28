import qango.Coordinate;
import qango.fielddata.Field;
import qango.fielddata.Qango6ColorZones;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import static generic.CommandLine.*;
import static qango.fielddata.FieldColor.*;
import static qango.fielddata.Qango6ColorZones.ZONE_PINK1;

public class Main2 {
    public static void main(String[] args){
        var board = new TreeMap<Coordinate, Field>();
        System.out.println(PINK.andThen(green).apply("Hoi"));

        String player1 = white.apply(" O ");
        String player2 = black.apply(" O ");
        System.out.println(PINK.apply(player1));
        System.out.println(PINK.apply(player2));

        for(Qango6ColorZones cz: Qango6ColorZones.values()){
            for(Coordinate c: cz.getLocations()){
                board.put(c, new Field(cz.getColor()));
            }
        }

        int x = 0;//coordinaten voor invoer a la schaakbord ook printen
        for(Coordinate c: board.keySet()){
            if(x != c.row())System.out.println();
            System.out.print(board.get(c));
            x = c.row();
        }
        System.out.println("\n");

        String currentPlayer = "O";
        board.get(new Coordinate(0,0)).setPlayer(currentPlayer);
        board.get(new Coordinate(1,0)).setPlayer(currentPlayer);
        board.get(new Coordinate(0,1)).setPlayer(currentPlayer);
        //board.get(new Coordinate(0,2)).setPlayer(currentPlayer);
        board.get(new Coordinate(0,3)).setPlayer(currentPlayer);
        board.get(new Coordinate(0,4)).setPlayer(currentPlayer);
        board.get(new Coordinate(0,5)).setPlayer(currentPlayer);

        int row = 0;
        int column = 0;

        //var str = board.entrySet().stream().filter(e -> e.getKey().row()==row && e.getValue().hasPlayer() && e.getValue().getPlayer().equals(currentPlayer));
        var str = board.entrySet().stream().filter(e -> e.getKey().row() == row || e.getKey().column() == column).toList();
        long count = str.stream().filter(e -> e.getKey().row() == row).takeWhile(e -> e.getValue().hasPlayer() && e.getValue().getPlayer().equals(currentPlayer)).count();
        System.out.println(count);
        if(count < 5){
            count = str.stream().filter(e -> e.getKey().row() == row).skip(count+1).takeWhile(e -> e.getValue().hasPlayer() && e.getValue().getPlayer().equals(currentPlayer)).count();
        }
        System.out.println(count);
        //System.out.println(str.filter(e -> e.getKey().column() == column).count());
        //System.out.println(str.filter(e -> e.getKey().row() == row).count());

        ArrayList<Coordinate> lijst2 = new ArrayList<>();
        board.forEach((k,v) -> {if(v.getColor() == PINK && v.getPlayer() != null && v.getPlayer().equals(currentPlayer))lijst2.add(k);});
        System.out.println(lijst2.equals(Arrays.stream(ZONE_PINK1.getLocations()).toList()));

        x = 0;
        for(Coordinate c: board.keySet()){
            if(x != c.row())System.out.println();
            System.out.print(board.get(c));
            x = c.row();
        }
    }
}
