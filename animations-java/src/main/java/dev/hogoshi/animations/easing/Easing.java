package dev.hogoshi.animations.easing;

/**
 * Functional interface for easy working with interpolation
 *
 * @author hogoshi
 */
@FunctionalInterface
public interface Easing {

    double ease(double value);

}