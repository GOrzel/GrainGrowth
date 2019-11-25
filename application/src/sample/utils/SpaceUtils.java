package sample.utils;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import sample.structures.Cell;

import java.util.Random;

/**
 * Created by User on 2019-11-17.
 */
public class SpaceUtils {

    private static Random RNG = new Random();

    public static Paint generateRandomColor() {
        Color color;

        float r = RNG.nextFloat();
        float g = RNG.nextFloat();
        float b = RNG.nextFloat();

        color = new Color(r, g, b, 1f);
        if (color == Cell.DEFAULT_COLOR)
            return generateRandomColor();
        else return color;
    }

    public static int getRandomNumber(int top){
        return RNG.nextInt(top);
    }

    public static Cell[][] copyArray(Cell array[][]){
        Cell copy[][] = new Cell[array.length][array[0].length];
        for (int x = 0; x < array.length; x++)
            for (int y = 0; y < array[x].length; y++)
                copy[x][y] = new Cell(array[x][y]);
        return copy;
    }
}
