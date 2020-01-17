package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.structures.Space;
import sample.utils.BoundaryConditions;
import sample.utils.Neighbourhoods;
import sample.utils.SimulationParameters;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Controller {

    private Space space;
    private boolean doStop = false;


    @FXML
    private TextField inputSpaceHeight;

    @FXML
    private TextField inputSpaceWidth;

    @FXML
    private TextField inputSeedsAmount;

    @FXML
    private TextField inputInclusionsAmount;

    @FXML
    private TextField inputMinRadius;

    @FXML
    private TextField inputMaxRadius;

    @FXML
    private AnchorPane spaceDisplay;

    @FXML
    private CheckBox inputIsGCMode;

    @FXML
    private TextField inputGCChangeChance;

    @FXML
    private void generateSpace() {
        int spaceHeight = Integer.parseInt(inputSpaceHeight.getText());
        int spaceWidth = Integer.parseInt(inputSpaceWidth.getText());
        int seedsAmount = Integer.parseInt(inputSeedsAmount.getText());
        int inclusionsAmount = Integer.parseInt(inputInclusionsAmount.getText());
        int minRadius = Integer.parseInt(inputMinRadius.getText());
        int maxRadius = Integer.parseInt(inputMaxRadius.getText());
        space = new Space(spaceHeight, spaceWidth, seedsAmount, inclusionsAmount, minRadius, maxRadius);
        renderView();
    }

    @FXML
    private void addSeeds(){
        int seedsAmount = Integer.parseInt(inputSeedsAmount.getText());
        space.prepareSeeds(seedsAmount);
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
                        .filter(Neighbourhoods.Neighbourhood::isForNormalMode)
                        .map(Neighbourhoods.Neighbourhood::name)
                        .collect(Collectors.toList()));
        selectNeighbourhood.setValue(Neighbourhoods.Neighbourhood.VON_NEUMANN.name());

        selectBoundaryCondition.getItems().removeAll(selectBoundaryCondition.getItems());
        selectBoundaryCondition.getItems().addAll(
                Arrays.stream(BoundaryConditions.BoundaryCondition.values())
                        .map(BoundaryConditions.BoundaryCondition::name)
                        .collect(Collectors.toList()));
        selectBoundaryCondition.setValue(BoundaryConditions.BoundaryCondition.PERIODIC.name());

        inputIsGCMode.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                selectNeighbourhood.setDisable(newValue);
                renderView();
            }
        });

    }

    @FXML
    private void nextStep() throws Exception {
        Neighbourhoods.Neighbourhood neighbourhood;
        BoundaryConditions.BoundaryCondition boundaryCondition;
        boolean isGCMode;
        int gCChangeChance;

        neighbourhood = Neighbourhoods.Neighbourhood.valueOf(selectNeighbourhood.getValue());
        boundaryCondition = BoundaryConditions.BoundaryCondition.valueOf(selectBoundaryCondition.getValue());
        isGCMode = inputIsGCMode.isSelected();
        gCChangeChance= Integer.valueOf(inputGCChangeChance.getText());
        params.setNeighbourhood(neighbourhood);
        params.setBoundaryCondition(boundaryCondition);
        params.setGCMode(isGCMode);
        params.setGCChangeChance(gCChangeChance);

        this.space.setSimParams(params);
        this.space.nextStep(params);
        renderView();
    }

    @FXML
    private void shiftGrainsPhase(){
        space.shiftGrainsPhase();
    }

    @FXML
    private void startSubstructuresGrow(){
        space.startSubstructuresGrow();
    }

    @FXML
    private void markBoundaries() throws Exception{
        space.markBoundaries();
        renderView();
    }

    @FXML
    private void resetSpace() {
        stop();
        this.space.reset();
        renderView();
    }

    @FXML
    private void play() {
        doStop = false;
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (!doStop) {
                    runNextStep();
                    try {
                        Thread.sleep(250);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };

        new Thread(task).start();
    }

    private void runNextStep() {
        Platform.runLater(() -> {
            try {
                nextStep();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private synchronized void stop() {
        this.doStop = true;
    }

    private void renderView() {
        spaceDisplay.getChildren().clear();
        spaceDisplay.getChildren().add(this.space.render());
    }

    @FXML
    private void refreshView(){
        renderView();
    }


    @FXML
    private void saveToCsv() throws IOException {
        this.space.saveToCsv();
    }

    @FXML
    private void loadFromCsv() throws IOException {
        this.space.loadFromCsv();
        renderView();
    }

    @FXML
    private void saveToBitmap() throws IOException {
        this.space.saveToBitmap();
    }
}
