package com.example.proyecto2fpoe.Model.Animation;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * A class that extends `AnimationAdapter` to provide animation functionality
 * for highlighting cells in a Sudoku board represented by a `GridPane`.
 */
public class BoardAnimation extends AnimationAdapter {
    private GridPane gridPane; // The grid pane containing the Sudoku cells
    private static final int GRID_SIZE = 6; // Size of the Sudoku grid (6x6)
    private Timeline timeline; // Timeline for the animation
    private boolean running; // Indicates whether the animation is currently running

    /**
     * Constructor that initializes the BoardAnimation with the given GridPane.
     *
     * @param gridPane the GridPane containing the Sudoku cells
     */
    public BoardAnimation(GridPane gridPane) {
        this.gridPane = gridPane;
        this.running = false; // Initially, the animation is not running
    }

    /**
     * Starts the animation, which highlights the cells in a sequence.
     */
    @Override
    public void start() {
        running = true; // Set the running flag to true
        timeline = new Timeline(); // Initialize the timeline

        // Loop through each cell in the grid
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                TextField cell = getTextFieldAt(row, col); // Get the TextField for the cell
                if (cell != null) {
                    // Create key frames for highlighting and resetting the cell
                    KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.2), e -> highlightCell(cell));
                    KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.4), e -> resetCell(cell));
                    timeline.getKeyFrames().addAll(keyFrame1, keyFrame2); // Add key frames to the timeline
                }
            }
        }

        timeline.setCycleCount(4); // Set the number of cycles for the animation
        timeline.play(); // Start the animation
    }

    /**
     * Highlights a specific cell with the given color.
     *
     * @param cell the TextField representing the cell to highlight
     */
    private void highlightCell(TextField cell) {
        cell.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(4), null))); // Change cell background
    }

    /**
     * Resets the background of a cell to its default color (white).
     *
     * @param cell the TextField representing the cell to reset
     */
    private void resetCell(TextField cell) {
        cell.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(4), null))); // Reset background
    }

    /**
     * Retrieves the TextField at the specified row and column in the GridPane.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return the TextField at the specified position, or null if not found
     */
    private TextField getTextFieldAt(int row, int col) {
        for (var node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (TextField) node; // Return the TextField if found
            }
        }
        return null; // Return null if no TextField is found at the specified location
    }
}