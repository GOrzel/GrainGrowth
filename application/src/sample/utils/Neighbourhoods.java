package sample.utils;


import static sample.utils.SpaceUtils.getRandomNumber;

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
        HEXAGONAL_RANDOM(DIR_HEXAGONAL_LEFT, DIR_HEXAGONAL_RIGHT),
        PENTAGONAL_LEFT(DIR_PENTAGONAL_LEFT),
        PENTAGONAL_RIGHT(DIR_PENTAGONAL_RIGHT),
        PENTAGONAL_RANDOM(DIR_PENTAGONAL_LEFT, DIR_PENTAGONAL_RIGHT),
        NEAREST_MOORE(DIR_NEAREST_MOORE, true),
        FURTHEST_MOORE(DIR_FURTHEST_MOORE, true);

        private Direction neighbourhood[][] = new Direction[2][];
        private boolean isGCExclusive;

        Neighbourhood(Direction[] neighbourhood) {
            this.neighbourhood[0] = neighbourhood;
            this.neighbourhood[1] = neighbourhood;
        }

        Neighbourhood(Direction[] neighbourhood, boolean isGCExclusive) {
            this.neighbourhood[0] = neighbourhood;
            this.neighbourhood[1] = neighbourhood;
            this.isGCExclusive = isGCExclusive;
        }

        Neighbourhood(Direction[] neighbourhoodLeft, Direction[] neighbourhoodRight) {
            this.neighbourhood[0] = neighbourhoodLeft;
            this.neighbourhood[1] = neighbourhoodRight;
        }

        public Direction[] getDirections() {
            int size = this.neighbourhood.length;
            return neighbourhood[getRandomNumber(size)];
        }

        public boolean isForNormalMode(){
            return !this.isGCExclusive;
        }
    }

    private static final Direction[] DIR_VON_NEUMANN = {Direction.N, Direction.E, Direction.S, Direction.W};
    private static final Direction[] DIR_MOORE = {Direction.NW, Direction.N, Direction.NE, Direction.E, Direction.SE, Direction.S, Direction.SW, Direction.W};
    private static final Direction[] DIR_HEXAGONAL_LEFT = {Direction.W, Direction.NW, Direction.N, Direction.E, Direction.SE, Direction.S};
    private static final Direction[] DIR_HEXAGONAL_RIGHT = {Direction.N, Direction.NE, Direction.E, Direction.S, Direction.SW, Direction.W};
    private static final Direction[] DIR_PENTAGONAL_LEFT = {Direction.S, Direction.SW, Direction.W, Direction.NW, Direction.N};
    private static final Direction[] DIR_PENTAGONAL_RIGHT = {Direction.N, Direction.NE, Direction.E, Direction.SE, Direction.S};
    private static final Direction[] DIR_NEAREST_MOORE = {Direction.N, Direction.E, Direction.S, Direction.W};
    private static final Direction[] DIR_FURTHEST_MOORE = {Direction.NW, Direction.SW, Direction.SE, Direction.NE};

}
