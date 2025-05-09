package dev.hogoshi.animations.core;

import java.util.List;
import java.util.function.Consumer;

import dev.hogoshi.animations.model.AnimationConfig;
import dev.hogoshi.animations.model.KeyFrame;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Abstract base class for all animations.
 * Provides common functionality and state management for animations.
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class AbstractAnimation {
    /**
     * Configuration for the animation.
     */
    protected AnimationConfig config;

    /**
     * List of keyframes defining the animation sequence.
     */
    protected List<KeyFrame> keyFrames;

    /**
     * Callback function called on each animation update.
     */
    protected Consumer<Double> onUpdate;

    /**
     * Callback function called when animation completes.
     */
    protected Runnable onComplete;

    /**
     * Current time position in the animation (0.0 to 1.0).
     */
    protected double currentTime = 0;

    /**
     * Whether the animation is currently running.
     */
    protected boolean isRunning = false;

    /**
     * Delay before the animation starts (in seconds).
     */
    protected double delay = 0;

    /**
     * Sets a delay before the animation starts.
     *
     * @param delay delay in seconds
     * @return this animation instance for method chaining
     */
    public AbstractAnimation withDelay(double delay) {
        this.delay = delay;
        return this;
    }

    /**
     * Sets the callback function to be called on each animation update.
     *
     * @param onUpdate callback function that receives the current animation value
     * @return this animation instance for method chaining
     */
    public AbstractAnimation onUpdate(Consumer<Double> onUpdate) {
        this.onUpdate = onUpdate;
        return this;
    }

    /**
     * Sets the callback function to be called when animation completes.
     *
     * @param onComplete callback function to execute on completion
     * @return this animation instance for method chaining
     */
    public AbstractAnimation onComplete(Runnable onComplete) {
        this.onComplete = onComplete;
        return this;
    }

    /**
     * Updates the animation state based on the elapsed time.
     *
     * @param deltaTime time elapsed since last update in seconds
     */
    public abstract void update(double deltaTime);

    /**
     * Checks if the animation has finished.
     *
     * @return true if the animation has completed, false otherwise
     */
    public abstract boolean isFinished();

    /**
     * Resets the animation to its initial state.
     */
    public abstract void reset();
}
