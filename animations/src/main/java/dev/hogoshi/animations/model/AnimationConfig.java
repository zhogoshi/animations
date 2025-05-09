package dev.hogoshi.animations.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import dev.hogoshi.animations.easing.Easing;
import dev.hogoshi.animations.utility.Validator;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Configuration class for animations.
 * Defines the duration, easing function, and delay for an animation.
 */
@Data
@Accessors(fluent = true, chain = true)
public class AnimationConfig {
    /**
     * Duration of the animation in seconds.
     */
    private double duration = 1.0;

    /**
     * Easing function to apply to the animation.
     */
    private Easing easing;

    /**
     * Delay before the animation starts in seconds.
     */
    private double delay = 0;

    /**
     * Sets the duration of the animation.
     *
     * @param duration duration in seconds
     * @return this config instance for method chaining
     * @throws IllegalArgumentException if duration is not positive
     */
    public @NotNull AnimationConfig duration(double duration) {
        Validator.requirePositive(duration, "Duration must be positive");
        this.duration = duration;
        return this;
    }

    /**
     * Sets the easing function for the animation.
     *
     * @param easing easing function to apply
     * @return this config instance for method chaining
     */
    public @NotNull AnimationConfig easing(@Nullable Easing easing) {
        this.easing = easing;
        return this;
    }

    /**
     * Sets the delay before the animation starts.
     *
     * @param delay delay in seconds
     * @return this config instance for method chaining
     * @throws IllegalArgumentException if delay is negative
     */
    public @NotNull AnimationConfig delay(double delay) {
        Validator.requireInRange(delay, 0, Double.MAX_VALUE, "Delay must be non-negative");
        this.delay = delay;
        return this;
    }
}