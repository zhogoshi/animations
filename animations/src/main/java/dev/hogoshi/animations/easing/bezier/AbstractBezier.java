package dev.hogoshi.animations.easing.bezier;

import dev.hogoshi.animations.easing.Easing;
import lombok.Getter;

/**
 * Abstract base class for Bezier curve-based easing functions.
 * Provides common functionality for calculating Bezier curve points and coefficients.
 */
@Getter
public abstract class AbstractBezier implements Easing {
    /**
     * Control points of the Bezier curve.
     */
    protected final double[] points;

    /**
     * Creates a new Bezier curve with the specified control points.
     *
     * @param points control points of the curve
     */
    protected AbstractBezier(double... points) {
        this.points = points;
    }

    /**
     * Calculates a point on the Bezier curve at the given parameter value.
     *
     * @param t      parameter value (0.0 to 1.0)
     * @param points control points of the curve
     * @return point on the curve
     */
    protected double calculateBezierPoint(double t, double[] points) {
        int n = points.length - 1;
        double result = 0;
        for (int i = 0; i <= n; i++) {
            result += binomialCoefficient(n, i) * Math.pow(1 - t, n - i) * Math.pow(t, i) * points[i];
        }
        return result;
    }

    /**
     * Calculates the binomial coefficient (n choose k).
     *
     * @param n total number of items
     * @param k number of items to choose
     * @return binomial coefficient
     */
    private double binomialCoefficient(int n, int k) {
        if (k < 0 || k > n) return 0;
        if (k == 0 || k == n) return 1;
        k = Math.min(k, n - k);
        double c = 1;
        for (int i = 0; i < k; i++) {
            c = c * (n - i) / (i + 1);
        }
        return c;
    }
}
