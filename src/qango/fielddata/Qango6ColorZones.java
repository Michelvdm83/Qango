package qango.fielddata;

import qango.Coordinate;

import static qango.fielddata.FieldColor.*;

public enum Qango6ColorZones {
    ZONE_PINK1(PINK),
    ZONE_PINK2(PINK),
    ZONE_PINK3(PINK),
    ZONE_PINK4(PINK),
    ZONE_YELLOW1(YELLOW),
    ZONE_YELLOW2(YELLOW),
    ZONE_GREEN1(GREEN),
    ZONE_GREEN2(GREEN),
    ZONE_ORANGE1(ORANGE),
    ZONE_ORANGE2(ORANGE),
    ZONE_BLUE1(BLUE),
    ZONE_BLUE2(BLUE);

    Qango6ColorZones(FieldColor color){
        COLOR = color;

        LOCATIONS = switch(this){
            case ZONE_PINK1 -> new Coordinate[]{new Coordinate(0,0), new Coordinate(0,1), new Coordinate(1,0)};
            case ZONE_YELLOW1 -> new Coordinate[]{new Coordinate(0,2), new Coordinate(1,1), new Coordinate(2,0)};
            case ZONE_GREEN1 -> new Coordinate[]{new Coordinate(1,2), new Coordinate(2,2), new Coordinate(2,1)};

            case ZONE_PINK2 -> new Coordinate[]{new Coordinate(0,4), new Coordinate(0,5), new Coordinate(1,5)};
            case ZONE_ORANGE1 -> new Coordinate[]{new Coordinate(0,3), new Coordinate(1,4), new Coordinate(2,5)};
            case ZONE_BLUE1 -> new Coordinate[]{new Coordinate(1,3), new Coordinate(2,3), new Coordinate(2,4)};

            case ZONE_PINK3 -> new Coordinate[]{new Coordinate(4,0), new Coordinate(5,1), new Coordinate(5,0)};
            case ZONE_ORANGE2 -> new Coordinate[]{new Coordinate(3,0), new Coordinate(4,1), new Coordinate(5,2)};
            case ZONE_BLUE2 -> new Coordinate[]{new Coordinate(3,1), new Coordinate(3,2), new Coordinate(4,2)};

            case ZONE_PINK4 -> new Coordinate[]{new Coordinate(5,4), new Coordinate(5,5), new Coordinate(4,5)};
            case ZONE_YELLOW2 -> new Coordinate[]{new Coordinate(5,3), new Coordinate(4,4), new Coordinate(3,5)};
            case ZONE_GREEN2 -> new Coordinate[]{new Coordinate(3,3), new Coordinate(3,4), new Coordinate(4,3)};
        };
    }

    private final FieldColor COLOR;
    private final Coordinate[] LOCATIONS;

    public FieldColor getColor() {
        return COLOR;
    }

    public Coordinate[] getLocations() {
        return LOCATIONS;
    }

    public static Qango6ColorZones getZoneOfCoordinate(Coordinate coordinate){
        for(Qango6ColorZones zone: Qango6ColorZones.values()){
            for(Coordinate c: zone.LOCATIONS){
                if(c == coordinate) return zone;
            }
        }
        throw new IllegalArgumentException("There is no zone with Coordinate " + coordinate);
    }
}
