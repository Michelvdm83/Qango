//test omgeving voor ideëen

import qango.Coordinate;
import qango.FieldColorOld;
import qango.FieldOld;

import java.util.*;

import static generic.CommandLine.*;
import static qango.FieldColorOld.*;

public class Main {
    public static void main(String[] args) {

        System.out.println(PINK.apply(white.apply("Hello world!")));
        System.out.println(PINK.getBackgroundColorString() + "\u001B[38;2;0;0;0m" + "Hello world!");

        System.out.println(ORANGE.getBackgroundColorString() + "\u001B[37m" + "Hello world!");
        System.out.println("\u001B[38;2;255;255;255m" + "Hello world!");
        System.out.println(black.apply(ORANGE.apply("Hello world!")));
        System.out.println();

        var board = new TreeMap<Coordinate, FieldOld>();

        for(FieldColorOld fc: FieldColorOld.values()){
            for(Coordinate c: fc.getQANGO6Coordinates()){
                board.put(c, new FieldOld(fc));
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

//        int row = 0;
//        int column = 0;
//        ArrayList<Coordinate> rowLine = new ArrayList<>();
//        ArrayList<Coordinate> columnLine = new ArrayList<>();
//        ArrayList<Coordinate> DiagLine1 = new ArrayList<>();
//        ArrayList<Coordinate> DiagLine2 = new ArrayList<>();
//
//        board.forEach((k,v) -> {
//            if(k.row() == row)rowLine.add(k);
//            if(k.column() == column)columnLine.add(k);
//        });

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
        board.forEach((k,v) -> {if(v.getCOLOR() == PINK && v.getPlayer() != null && v.getPlayer().equals(currentPlayer))lijst2.add(k);});
        System.out.println(lijst2.equals(Arrays.stream(PINK.getQANGO6Coordinates()).toList()));

        x = 0;
        for(Coordinate c: board.keySet()){
            if(x != c.row())System.out.println();
            System.out.print(board.get(c));
            x = c.row();
        }
    }
}
/*
Raw use of parameterized class 'Comparable'
 Inspection info: Reports generic classes with omitted type parameters.
 Such raw use of generic types is valid in Java, but it defeats the purpose of type parameters and may mask bugs.
  This inspection mirrors the rawtypes warning of javac.
Examples:
//warning: Raw use of parameterized class 'List'
List list = new ArrayList<String>();
//list of strings was created but integer is accepted as well
list.add(1);
//no warning as it's impossible to provide type arguments during array creation
IntFunction<List<?>[]> fun = List[]::new;
 */