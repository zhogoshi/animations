package dev.hogoshi.animations.core;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import dev.hogoshi.animations.model.AnimationConfig;
import dev.hogoshi.animations.utility.Validator;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Concrete implementation of AbstractAnimation that provides smooth interpolation
 * between keyframes with configurable easing functions.
 */
@Getter
@Accessors(chain = true)
public class Animation extends AbstractAnimation {
    /**
     * Current interpolated value of the animation.
     */
    private double currentValue;

    /**
     * Start value of the animation.
     */
    private double fromValue;

    /**
     * Whether the animation has started playing.
     */
    private boolean hasStarted = false;

    /**
     * Total elapsed time since animation start.
     */
    private double elapsedTime = 0;

    private final double valueTo;

    /**
     * Creates a new animation with the specified configuration and keyframes.
     *
     * @param config    animation configuration
     * @param fromValue the starting value of the animation
     * @param valueTo   the target value of the animation
     * @throws IllegalArgumentException if config is null or valueTo is invalid
     */
    public Animation(@NotNull AnimationConfig config, double fromValue, double valueTo) {
        Validator.requireNonNull(config, "Config cannot be null");
        this.config = config;
        this.fromValue = fromValue;
        this.currentValue = fromValue;
        this.valueTo = valueTo;
        this.delay = config.delay();
    }

    /**
     * Updates the animation state based on the elapsed time.
     * Handles delay, easing, and interpolation between keyframes.
     *
     * @param deltaTime time elapsed since last update in seconds
     * @throws IllegalArgumentException if deltaTime is not positive
     */
    @Override
    public void update(double deltaTime) {
        Validator.requirePositive(deltaTime, "Delta time must be positive");

        if (delay > 0) {
            delay -= deltaTime;
            if (delay <= 0) {
                hasStarted = true;
                isRunning = true;
                currentValue = valueTo;
                if (onUpdate != null) {
                    onUpdate.accept(currentValue);
                }
            }
            return;
        }

        if (!hasStarted) {
            hasStarted = true;
            isRunning = true;
            currentValue = valueTo;
            if (onUpdate != null) {
                onUpdate.accept(currentValue);
            }
        }

        if (!isRunning) {
            return;
        }

        elapsedTime += deltaTime;
        currentTime = Math.min(elapsedTime / config.duration(), 1.0);

        if (currentTime >= 1.0) {
            currentTime = 1.0;
            isRunning = false;
            currentValue = valueTo;
            if (onUpdate != null) {
                onUpdate.accept(currentValue);
            }
            if (onComplete != null) {
                onComplete.run();
            }
            return;
        }

        double easedTime = config.easing() != null ?
                config.easing().ease(currentTime) : currentTime;

        currentValue = getNewValue(easedTime);

        if (onUpdate != null) {
            onUpdate.accept(currentValue);
        }
    }

    /**
     * Calculates the interpolated value between keyframes based on eased time.
     *
     * @param easedTime eased time value (0.0 to 1.0)
     * @return interpolated value
     */
    private double getNewValue(double easedTime) {
        return fromValue + (valueTo - fromValue) * easedTime;
    }

    /**
     * Checks if the animation has finished.
     *
     * @return true if the animation has completed, false otherwise
     */
    @Override
    public boolean isFinished() {
        return !isRunning && currentTime >= 1.0;
    }

    /**
     * Resets the animation to its initial state.
     */
    @Override
    public void reset() {
        currentTime = 0;
        elapsedTime = 0;
        isRunning = false;
        hasStarted = false;
        currentValue = fromValue;
        delay = config.delay();
    }

    /**
     * Sets the callback function to be called on each animation update.
     *
     * @param onUpdate callback function that receives the current animation value
     * @return this animation instance for method chaining
     */
    public @NotNull Animation onUpdate(@Nullable Consumer<Double> onUpdate) {
        this.onUpdate = onUpdate;
        return this;
    }

    /**
     * Sets the callback function to be called when animation completes.
     *
     * @param onComplete callback function to execute on completion
     * @return this animation instance for method chaining
     */
    public @NotNull Animation onComplete(@Nullable Runnable onComplete) {
        this.onComplete = onComplete;
        return this;
    }

    /**
     * Interrupts the animation and triggers the completion callback.
     */
    public void interrupt() {
        if (isRunning) {
            isRunning = false;
            if (onComplete != null) onComplete.run();
        }
    }

    public double getValueTo() {
        return valueTo;
    }
}