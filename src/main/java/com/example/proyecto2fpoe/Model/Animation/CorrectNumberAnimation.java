package com.example.proyecto2fpoe.Model.Animation;

import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class CorrectNumberAnimation extends AnimationAdapter {
    private TextField textField;
    private boolean running;
    private Timeline timeline;

    public CorrectNumberAnimation (TextField textField) {
        this.textField = textField;
        this.running = false;
    }

    @Override
    public void start() {
        running = true;
        timeline = new Timeline();

            if (textField != null) {
                KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.2), e -> highlightCell(textField, Color.LIGHTGREEN));
                KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.4), e -> resetCell(textField));
                timeline.getKeyFrames().addAll(keyFrame1, keyFrame2);
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
    @Override
    public boolean isRunning() {
        return running;
    }
    }

