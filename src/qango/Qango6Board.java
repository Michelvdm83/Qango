package qango;

import qango.fielddata.Field;
import qango.fielddata.Qango6ColorZones;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Qango6Board {
    private final TreeMap<Coordinate, Field> board;
    private final int lowestCoordinateNumber;
    private final int highestCoordinateNumber;

    public Qango6Board(){
        board = new TreeMap<>();
        for(Qango6ColorZones cz: Qango6ColorZones.values()){
            for(Coordinate c: cz.getLocations()){
                board.put(c, new Field(cz.getColor()));
            }
        }
        lowestCoordinateNumber = board.firstKey().row();
        highestCoordinateNumber = board.lastKey().row();
    }

    public void emptyBoard(){
        board.values().forEach(field -> field.setPlayer(null));
    }

    public void placePlayer(Player player, Coordinate coordinate){
        if(locationTaken(coordinate)) throw new IllegalArgumentException("Location should first be checked with locationTaken!");
        board.get(coordinate).setPlayer(player);
    }

    private boolean wonByColorZone(Player player, Coordinate lastMove){
        return Arrays.stream(Qango6ColorZones.getZoneOfCoordinate(lastMove).getLocations()).allMatch(c ->
                board.get(c).hasPlayer() && board.get(c).getPlayer().equals(player));
    }

    public boolean playerWon(Player player, Coordinate lastMove){
        int row = lastMove.row();
        int column = lastMove.column();

        Predicate<Coordinate> rowFilter = coordinate -> coordinate.row() == row;
        Predicate<Coordinate> columnFilter = coordinate -> coordinate.column() == column;
        Predicate<Coordinate> diagonalSlashFilter = coordinate -> coordinate.column() + coordinate.row() == column + row;
        Predicate<Coordinate> diagonalBackSlashFilter = coordinate -> coordinate.column() - coordinate.row() == column - row;

        boolean straightLineWin = wonByLine(player, rowFilter) || wonByLine(player, columnFilter);
        boolean diagonalLineWin = wonByLine(player, diagonalSlashFilter) || wonByLine(player, diagonalBackSlashFilter);

        return straightLineWin || diagonalLineWin || wonBySquare(player, lastMove) || wonByColorZone(player, lastMove);
    }

    private boolean wonBySquare(Player player, Coordinate lastMove){
        int row = lastMove.row();
        int column = lastMove.column();
        var startingCoordinates = board.keySet().stream().filter(c -> (c.row() == row || c.row() == row-1) && (c.column() == column || c.column() == column-1)).toList();

        for(Coordinate coordinate: startingCoordinates){
            if(board.keySet().stream().filter(coord ->
                    (coord.row() == coordinate.row() || coord.row() == coordinate.row()+1) &&
                            (coord.column() == coordinate.column() || coord.column() == coordinate.column()+1) &&
                            board.get(coord).hasPlayer() && board.get(coord).getPlayer().equals(player)).count() == 4) return true;
        }
        return false;
    }

    private boolean wonByLine(Player player, Predicate<Coordinate> filter){
        var coordList = board.keySet().stream().filter(filter).toList();

        int concurrentFieldsTaken = 0;
        for(Coordinate c: coordList){
            if(board.get(c).hasPlayer() && board.get(c).getPlayer().equals(player)){
                concurrentFieldsTaken++;
                if(concurrentFieldsTaken >= 5) return true;
            } else{
                concurrentFieldsTaken = 0;
            }
        }
        return false;
    }

    public boolean locationTaken(Coordinate location){
        if(!board.containsKey(location))throw new IllegalArgumentException("Coordinate " + location + " is not on the board!");
        return board.get(location).hasPlayer();
    }

    public ArrayList<Coordinate> freeLocations(){
        return board.keySet().stream().filter(coordinate -> !board.get(coordinate).hasPlayer()).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String toString(){
        String rowLegendFormat = "%c ";
        StringBuilder boardAsString = new StringBuilder(String.format(rowLegendFormat, ' '));

        IntStream.rangeClosed(lowestCoordinateNumber, highestCoordinateNumber).forEachOrdered(i -> boardAsString.append(String.format(" %d ", i)));
        boardAsString.append("\n");

        IntStream.rangeClosed(lowestCoordinateNumber, highestCoordinateNumber).forEachOrdered(i -> {
            boardAsString.append(String.format("%c ", 'a'+i));
            board.keySet().stream().filter(c -> c.row() == i).forEach(coord -> boardAsString.append(board.get(coord)));
            boardAsString.append("\n");
        });

        return boardAsString.toString();
    }

    public String toBigString(){
        String rowLegendFormat = "%c ";
        StringBuilder boardAsString = new StringBuilder(String.format(rowLegendFormat, ' '));

        //printing the numbers for the coordinates
        IntStream.rangeClosed(lowestCoordinateNumber, highestCoordinateNumber).forEachOrdered(i -> boardAsString.append(String.format("    %d    ", i)));
        boardAsString.append("\n");

        StringBuilder[] linesPerRow = new StringBuilder[3];

        IntStream.rangeClosed(lowestCoordinateNumber, highestCoordinateNumber).forEachOrdered(i -> {
            linesPerRow[0] = new StringBuilder(String.format(rowLegendFormat, ' '));
            linesPerRow[1] = new StringBuilder(String.format(rowLegendFormat, 'a'+i));
            linesPerRow[2] = new StringBuilder(String.format(rowLegendFormat, ' '));

            board.keySet().stream().filter(c -> c.row() == i).forEach(coord -> {
                String[] fieldLines = board.get(coord).toBigString().split("\n");
                IntStream.range(0, linesPerRow.length).forEachOrdered(iterator -> linesPerRow[iterator].append(fieldLines[iterator]));
            });

            Arrays.stream(linesPerRow).forEach(sb -> boardAsString.append(sb).append("\n"));
        });

        return boardAsString.toString();
    }
}