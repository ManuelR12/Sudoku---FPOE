package com.example.proyecto2fpoe.Model.Animation;

import java.awt.*;

/**
 * An abstract adapter class for the `IAnimation` interface. This class provides
 * default (empty) implementations of the methods in the `IAnimation` interface.
 * Subclasses can override the methods they need to implement custom animation behavior.
 */
public abstract class AnimationAdapter implements IAnimation {

    /**
     * Starts the animation. This method is intended to be overridden by subclasses
     * if custom behavior is needed when the animation starts.
     */
    @Override
    public void start() {
        // Default implementation - no behavior
    }

    /**
     * Stops the animation. This method is intended to be overridden by subclasses
     * if custom behavior is needed when the animation stops.
     */
    @Override
    public void stop() {
        // Default implementation - no behavior
    }

    /**
     * Updates the animation state. This method is intended to be overridden by subclasses
     * to define how the animation's state changes over time.
     */
    @Override
    public void update() {
        // Default implementation - no behavior
    }

    /**
     * Renders the animation's current frame. Subclasses should override this method
     * to draw the animation using the provided `Graphics` context.
     *
     * @param g the `Graphics` context used for drawing the animation.
     */
    @Override
    public void render(Graphics g) {
        // Default implementation - no behavior
    }

    /**
     * Checks if the animation is currently running. By default, this method returns false.
     * Subclasses can override this method to provide the actual running state of the animation.
     *
     * @return `false` by default, indicating that the animation is not running.
     */
    @Override
    public boolean isRunning() {
        // Default implementation returns false
        return false;
    }
}

