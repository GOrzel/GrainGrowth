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
        br.write("Total boundary length: " + countBoundariesLength(array));
        br.newLine();
        br.write("Total unique grains: " + countUniqueGrains(array));
        br.newLine();
        br.write("Mean grain size: " + array.length*array[0].length/countUniqueGrains(array));
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
                sb.append(cell.isBoundary());
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

        br.readLine();
        br.readLine();
        br.readLine();

        String line = br.readLine();
        while (line != null) {

            String[] attr = line.split(",");
            int posX = Integer.valueOf(attr[0]);
            int posY = Integer.valueOf(attr[1]);
            Paint color = Paint.valueOf(attr[2]);
            int phase = Integer.valueOf(attr[3]);
            boolean isBoundary = Boolean.valueOf(attr[4]);

            cells[posX][posY] = new Cell(color, phase, isBoundary);

            line = br.readLine();
        }

        br.close();
        return cells;
    }

    private static int countBoundariesLength(Cell array[][]) {
        int counter = 0;
        for (int x = 0; x < array.length; x++)
            for (int y = 0; y < array[x].length; y++) {
                if (array[x][y].isBoundary()) {
                    counter++;
                }
            }
        counter /= 2;
        return counter;
    }

    private static int countUniqueGrains(Cell array[][]) {
        int counter = 0;
        ArrayList<Paint> uniques = new ArrayList<>();
        for (int x = 0; x < array.length; x++)
            for (int y = 0; y < array[x].length; y++) {
                if (array[x][y].isGrain() && !array[x][y].isBoundary() && !uniques.contains(array[x][y].getBackgroundColor())) {
                    counter++;
                    uniques.add(array[x][y].getBackgroundColor());
                }
            }
        return counter;
    }

    public static boolean isInCircle(int r, int y, int x) {
        return x * x + y * y <= r * r;
    }
}
