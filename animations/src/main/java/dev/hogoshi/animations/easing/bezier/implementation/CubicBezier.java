package dev.hogoshi.animations.easing.bezier.implementation;

import dev.hogoshi.animations.easing.bezier.AbstractBezier;

/**
 * Implementation of a cubic Bezier curve easing function.
 * A cubic Bezier curve is defined by four control points: P0, P1, P2, and P3.
 * P0 and P3 are fixed at (0,0) and (1,1) respectively, while P1 and P2 are specified by the constructor parameters.
 */
public class CubicBezier extends AbstractBezier {
    private final double[] xPoints;
    private final double[] yPoints;

    /**
     * Creates a new cubic Bezier curve easing function.
     *
     * @param p1x x-coordinate of the first control point
     * @param p1y y-coordinate of the first control point
     * @param p2x x-coordinate of the second control point
     * @param p2y y-coordinate of the second control point
     */
    public CubicBezier(double p1x, double p1y, double p2x, double p2y) {
        super(0.0, p1x, p2x, 1.0);
        this.xPoints = new double[]{0.0, p1x, p2x, 1.0};
        this.yPoints = new double[]{0.0, p1y, p2y, 1.0};
    }

    /**
     * Calculates the y-coordinate of a point on the cubic Bezier curve at the given x-coordinate.
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
