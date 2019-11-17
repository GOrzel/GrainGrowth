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

    public static final Color DEFAULT_COLOR = Color.LIGHTGRAY;

    private Paint backgroundColor;

    public Cell(){
        this.backgroundColor = DEFAULT_COLOR;
    }

    public Cell(Paint backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public AnchorPane render() {
        AnchorPane cellRender = new AnchorPane();
        cellRender.setBackground(new Background(new BackgroundFill(this.backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        return cellRender;
    }

    public boolean isEmpty(){
        return backgroundColor == DEFAULT_COLOR;
    }

    public Paint getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Paint backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
