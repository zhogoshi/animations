package dev.hogoshi.animations.easing.bezier.implementation;

import dev.hogoshi.animations.easing.bezier.AbstractBezier;

/**
 * Implementation of a quadratic Bezier curve easing function.
 * A quadratic Bezier curve is defined by three control points: P0, P1, and P2.
 * P0 and P2 are fixed at (0,0) and (1,1) respectively, while P1 is specified by the constructor parameters.
 */
public class QuadBezier extends AbstractBezier {
    private final double[] xPoints;
    private final double[] yPoints;

    /**
     * Creates a new quadratic Bezier curve easing function.
     *
     * @param p1x x-coordinate of the control point
     * @param p1y y-coordinate of the control point
     */
    public QuadBezier(double p1x, double p1y) {
        super(0.0, p1x, 1.0);
        this.xPoints = new double[]{0.0, p1x, 1.0};
        this.yPoints = new double[]{0.0, p1y, 1.0};
    }

    /**
     * Calculates the y-coordinate of a point on the quadratic Bezier curve at the given x-coordinate.
     * Uses binary search to find the corresponding t value for the given x-coordinate.
     *
     * @param x the x-coordinate (between 0 and 1)
     * @return the y-coordinate of the point on the curve
     */
    @Override
    public double ease(double x) {
        if (x <= 0) return 0;
        if (x >= 1) return 1;

        double t = binarySearch(x);
        return calculateBezierPoint(t, yPoints);
    }

    /**
     * Performs binary search to find the t value that corresponds to the given x-coordinate.
     *
     * @param x the target x-coordinate
     * @return the t value that gives the closest x-coordinate
     */
    private double binarySearch(double x) {
        double start = 0;
        double end = 1;
        double mid;

        for (int i = 0; i < 20; i++) {
            mid = (start + end) / 2;
            double currentX = calculateBezierPoint(mid, xPoints);

            if (Math.abs(currentX - x) < 0.0001) {
                return mid;
            }

            if (currentX < x) {
                start = mid;
            } else {
                end = mid;
            }
        }

        return (start + end) / 2;
    }

} 