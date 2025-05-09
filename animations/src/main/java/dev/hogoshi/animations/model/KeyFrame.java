package dev.hogoshi.animations.model;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a keyframe in an animation sequence.
 * A keyframe defines a specific value at a specific time point in the animation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyFrame {
    /**
     * Time point in the animation (0.0 to 1.0).
     */
    private double time;

    /**
     * Value at this keyframe.
     */
    private double value;

    /**
     * Creates a list of evenly spaced keyframes for a linear animation.
     *
     * @param start  starting value
     * @param end    ending value
     * @param steps  number of keyframes to create
     * @return list of keyframes
     * @throws IllegalArgumentException if steps is less than 2
     */
    public static @NotNull List<KeyFrame> linearFrames(double start, double end, int steps) {
        if (steps < 2) {
            throw new IllegalArgumentException("Number of steps must be at least 2");
        }

        List<KeyFrame> frames = new ArrayList<>();
        double step = 1.0 / (steps - 1);
        double valueStep = (end - start) / (steps - 1);

        for (int i = 0; i < steps; i++) {
            frames.add(new KeyFrame(i * step, start + (i * valueStep)));
        }
        return frames;
    }

    /**
     * Creates a simple linear animation with just start and end keyframes.
     *
     * @param start starting value
     * @param end   ending value
     * @return list containing start and end keyframes
     */
    public static @NotNull List<KeyFrame> linear(double start, double end) {
        return List.of(
                new KeyFrame(0.0, start),
                new KeyFrame(1.0, end)
        );
    }
}