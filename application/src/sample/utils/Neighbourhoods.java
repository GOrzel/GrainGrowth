package sample.utils;


/**
 * Created by User on 2019-11-17.
 */
public class Neighbourhoods {

    public enum Direction {
        NW,
        N,
        NE,
        E,
        SE,
        S,
        SW,
        W
    }

    public enum Neighbourhood {
        VON_NEUMANN(DIR_VON_NEUMANN),
        MOORE(DIR_MOORE),
        HEXAGONAL_LEFT(DIR_HEXAGONAL_LEFT),
        HEXAGONAL_RIGHT(DIR_HEXAGONAL_RIGHT),
        PENTAGONAL_LEFT(DIR_PENTAGONAL_LEFT),
        PENTAGONAL_RIGHT(DIR_PENTAGONAL_RIGHT);

        private Direction neighbourhood[];

        Neighbourhood(Direction[] neighbourhood) {
            this.neighbourhood = neighbourhood;
        }

        public Direction[] getDirections() {
            return neighbourhood;
        }
    }

    private static final Direction[] DIR_VON_NEUMANN = {Direction.N, Direction.E, Direction.S, Direction.W};
    private static final Direction[] DIR_MOORE = {Direction.NW, Direction.N, Direction.NE, Direction.E, Direction.SE, Direction.S, Direction.SW, Direction.W};
    private static final Direction[] DIR_HEXAGONAL_LEFT = {Direction.W, Direction.NW, Direction.N, Direction.E, Direction.SE, Direction.S};
    private static final Direction[] DIR_HEXAGONAL_RIGHT = {Direction.N, Direction.NE, Direction.E, Direction.S, Direction.SW, Direction.W};
    private static final Direction[] DIR_PENTAGONAL_LEFT = {Direction.S,Direction.SW,Direction.W,Direction.NW,Direction.N};
    private static final Direction[] DIR_PENTAGONAL_RIGHT = {Direction.N,Direction.NE,Direction.E,Direction.SE,Direction.S};
}
