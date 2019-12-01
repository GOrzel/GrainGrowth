package sample.structures;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import sample.exceptions.NoBoundaryConditionSet;
import sample.exceptions.WrongCoordinatesException;
import sample.utils.BoundaryConditions;
import sample.utils.Neighbourhoods;
import sample.utils.SimulationParameters;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static sample.utils.SpaceUtils.*;

/**
 * Created by User on 2019-11-16.
 */
public class Space {

    private int height;
    private int width;
    private Cell cells[][];
    private Cell originState[][];


    public Space(int height, int width, int seedsAmount, int inclusionsAmount, int minRadius, int maxRadius) {
        this.height = height;
        this.width = width;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell();
            }
        }
        prepareInclusions(inclusionsAmount, minRadius, maxRadius);
        prepareSeeds(seedsAmount);
        //For the RESET function
        this.originState = copyArray(cells);
    }

    public AnchorPane render() {
        AnchorPane view = new AnchorPane();
        double size = 700 / Math.max(height, width);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                AnchorPane temp = cells[i][j].render();
                temp.setPrefSize(size, size);
                temp.setPrefSize(size, size);
                temp.setMaxSize(size, size);
                AnchorPane.setTopAnchor(temp, j * size);
                AnchorPane.setLeftAnchor(temp, i * size);
                view.getChildren().add(temp);
            }
        }

        return view;
    }

    private void prepareSeeds(int seedsAmount) {
        ArrayList<Paint> activeColors = new ArrayList<>();
        Random RNG = new Random();
        Paint color;
        int x;
        int y;
        for (int i = 0; i < seedsAmount; ) {
            x = getRandomNumber(width);
            y = getRandomNumber(height);
            color = generateRandomColor();
            if (!activeColors.contains(color) && cells[x][y].isEmpty()) {
                activeColors.add(color);
                cells[x][y].setBackgroundColor(color);
                i++;
            }
        }
    }

    private void prepareInclusions(int inclusionsAmount, int minRadius, int maxRadius) {
        int x;
        int y;
        int radius;
        for (int counter = 0; counter < inclusionsAmount; ) {
            x = getRandomNumber(width);
            y = getRandomNumber(height);
            radius = minRadius + getRandomNumber(maxRadius - minRadius);

            for (int i = x - radius; i <= x + radius; i++) {
                for (int j = y - radius; j <= y + radius; j++) {
                    if (isInCircle(radius, Math.abs(x - i), Math.abs(y - j)) && i >= 0 && j >= 0 && i < width && j < height) {
                        cells[i][j].setPhase(1);
                        cells[i][j].setBackgroundColor(Cell.INCLUSION_COLOR);
                    }
                }
            }
            counter++;
        }
    }

    public void nextStep(SimulationParameters params) throws Exception {
        Cell nextIteration[][] = copyArray(cells);

        HashMap<Paint, Integer> neighbours;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (cells[x][y].getBackgroundColor().equals(Cell.DEFAULT_COLOR)) {
                    neighbours = getNeighbourGrainsCounted(x, y, params);
                    nextIteration[x][y].setBackgroundColor(getMostPopularColor(neighbours));
                }
            }
        }
        this.cells = nextIteration;
    }

    private Paint getMostPopularColor(HashMap<Paint, Integer> neighbours) {
        int max = 0;
        ArrayList<Paint> candidates = new ArrayList<>();
        for (Integer count : neighbours.values())
            if (count > max) max = count;

        for (Map.Entry<Paint, Integer> a : neighbours.entrySet()) {
            if (a.getValue() >= max)
                candidates.add(a.getKey());
        }
        if (candidates.size() == 0) return Cell.DEFAULT_COLOR;
        return candidates.get(getRandomNumber(candidates.size()));
    }

    private HashMap<Paint, Integer> getNeighbourGrainsCounted(int x, int y, SimulationParameters params) throws Exception {
        HashMap<Paint, Integer> neighboursCounted = new HashMap<>();
        ArrayList<Cell> neighbours;

        neighbours = getNeighbourhoodGrains(x, y, params);

        neighbours.forEach(
                a -> neighboursCounted.putIfAbsent(a.getBackgroundColor(), 0));
        neighbours.forEach(
                a -> neighboursCounted.put(a.getBackgroundColor(), neighboursCounted.get(a.getBackgroundColor()) + 1));
        return neighboursCounted;
    }

    private ArrayList<Cell> getNeighbourhoodGrains(int xPos, int yPos, SimulationParameters params) throws WrongCoordinatesException, NoBoundaryConditionSet {
        ArrayList<Cell> neighbours = new ArrayList<>();
        Cell neighbour;

        for (Neighbourhoods.Direction direction : params.getNeighbourhood().getDirections()) {
            neighbour = getNeighbour(xPos, yPos, direction, params.getBoundaryCondition());
            if (neighbour.isGrain()) {
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    private Cell getNeighbour(int xPos, int yPos, Neighbourhoods.Direction direction, BoundaryConditions.BoundaryCondition boundaryCondition) throws NoBoundaryConditionSet, WrongCoordinatesException {
        switch (direction) {
            case N:
                return getCell(xPos, yPos + 1, boundaryCondition);
            case NE:
                return getCell(xPos - 1, yPos + 1, boundaryCondition);
            case E:
                return getCell(xPos - 1, yPos, boundaryCondition);
            case SE:
                return getCell(xPos - 1, yPos - 1, boundaryCondition);
            case S:
                return getCell(xPos, yPos - 1, boundaryCondition);
            case SW:
                return getCell(xPos + 1, yPos - 1, boundaryCondition);
            case W:
                return getCell(xPos + 1, yPos, boundaryCondition);
            case NW:
                return getCell(xPos + 1, yPos + 1, boundaryCondition);
            default:
                return null;
        }
    }

    private Cell getCell(int xPos, int yPos, BoundaryConditions.BoundaryCondition boundaryCondition) throws WrongCoordinatesException, NoBoundaryConditionSet {
        if (yPos < -1) throw new WrongCoordinatesException("Y position below -1");
        if (xPos < -1) throw new WrongCoordinatesException("X position below -1");
        if (yPos > height) throw new WrongCoordinatesException("Y position over height");
        if (yPos > width) throw new WrongCoordinatesException("X position over width");

        switch (boundaryCondition) {
            case PERIODIC:
                if (xPos == -1) xPos = width - 1;
                if (yPos == -1) yPos = height - 1;
                if (xPos == width) xPos = 0;
                if (yPos == height) yPos = 0;
                return cells[xPos][yPos];
            case NON_PERIODIC:
                if (xPos == -1 || yPos == -1 || xPos == width || yPos == height) return new Cell();
                return cells[xPos][yPos];
            default:
                throw new NoBoundaryConditionSet("Unspecified boundary condition");
        }
    }

    public void reset() {
        this.cells = copyArray(originState);
        this.height = this.cells[0].length;
        this.width = this.cells.length;
    }

    public void saveToCsv() throws IOException {
        saveArrayToCsv(this.cells);
    }

    public void loadFromCsv() throws IOException {
        this.cells = loadArrayFromCsv();
        this.height = this.cells[0].length;
        this.width = this.cells.length;

    }

    public void saveToBitmap() throws IOException {
        WritableImage snapshot = this.render().snapshot(new SnapshotParameters(), null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        File file = new File("bitmap.bmp");
        ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
    }
}