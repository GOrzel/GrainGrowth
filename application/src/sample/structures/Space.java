package sample.structures;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Random;

import static sample.utils.SpaceUtils.generateRandomColor;

/**
 * Created by User on 2019-11-16.
 */
public class Space {

    private int height;
    private int width;
    private Cell cells[][];



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

    private void prepareSeeds(int seedsAmount){
        ArrayList<Paint> activeColors = new ArrayList<>();
        Random RNG = new Random();
        Paint color;
        int x;
        int y;
        for (int i = 0; i < seedsAmount;){
            x = RNG.nextInt(width);
            y = RNG.nextInt(height);
            color = generateRandomColor();
            if (!activeColors.contains(color) && cells[x][y].isEmpty()){
                activeColors.add(color);
                cells[x][y].setBackgroundColor(color);
                i++;
            }
        }
    }

}
