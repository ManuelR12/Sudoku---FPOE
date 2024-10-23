package com.example.proyecto2fpoe.Model.Animation;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.text.Text;
import javafx.util.Duration;
/**
 * This class provides an animation for a `Text` object in JavaFX.
 * The animation consists of two phases:
 * 1. A fade-in with a horizontal translation (moving right).
 * 2. A brief pause followed by a fade-out with another horizontal translation.
 */
public class AdviceAnimation extends AnimationAdapter {

    /**
     * Starts the animation on the given `Text` object. The animation includes:
     * - Fade-in effect.
     * - Translation (horizontal movement) of the text.
     * - Pause.
     * - Fade-out effect with a second translation.
     *
     * @param status the `Text` object to be animated.
     */
    public void start(Text status){

        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(1), status);
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(1), status);

        FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(1), status);
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), status);

        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));

        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(1);
        transition1.setByX(100);
        transition1.setCycleCount(1);
        transition1.setAutoReverse(false);

        fadeTransition2.setFromValue(1);
        fadeTransition2.setToValue(0);
        transition2.setByX(135);
        transition2.setCycleCount(1);
        transition2.setAutoReverse(false);

        ParallelTransition parallel1 = new ParallelTransition(transition1, fadeTransition1);
        ParallelTransition parallel2 = new ParallelTransition(transition2, fadeTransition2);
        parallel1.setOnFinished(event -> pause.play());
        pause.setOnFinished(event -> parallel2.play());
        parallel1.play();
    }

}