package com.example.proyecto2fpoe.Model.Animation;

import java.awt.*;

/**
 * Interface representing a general animation with methods for controlling its lifecycle
 * and rendering. Implementing classes must define the behavior for starting, stopping,
 * updating, and rendering animations.
 */
public interface IAnimation {

    /**
     * Starts the animation.
     */
    void start();

    /**
     * Stops the animation.
     */
    void stop();

    /**
     * Updates the animation's state. This method can be used to change the animation
     * parameters based on time or other factors.
     */
    void update();

    /**
     * Renders the animation onto the provided Graphics context.
     *
     * @param g the Graphics context to render onto
     */
    void render(Graphics g);

    /**
     * Checks if the animation is currently running.
     *
     * @return true if the animation is running, false otherwise
     */
    boolean isRunning();
}

