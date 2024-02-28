package qango;

import qango.fielddata.Field;
import qango.fielddata.Qango6ColorZones;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static generic.CommandLine.black;
import static generic.CommandLine.white;

public class Qango6Board {
    private final TreeMap<Coordinate, Field> BOARD;
//    private final String PLAYER1;
//    private final String PLAYER2;

    public Qango6Board(){
        BOARD = new TreeMap<>();
        for(Qango6ColorZones cz: Qango6ColorZones.values()){
            for(Coordinate c: cz.getLocations()){
                BOARD.put(c, new Field(cz.getColor()));
            }
        }
    }

    public void placePlayer(String player, Coordinate coordinate){
        if(isLocationTaken(coordinate)) throw new IllegalArgumentException("Location should first be checked with isLocationTaken!");
        BOARD.get(coordinate).setPlayer(player);
    }

    public boolean hasPlayerWonByMove(String player, Coordinate lastMove){
        var fieldsTakenByPlayer = BOARD.entrySet().stream().filter
                (e -> e.getValue().hasPlayer() && e.getValue().getPlayer().equals(player)).toList();

        var fieldsTakenByPlayer2 = BOARD.keySet().stream().filter
                (e -> BOARD.get(e).hasPlayer() && BOARD.get(e).getPlayer().equals(player)).toList();

        var colorZone = Qango6ColorZones.getZoneOfCoordinate(lastMove);
        if(new HashSet<>(fieldsTakenByPlayer2).containsAll(Arrays.asList(colorZone.getLocations()))) {
            return true;
        }
        if(fieldsTakenByPlayer.size() < 5) return false;


        return true;
    }

    private boolean playerWon(String player, Coordinate lastMove){
        int row = lastMove.row();
        Predicate<Coordinate> rowFilter = coordinate -> coordinate.row() == row;
        return checkLineForWin(player, rowFilter);
    }

    private boolean checkLineForWin(String player, Predicate<Coordinate> filter){
        var coordList = BOARD.keySet().stream().filter(filter).toList();

        int concurrentFieldsTaken = 0;
        for(Coordinate c: coordList){
            if(BOARD.get(c).getPlayer().equals(player)){
                concurrentFieldsTaken++;
                if(concurrentFieldsTaken >= 5) return true;
            } else{
                concurrentFieldsTaken = 0;
            }
        }
        return false;
    }

    private boolean winRowCheck(String player, Coordinate lastMove){
        int row = lastMove.row();
        Predicate<Coordinate> rowCheck = coordinate -> coordinate.row() == row;

        var coordList = BOARD.keySet().stream().filter(c -> c.row() == row).toList();
        int concurrentFieldsTaken = 0;
        for(Coordinate c: coordList){
            if(BOARD.get(c).getPlayer().equals(player)){
                concurrentFieldsTaken++;
                if(concurrentFieldsTaken >= 5) return true;
            } else{
                concurrentFieldsTaken = 0;
            }
        }
        return false;
    }

    private boolean winColumnCheck(String player, Coordinate lastMove){
        int column = lastMove.column();
        var coordList = BOARD.keySet().stream().filter(c -> c.column() == column).toList();
        int concurrentFieldsTaken = 0;
        for(Coordinate c: coordList){
            if(BOARD.get(c).getPlayer().equals(player)){
                concurrentFieldsTaken++;
                if(concurrentFieldsTaken >= 5) return true;
            } else{
                concurrentFieldsTaken = 0;
            }
        }
        return false;
    }

    private boolean winDiagonolSlashCheck(String player, Coordinate lastMove){
        var coordList = BOARD.keySet().stream().filter(c -> c.column() + c.row() == lastMove.column() + lastMove.row()).toList();
        int concurrentFieldsTaken = 0;
        for(Coordinate c: coordList){
            if(BOARD.get(c).getPlayer().equals(player)){
                concurrentFieldsTaken++;
                if(concurrentFieldsTaken >= 5) return true;
            } else{
                concurrentFieldsTaken = 0;
            }
        }
        return false;
    }

    private boolean winDiagonalBackslashCheck(String player, Coordinate lastMove){
        var coordList = BOARD.keySet().stream().filter(c -> c.column() - c.row() == lastMove.column() - lastMove.row()).toList();
        int concurrentFieldsTaken = 0;
        for(Coordinate c: coordList){
            if(BOARD.get(c).getPlayer().equals(player)){
                concurrentFieldsTaken++;
                if(concurrentFieldsTaken >= 5) return true;
            } else{
                concurrentFieldsTaken = 0;
            }
        }
        return false;
    }

    public boolean isLocationTaken(Coordinate location){
        if(!BOARD.containsKey(location))throw new IllegalArgumentException("Coordinate " + location + " is not on the board!");
        return BOARD.get(location).hasPlayer();
    }

    @Override
    public String toString(){
        StringBuilder boardAsString = new StringBuilder();

        int currentRow = 0;//coordinaten voor invoer a la schaakbord ook printen
        for(Coordinate c: BOARD.keySet()){
            if(currentRow != c.row())boardAsString.append("\n");
            boardAsString.append(BOARD.get(c));
            currentRow = c.row();
        }
        return boardAsString.toString();
    }
}