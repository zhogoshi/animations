package dev.hogoshi.animations.easing;

/**
 * Functional interface for easing functions.
 * Easing functions transform input values (typically in range [0,1]) to create
 * smooth acceleration and deceleration effects in animations.
 */
@FunctionalInterface
public interface Easing {

    /**
     * Creates a new easing function by chaining multiple easing functions together.
     * The output of each easing function becomes the input for the next one.
     *
     * @param easings array of easing functions to chain
     * @return a new easing function that applies all functions in sequence
     */
    static Easing chain(Easing... easings) {
        return value -> {
            double result = value;
            for (Easing easing : easings) {
                result = easing.ease(result);
            }
            return result;
        };
    }

    /**
     * Applies the easing function to transform an input value.
     *
     * @param value input value (typically in range [0,1])
     * @return transformed value
     */
    double ease(double value);

}
