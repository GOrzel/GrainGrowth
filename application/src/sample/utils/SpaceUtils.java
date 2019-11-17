package sample.utils;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import sample.structures.Cell;

import java.util.Random;

/**
 * Created by User on 2019-11-17.
 */
public class SpaceUtils {

    public static Paint generateRandomColor() {
        Random RNG = new Random();
        Color color;

        float r = RNG.nextFloat();
        float g = RNG.nextFloat();
        float b = RNG.nextFloat();

        color = new Color(r, g, b, 1f);
        if (color == Cell.DEFAULT_COLOR)
            return generateRandomColor();
        else return color;
    }
}
