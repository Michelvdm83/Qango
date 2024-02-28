package qango;

public record Coordinate(int row, int column) implements Comparable<Coordinate>{

    @Override
    public int compareTo(Coordinate o) {
        if(this.row != o.row){
            return this.row - o.row;
        }
        return this.column - o.column;
    }
}
