package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import sample.structures.Cell;
import sample.structures.Space;
import sample.utils.BoundaryConditions;
import sample.utils.Neighbourhoods;
import sample.utils.SimulationParameters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
    private AnchorPane spaceDisplay;

    @FXML
    private void generateSpace() {
        int spaceHeight = Integer.parseInt(inputSpaceHeight.getText());
        int spaceWidth = Integer.parseInt(inputSpaceWidth.getText());
        int seedsAmount = Integer.parseInt(inputSeedsAmount.getText());
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
