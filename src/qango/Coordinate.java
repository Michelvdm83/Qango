package qango;

import qango.fielddata.Qango6ColorZone;

//van row een char maken? of zorgen dat 'a' altijd gelijk is aan MINIMUM_COORDINATE_VALUE? dmv bvb een variabele distanceToA
public record Coordinate(int row, int column) implements Comparable<Coordinate>{

    public Coordinate {
        if (!isValid(row, column)) throw new IllegalArgumentException("Invalid value at Coordinate creation");
    }

    public static boolean isValid(int row, int column){
        return Math.min(row, column) >= Qango6ColorZone.MINIMUM_COORDINATE_VALUE && Math.max(row, column) <= Qango6ColorZone.MAXIMUM_COORDINATE_VALUE;
    }

    @Override
    public String toString(){
        return String.format("%c%d", 'a'+row, column);
    }

    @Override
    public int compareTo(Coordinate o) {
        if(this.row != o.row){
            return this.row - o.row;
        }
        return this.column - o.column;
    }
}