package dev.hogoshi.animations.easing.bezier.implementation;

import dev.hogoshi.animations.easing.bezier.AbstractBezier;

/**
 * Implementation of a linear Bezier curve easing function.
 * A linear Bezier curve is a straight line from (0,0) to (1,1).
 * This is equivalent to no easing (linear interpolation).
 */
public class LinearBezier extends AbstractBezier {
    /**
     * Creates a new linear Bezier curve easing function.
     * The curve is defined by two points: (0,0) and (1,1).
     */
    public LinearBezier() {
        super(0.0, 1.0);
    }

    /**
     * Calculates the y-coordinate of a point on the linear Bezier curve.
     * For a linear curve, this is simply the input value (linear interpolation).
     *
     * @param t the parameter value (between 0 and 1)
     * @return the y-coordinate of the point on the curve
     */
    @Override
    public double ease(double t) {
        return t;
    }
} 