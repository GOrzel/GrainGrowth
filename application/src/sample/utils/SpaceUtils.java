package sample.utils;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import sample.structures.Cell;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    public static int getRandomNumber(int top) {
        return RNG.nextInt(top);
    }

    public static Cell[][] copyArray(Cell array[][]) {
        Cell copy[][] = new Cell[array.length][array[0].length];
        for (int x = 0; x < array.length; x++)
            for (int y = 0; y < array[x].length; y++)
                copy[x][y] = new Cell(array[x][y]);
        return copy;
    }

    public static void saveArrayToCsv(Cell array[][]) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter("dump.csv"));
        StringBuilder sb = new StringBuilder();
        Cell cell;

        sb.append(array.length);
        sb.append(",");
        sb.append(array[0].length);
        br.write(sb.toString());
        br.newLine();

        for (int x = 0; x < array.length; x++)
            for (int y = 0; y < array[x].length; y++) {
                sb = new StringBuilder();
                cell = array[x][y];
                sb.append(x);
                sb.append(",");
                sb.append(y);
                sb.append(",");
                sb.append(cell.getBackgroundColor().toString());
                sb.append(",");
                sb.append(cell.getPhase());
                sb.append(",");
                br.write(sb.toString());
                br.newLine();
            }
        br.close();
    }

    public static Cell[][] loadArrayFromCsv() throws IOException {
        Path pathToFile = Paths.get("dump.csv");
        BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII);

        String[] sizes = br.readLine().split(",");
        int height = Integer.valueOf(sizes[0]);
        int width = Integer.valueOf(sizes[1]);
        Cell cells[][] = new Cell[width][height];

        String line = br.readLine();
        while (line != null) {

            String[] attr = line.split(",");
            int posX = Integer.valueOf(attr[0]);
            int posY = Integer.valueOf(attr[1]);
            Paint color = Paint.valueOf(attr[2]);
            int phase = Integer.valueOf(attr[3]);

            cells[posX][posY] = new Cell(color, phase);

            line = br.readLine();
        }

        br.close();
        return cells;
    }

    public static boolean isInCircle(int r, int y, int x) {
        return x * x + y * y <= r * r;
    }
}
