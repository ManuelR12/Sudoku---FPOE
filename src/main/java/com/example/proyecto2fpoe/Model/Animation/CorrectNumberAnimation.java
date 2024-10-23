package com.example.proyecto2fpoe.Model.Animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * A class that extends `AnimationAdapter` to provide animation functionality
 * for highlighting a specific TextField representing a correct number in a Sudoku board.
 */
public class CorrectNumberAnimation extends AnimationAdapter {
    private TextField textField; // The TextField to animate
    private boolean running; // Indicates whether the animation is currently running
    private Timeline timeline; // Timeline for the animation

    /**
     * Constructor that initializes the CorrectNumberAnimation with the given TextField.
     *
     * @param textField the TextField representing the correct number
     */
    public CorrectNumberAnimation(TextField textField) {
        this.textField = textField; // Set the TextField
        this.running = false; // Initially, the animation is not running
    }

    /**
     * Starts the animation, which highlights the TextField indicating a correct number.
     */
    @Override
    public void start() {
        running = true; // Set the running flag to true
        timeline = new Timeline(); // Initialize the timeline

        if (textField != null) {
            // Create key frames for highlighting and resetting the TextField
            KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.2), e -> highlightCell(textField));
            KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.4), e -> resetCell(textField));
            timeline.getKeyFrames().addAll(keyFrame1, keyFrame2); // Add key frames to the timeline
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
     * Checks whether the animation is currently running.
     *
     * @return true if the animation is running, false otherwise
     */
    @Override
    public boolean isRunning() {
        return running; // Return the running status
    }
}