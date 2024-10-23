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

/**
 * SubGridAnimation class extends AnimationAdapter to animate a specific 2x3 sub-grid
 * in a Sudoku grid. It highlights each cell in the specified sub-grid to create a visual effect.
 */
public class SubGridAnimation extends AnimationAdapter {

    private final GridPane gridPane;  // The GridPane containing the Sudoku cells
    private static final int SUB_GRID_ROWS = 2;  // Number of rows in the sub-grid
    private static final int SUB_GRID_COLS = 3;  // Number of columns in the sub-grid
    private final int subGridRow;  // Row index of the sub-grid
    private final int subGridCol;  // Column index of the sub-grid
    private Timeline timeline;  // Timeline for the animation
    private boolean running;  // Indicates if the animation is currently running

    /**
     * Constructor for SubGridAnimation.
     *
     * @param gridPane the GridPane containing the Sudoku cells
     * @param subGridRow the row index of the sub-grid to animate
     * @param subGridCol the column index of the sub-grid to animate
     */
    public SubGridAnimation(GridPane gridPane, int subGridRow, int subGridCol) {
        this.gridPane = gridPane;
        this.subGridRow = subGridRow;
        this.subGridCol = subGridCol;
        this.running = false;
    }

    /**
     * Starts the sub-grid animation, highlighting each cell in the specified 2x3 sub-grid.
     */
    @Override
    public void start() {
        running = true;
        timeline = new Timeline();

        // Calculate the starting row and column for the sub-grid
        int startRow = (subGridRow / 2) * 2;
        int startCol = (subGridCol / 3) * 3;

        // Loop through the cells in the 2x3 sub-grid and animate them
        for (int row = startRow; row < startRow + SUB_GRID_ROWS; row++) {
            for (int col = startCol; col < startCol + SUB_GRID_COLS; col++) {
                TextField cell = getTextFieldAt(row, col);
                if (cell != null) {
                    KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.2), e -> highlightCell(cell));
                    KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.4), e -> resetCell(cell));
                    timeline.getKeyFrames().addAll(keyFrame1, keyFrame2);
                }
            }
        }

        timeline.setCycleCount(4);  // Cycle the animation 4 times
        timeline.play();  // Play the animation
    }

    /**
     * Stops the sub-grid animation.
     */
    @Override
    public void stop() {
        running = false;
        if (timeline != null) {
            timeline.stop();  // Stop the timeline if it's running
        }
    }

    /**
     * Checks if the animation is currently running.
     *
     * @return true if the animation is running, false otherwise
     */
    @Override
    public boolean isRunning() {
        return running;
    }

    /**
     * Helper method to retrieve the TextField at a given row and column in the GridPane.
     *
     * @param row the row index
     * @param col the column index
     * @return the TextField at the specified location, or null if not found
     */
    private TextField getTextFieldAt(int row, int col) {
        for (var node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (TextField) node;  // Return the TextField if found
            }
        }
        return null;  // Return null if no TextField is found at the specified location
    }

    /**
     * Helper method to highlight a cell with a specific color.
     *
     * @param cell the TextField to highlight
     */
    private void highlightCell(TextField cell) {
        cell.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(4), null)));
    }

    /**
     * Helper method to reset the cell background to white.
     *
     * @param cell the TextField to reset
     */
    private void resetCell(TextField cell) {
        cell.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(4), null)));
    }
}