package qango;

public record Coordinate(int row, int column) implements Comparable<Coordinate>{

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
