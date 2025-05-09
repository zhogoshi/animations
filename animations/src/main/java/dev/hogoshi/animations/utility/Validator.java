package dev.hogoshi.animations.utility;

import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import dev.hogoshi.animations.model.KeyFrame;

/**
 * Utility class for validating various conditions and throwing appropriate exceptions.
 * This class provides static methods for common validation scenarios.
 */
public final class Validator {
    private Validator() {
    }

    /**
     * Validates that an object is not null.
     *
     * @param obj     the object to check
     * @param message the exception message if validation fails
     * @throws IllegalArgumentException if the object is null
     */
    public static void requireNonNull(@Nullable Object obj, @NotNull String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that a collection is not null and not empty.
     *
     * @param collection the collection to check
     * @param message    the exception message if validation fails
     * @throws IllegalArgumentException if the collection is null or empty
     */
    public static void requireNonEmpty(@Nullable Collection<?> collection, @NotNull String message) {
        requireNonNull(collection, message);
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that a numeric value is positive.
     *
     * @param value   the value to check
     * @param message the exception message if validation fails
     * @throws IllegalArgumentException if the value is not positive
     */
    public static void requirePositive(double value, @NotNull String message) {
        if (value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that a numeric value is within a specified range.
     *
     * @param value   the value to check
     * @param min     the minimum allowed value (inclusive)
     * @param max     the maximum allowed value (inclusive)
     * @param message the exception message if validation fails
     * @throws IllegalArgumentException if the value is outside the specified range
     */
    public static void requireInRange(double value, double min, double max, @NotNull String message) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates a list of keyframes for animation.
     * Checks that the list is not empty, contains no null elements,
     * and that keyframes are in ascending time order.
     *
     * @param keyFrames the list of keyframes to validate
     * @throws IllegalArgumentException if any validation fails
     */
    public static void requireValidKeyFrames(@NotNull List<KeyFrame> keyFrames) {
        requireNonEmpty(keyFrames, "KeyFrames list cannot be empty");
        for (int i = 0; i < keyFrames.size(); i++) {
            KeyFrame frame = keyFrames.get(i);
            requireNonNull(frame, "KeyFrame at index " + i + " cannot be null");
            if (i > 0) {
                KeyFrame prevFrame = keyFrames.get(i - 1);
                if (frame.getTime() <= prevFrame.getTime()) {
                    throw new IllegalArgumentException("KeyFrames must be in ascending time order");
                }
            }
        }
    }
}