package qango;

import static generic.AnsiColors.RESET;

public enum FieldColorOld {
    PINK(255,192,203),
    ORANGE(255, 165, 0),
    BLUE(0, 0, 255),
    YELLOW(255,255,0),
    GREEN(0,255,0),
    PINK2(255,192,203),
    ORANGE2(255, 165, 0),
    BLUE2(0, 0, 255),
    YELLOW2(255,255,0),
    GREEN2(0,255,0),
    PINK3(255,192,203),
    PINK4(255,192,203);

    FieldColorOld(int r, int g, int b){
        backgroundColorString = String.format("\u001B[48;2;%d;%d;%dm", r, g, b);
        QANGO6 = switch(this){
            case PINK -> new Coordinate[]{new Coordinate(0,0), new Coordinate(0,1), new Coordinate(1,0)};
            case YELLOW -> new Coordinate[]{new Coordinate(0,2), new Coordinate(1,1), new Coordinate(2,0)};
            case GREEN -> new Coordinate[]{new Coordinate(1,2), new Coordinate(2,2), new Coordinate(2,1)};

            case PINK2 -> new Coordinate[]{new Coordinate(0,4), new Coordinate(0,5), new Coordinate(1,5)};
            case ORANGE -> new Coordinate[]{new Coordinate(0,3), new Coordinate(1,4), new Coordinate(2,5)};
            case BLUE -> new Coordinate[]{new Coordinate(1,3), new Coordinate(2,3), new Coordinate(2,4)};

            case PINK3 -> new Coordinate[]{new Coordinate(4,0), new Coordinate(5,1), new Coordinate(5,0)};
            case ORANGE2 -> new Coordinate[]{new Coordinate(3,0), new Coordinate(4,1), new Coordinate(5,2)};
            case BLUE2 -> new Coordinate[]{new Coordinate(3,1), new Coordinate(3,2), new Coordinate(4,2)};

            case PINK4 -> new Coordinate[]{new Coordinate(5,4), new Coordinate(5,5), new Coordinate(4,5)};
            case YELLOW2 -> new Coordinate[]{new Coordinate(5,3), new Coordinate(4,4), new Coordinate(3,5)};
            case GREEN2 -> new Coordinate[]{new Coordinate(3,3), new Coordinate(3,4), new Coordinate(4,3)};
        };
    }

    private final String backgroundColorString;
    private final Coordinate[] QANGO6;

    public String getBackgroundColorString(){
        return backgroundColorString;
    }

    public String apply(String original){
        return backgroundColorString + original + RESET.getColor();
    }

    public Coordinate[] getQANGO6Coordinates() {
        return QANGO6;
    }
}