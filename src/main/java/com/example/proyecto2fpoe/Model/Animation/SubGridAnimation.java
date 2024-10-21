package com.example.proyecto2fpoe.Model.Animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.util.Duration;

public class SubGridAnimation extends AnimationAdapter {

    private final GridPane gridPane;
    private static final int SUB_GRID_ROWS = 2;
    private static final int SUB_GRID_COLS = 3;
    private final int subGridRow;
    private final int subGridCol;
    private Timeline timeline;
    private boolean running;

    public SubGridAnimation(GridPane gridPane, int subGridRow, int subGridCol) {
        this.gridPane = gridPane;
        this.subGridRow = subGridRow;
        this.subGridCol = subGridCol;
        this.running = false;
    }

    @Override
    public void start() {
        running = true;
        timeline = new Timeline();

        // Calculate the starting row/col for the sub-grid
        int startRow = (subGridRow / 2) * 2;
        int startCol = (subGridCol / 3) * 3;

        // Loop through the cells in the 2x3 sub-grid and animate
        for (int row = startRow; row < startRow + SUB_GRID_ROWS; row++) {
            for (int col = startCol; col < startCol + SUB_GRID_COLS; col++) {
                TextField cell = getTextFieldAt(row, col);
                if (cell != null) {
                    KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.2), e -> highlightCell(cell, Color.LIGHTGREEN));
                    KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.4), e -> resetCell(cell));
                    timeline.getKeyFrames().addAll(keyFrame1, keyFrame2);
                }
            }
        }

        timeline.setCycleCount(4);
        timeline.play();
    }

    @Override
    public void stop() {
        running = false;
        if (timeline != null) {
            timeline.stop();
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    // Helper method to retrieve the TextField at a given row/column in the GridPane
    private TextField getTextFieldAt(int row, int col) {
        for (var node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (TextField) node;
            }
        }
        return null;
    }

    // Helper method to highlight a cell
    private void highlightCell(TextField cell, Color color) {
        cell.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, null)));
    }

    // Helper method to reset cell background
    private void resetCell(TextField cell) {
        cell.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, null)));
    }
}