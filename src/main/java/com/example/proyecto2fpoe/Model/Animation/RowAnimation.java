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
 * RowAnimation class extends AnimationAdapter to animate a specific row in a Sudoku grid.
 * It highlights each cell in the specified row, creating a visual indication for users.
 */
public class RowAnimation extends AnimationAdapter {

    private GridPane gridPane;
    private static final int GRID_SIZE = 6;  // Size of the Sudoku grid
    private int rowIndex;  // Index of the row to animate
    private Timeline timeline;  // Timeline for the animation
    private boolean running;  // Indicates if the animation is currently running

    /**
     * Constructor for RowAnimation.
     *
     * @param gridPane the GridPane containing the Sudoku cells
     * @param rowIndex the index of the row to animate
     */
    public RowAnimation(GridPane gridPane, int rowIndex) {
        this.gridPane = gridPane;
        this.rowIndex = rowIndex;
        this.running = false;
    }

    /**
     * Starts the row animation, highlighting each cell in the specified row.
     */
    @Override
    public void start() {
        running = true;
        timeline = new Timeline();

        // Create keyframes to highlight and reset each cell in the specified row
        for (int col = 0; col < GRID_SIZE; col++) {
            TextField cell = getTextFieldAt(rowIndex, col);
            if (cell != null) {
                KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.2), e -> highlightCell(cell));
                KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.4), e -> resetCell(cell));
                timeline.getKeyFrames().addAll(keyFrame1, keyFrame2);
            }
        }

        timeline.setCycleCount(4);  // Cycle the animation 4 times
        timeline.play();  // Play the animation
    }

    /**
     * Stops the row animation.
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
     * Helper method to retrieve the TextField at a given row/column in the GridPane.
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