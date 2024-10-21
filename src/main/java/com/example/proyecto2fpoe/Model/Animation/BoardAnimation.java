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

public class BoardAnimation  extends AnimationAdapter {
    private GridPane gridPane;
    private static final int GRID_SIZE = 6;
    private Timeline timeline;
    private boolean running;

    public BoardAnimation(GridPane gridPane) {
        this.gridPane = gridPane;
        this.running = false;
    }

    @Override
    public void start() {
        running = true;
        timeline = new Timeline();

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
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

    private void highlightCell(TextField cell, Color color) {
        cell.setBackground(new Background(new BackgroundFill(color, new CornerRadii(4), null)));
    }

    // Helper method to reset cell background
    private void resetCell(TextField cell) {
        cell.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(4), null)));
    }

    private TextField getTextFieldAt(int row, int col) {
        for (var node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (TextField) node;
            }
        }
        return null;
    }
}
