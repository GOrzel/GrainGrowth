package sample.structures;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Material;
import javafx.scene.paint.Paint;
import sample.exceptions.NoBoundaryConditionSet;
import sample.exceptions.WrongCoordinatesException;
import sample.utils.BoundaryConditions;
import sample.utils.Neighbourhoods;
import sample.utils.SimulationParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static sample.utils.SpaceUtils.generateRandomColor;

/**
 * Created by User on 2019-11-16.
 */
public class Space {

    private int height;
    private int width;
    private Cell cells[][];
    private Cell originState[][];


    public Space(int height, int width, int seedsAmount) {
        this.height = height;
        this.width = width;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell();
            }
        }
        prepareSeeds(seedsAmount);
        //For the RESET function
        this.originState = cells.clone();
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
            x = RNG.nextInt(width);
            y = RNG.nextInt(height);
            color = generateRandomColor();
            if (!activeColors.contains(color) && cells[x][y].isEmpty()) {
                activeColors.add(color);
                cells[x][y].setBackgroundColor(color);
                i++;
            }
        }
    }

    public void nextStep(SimulationParameters params) throws Exception{
        Cell[][] nextIteration = cells.clone();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                getNeighbourhood(x,y,params);
            }
        }
    }

    private ArrayList<Cell> getNeighbourhood(int xPos, int yPos, SimulationParameters params) throws WrongCoordinatesException, NoBoundaryConditionSet{
        ArrayList<Cell> neighbours = new ArrayList<>();
        for(Neighbourhoods.Direction direction : params.getNeighbourhood().getDirections()){
            neighbours.add(getNeighbour(xPos, yPos, direction, params.getBoundaryCondition()));
        }
        return neighbours;
    }

    private Cell getNeighbour(int xPos, int yPos, Neighbourhoods.Direction direction, BoundaryConditions.BoundaryCondition boundaryCondition) throws NoBoundaryConditionSet, WrongCoordinatesException {
        switch (direction) {
            case N:
                return getCell(xPos, yPos + 1, boundaryCondition);
            case NE:
                return getCell(xPos + 1, yPos + 1, boundaryCondition);
            case E:
                return getCell(xPos + 1, yPos, boundaryCondition);
            case SE:
                return getCell(xPos + 1, yPos - 1, boundaryCondition);
            case S:
                return getCell(xPos, yPos - 1, boundaryCondition);
            case SW:
                return getCell(xPos - 1, yPos - 1, boundaryCondition);
            case W:
                return getCell(xPos - 1, yPos, boundaryCondition);
            case NW:
                return getCell(xPos - 1, yPos + 1, boundaryCondition);
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

}