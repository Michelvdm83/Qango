package qango;

import qango.fielddata.Field;
import qango.fielddata.FieldColor;
import qango.fielddata.Qango6ColorZones;
import static qango.Player.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Qango6Board {
    private final TreeMap<Coordinate, Field> board;
    private final int boardSideLength;
    private final EnumMap<Player, String> playerNames;

    public Qango6Board(){
        playerNames = new EnumMap<>(Player.class);
        playerNames.put(PLAYER1, PLAYER1.getDefaultPlayerName());
        playerNames.put(PLAYER2, PLAYER2.getDefaultPlayerName());
        board = new TreeMap<>();
        for(Qango6ColorZones cz: Qango6ColorZones.values()){
            for(Coordinate c: cz.getLocations()){
                board.put(c, new Field(cz.getColor()));
            }
        }
        boardSideLength = board.keySet().stream().max(Coordinate::compareTo).orElse(new Coordinate(0,0)).row()+1;
    }

    public void emptyBoard(){
        board.values().forEach(field -> field.setPlayer(null));
    }

    public void placePlayer(Player player, Coordinate coordinate){
        if(locationTaken(coordinate)) throw new IllegalArgumentException("Location should first be checked with locationTaken!");
        board.get(coordinate).setPlayer(player);
    }

    private boolean wonByColorZone(Player player, Coordinate lastMove){
        var fieldsTakenByPlayer = board.keySet().stream().filter
                (e -> board.get(e).hasPlayer() && board.get(e).getPlayer().equals(player)).toList();

        var colorZone = Qango6ColorZones.getZoneOfCoordinate(lastMove);
        return new HashSet<>(fieldsTakenByPlayer).containsAll(Arrays.asList(colorZone.getLocations()));
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

    public void setPlayerName(Player player, String name){
        playerNames.put(player, name);
    }

    public String getPlayerName(Player player){
        return playerNames.get(player);
    }

    @Override
    public String toString(){
        StringBuilder boardAsString = new StringBuilder();

        boardAsString.append("  ");
        for(int i = 0; i < boardSideLength; i++){
            boardAsString.append(String.format(" %d ", i));
        }

        int currentRow = -1;
        for(Coordinate c: board.keySet()){
            if(currentRow != c.row()){
                boardAsString.append(String.format("\n%c ", 'a'+c.row()));
            }
            boardAsString.append(board.get(c));
            currentRow = c.row();
        }
        return boardAsString.toString();
    }

    public String toBigString(){
        StringBuilder boardAsString = new StringBuilder("  ");

        //printing the numbers for the coordinates
        IntStream.range(0,boardSideLength).forEachOrdered(i -> boardAsString.append(String.format("    %d    ", i)));
        boardAsString.append("\n");

        int currentRow = -1;
        StringBuilder rowPart1 = new StringBuilder();
        StringBuilder rowPart2 = new StringBuilder();
        StringBuilder rowPart3 = new StringBuilder();
        StringBuilder[] rows = new StringBuilder[3];

        for(Coordinate c: board.keySet()){
            if(currentRow != c.row()){
                rowPart1.delete(0, rowPart1.length()).append("  ");
                rowPart2 = new StringBuilder(String.format("%c ", 'a'+c.row()));
                rowPart3.setLength(0);rowPart3.append("  ");

                rows[0] = new StringBuilder("  ");
                rows[1] = new StringBuilder(String.format("%c ", 'a'+c.row()));
                rows[2] = new StringBuilder("  ");
                currentRow = c.row();
            }

            FieldColor zoneColor = board.get(c).getColor();
            rows[0].append(zoneColor.apply("   ").repeat(3));
            rows[1].append(zoneColor.apply("   "));
            if(board.get(c).hasPlayer()){
                rows[1].append(board.get(c).getPlayer().asBackgroundColor().apply("   "));
            }else{
                rows[1].append(zoneColor.apply("   "));
            }
            rows[1].append(zoneColor.apply("   "));
            rows[2].append(zoneColor.apply("   ").repeat(3));

            rowPart1.append(zoneColor.apply("   ").repeat(3));
            rowPart2.append(zoneColor.apply("   "));
            if(board.get(c).hasPlayer()){
                rowPart2.append(board.get(c).getPlayer().asBackgroundColor().apply("   "));
            }else{
                rowPart2.append(zoneColor.apply("   "));
            }
            rowPart2.append(zoneColor.apply("   "));
            rowPart3.append(zoneColor.apply("   ").repeat(3));

            if(c.column() == boardSideLength-1){
                boardAsString.append(rows[0]).append("\n");
                boardAsString.append(rows[1]).append("\n");
                boardAsString.append(rows[2]).append("\n");
//                boardAsString.append(rowPart1).append("\n");
//                boardAsString.append(rowPart2).append("\n");
//                boardAsString.append(rowPart3).append("\n");
            }
        }

        return boardAsString.toString();
    }
}