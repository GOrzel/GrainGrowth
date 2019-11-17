package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.structures.Space;

public class Controller {

    private Space space;

    @FXML
    private TextField inputSpaceHeight;
    private int spaceHeight;

    @FXML
    private TextField inputSpaceWidth;
    private int spaceWidth;

    @FXML
    private TextField inputSeedsAmount;
    private int seedsAmount;

    @FXML
    private AnchorPane spaceDisplay;

    @FXML
    private void generateSpace() {
        spaceHeight = Integer.parseInt(inputSpaceHeight.getText());
        spaceWidth = Integer.parseInt(inputSpaceWidth.getText());
        seedsAmount = Integer.parseInt(inputSeedsAmount.getText());
        space = new Space(spaceHeight,spaceWidth, seedsAmount);
        spaceDisplay.getChildren().clear();
        spaceDisplay.getChildren().add(space.render());
    }
}
