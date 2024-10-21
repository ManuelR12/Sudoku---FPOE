package com.example.proyecto2fpoe.Model.Animation;

import javafx.animation.FillTransition;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class CorrectNumberAnimation extends AnimationAdapter {
    private TextField textField;
    private boolean running = false;

    public CorrectNumberAnimation(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void start() {
        if (!running) {
            running = true;
            // Create a fill transition animation for the background color
            FillTransition fillTransition = new FillTransition(Duration.millis(300), textField.getShape());
            fillTransition.setFromValue(Color.WHITE);  // Starting color
            fillTransition.setToValue(Color.LIGHTGREEN);  // Green for correct numbers
            fillTransition.play();

            // After animation is done, mark the field as correct
            fillTransition.setOnFinished(e -> {
                textField.setStyle("-fx-background-color: lightgreen;");
                running = false;
            });
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
