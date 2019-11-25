package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.structures.Space;
import sample.utils.BoundaryConditions;
import sample.utils.Neighbourhoods;
import sample.utils.SimulationParameters;

import java.util.Arrays;
import java.util.stream.Collectors;

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
        space = new Space(spaceHeight, spaceWidth, seedsAmount);
        renderView();
    }

    @FXML
    private ComboBox<String> selectNeighbourhood;
    @FXML
    private ComboBox<String> selectBoundaryCondition;
    private SimulationParameters params = new SimulationParameters();

    @FXML
    public void initialize() {
        selectNeighbourhood.getItems().removeAll(selectNeighbourhood.getItems());
        selectNeighbourhood.getItems().addAll(
                Arrays.stream(Neighbourhoods.Neighbourhood.values())
                        .map(Neighbourhoods.Neighbourhood::name)
                        .collect(Collectors.toList()));

        selectBoundaryCondition.getItems().removeAll(selectBoundaryCondition.getItems());
        selectBoundaryCondition.getItems().addAll(
                Arrays.stream(BoundaryConditions.BoundaryCondition.values())
                        .map(BoundaryConditions.BoundaryCondition::name)
                        .collect(Collectors.toList()));
    }

    @FXML
    private void nextStep() throws Exception {
        Neighbourhoods.Neighbourhood neighbourhood;
        BoundaryConditions.BoundaryCondition boundaryCondition;

        neighbourhood = Neighbourhoods.Neighbourhood.valueOf(selectNeighbourhood.getValue());
        boundaryCondition = BoundaryConditions.BoundaryCondition.valueOf(selectBoundaryCondition.getValue());
        params.setNeighbourhood(neighbourhood);
        params.setBoundaryCondition(boundaryCondition);

        this.space.nextStep(params);
        renderView();
    }

    @FXML
    private void resetSpace(){
        this.space.reset();
        renderView();
    }

    private void renderView(){
        spaceDisplay.getChildren().clear();
        spaceDisplay.getChildren().add(this.space.render());
    }
}
