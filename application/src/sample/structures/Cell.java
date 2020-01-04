package sample.structures;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Created by User on 2019-11-16.
 */
public class Cell {

    public static final Paint DEFAULT_COLOR = Paint.valueOf(Color.LIGHTGRAY.toString());
    public static final Paint INCLUSION_COLOR = Paint.valueOf(Color.BLACK.toString());
    public static final Paint DP_STRUCTURE_COLOR = Paint.valueOf(Color.MAGENTA.toString());
    public static final int INCLUSION_PHASE = 1;

    private Paint backgroundColor;
    private int phase;

    public Cell(){
        this.backgroundColor = DEFAULT_COLOR;
        this.phase = 0;
    }

    public Cell(Paint backgroundColor, int phase) {
        this.backgroundColor = backgroundColor;
        this.phase = phase;
    }

    public Cell(Cell cell){
        this.backgroundColor = cell.getBackgroundColor();
        this.phase = cell.getPhase();

    }

    public AnchorPane render() {
        AnchorPane cellRender = new AnchorPane();
        cellRender.setBackground(new Background(new BackgroundFill(this.backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        return cellRender;
    }

    public boolean isEmpty(){
        return backgroundColor == DEFAULT_COLOR;
    }

    public boolean isActivePhaseGrain(){
        return !backgroundColor.toString().equals(DEFAULT_COLOR.toString()) && phase == 0;
    }

    public Paint getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Paint backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }
}
