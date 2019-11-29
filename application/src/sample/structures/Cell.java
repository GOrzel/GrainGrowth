package sample.structures;

import javafx.geometry.Insets;
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

    private Paint backgroundColor;

    public Cell(){
        this.backgroundColor = DEFAULT_COLOR;
    }

    public Cell(Paint backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Cell(Cell cell){
        this.backgroundColor = cell.getBackgroundColor();
    }

    public AnchorPane render() {
        AnchorPane cellRender = new AnchorPane();
        cellRender.setBackground(new Background(new BackgroundFill(this.backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        return cellRender;
    }

    public boolean isEmpty(){
        return backgroundColor == DEFAULT_COLOR;
    }

    public boolean isGrain(){
        return !backgroundColor.toString().equals(DEFAULT_COLOR.toString());
    }

    public Paint getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Paint backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
