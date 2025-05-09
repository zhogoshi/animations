package dev.hogoshi.animations.easing.bezier;

import dev.hogoshi.animations.easing.bezier.implementation.CubicBezier;
import dev.hogoshi.animations.easing.bezier.implementation.LinearBezier;

/**
 * Collection of predefined Bezier curve-based easing functions.
 * Provides various easing functions implemented using Bezier curves.
 */
public class Beziers {
    /**
     * Linear Bezier curve (no easing).
     */
    public static final LinearBezier LINEAR = new LinearBezier();

    /**
     * Cubic Bezier curves for sine-based easing.
     */
    public static final CubicBezier SINE_IN = new CubicBezier(0.12, 0, 0.39, 0);
    public static final CubicBezier SINE_OUT = new CubicBezier(0.61, 1, 0.88, 1);
    public static final CubicBezier SINE_BOTH = new CubicBezier(0.37, 0, 0.63, 1);

    /**
     * Cubic Bezier curves for quadratic easing.
     */
    public static final CubicBezier QUAD_IN = new CubicBezier(0.11, 0, 0.5, 0);
    public static final CubicBezier QUAD_OUT = new CubicBezier(0.5, 1, 0.89, 1);
    public static final CubicBezier QUAD_BOTH = new CubicBezier(0.45, 0, 0.55, 1);

    /**
     * Cubic Bezier curves for cubic easing.
     */
    public static final CubicBezier CUBIC_IN = new CubicBezier(0.32, 0, 0.67, 0);
    public static final CubicBezier CUBIC_OUT = new CubicBezier(0.33, 1, 0.68, 1);
    public static final CubicBezier CUBIC_BOTH = new CubicBezier(0.65, 0, 0.35, 1);

    /**
     * Cubic Bezier curves for quartic easing.
     */
    public static final CubicBezier QUART_IN = new CubicBezier(0.5, 0, 0.75, 0);
    public static final CubicBezier QUART_OUT = new CubicBezier(0.25, 1, 0.5, 1);
    public static final CubicBezier QUART_BOTH = new CubicBezier(0.76, 0, 0.24, 1);

    /**
     * Cubic Bezier curves for quintic easing.
     */
    public static final CubicBezier QUINT_IN = new CubicBezier(0.64, 0, 0.78, 0);
    public static final CubicBezier QUINT_OUT = new CubicBezier(0.22, 1, 0.36, 1);
    public static final CubicBezier QUINT_BOTH = new CubicBezier(0.83, 0, 0.17, 1);

    /**
     * Cubic Bezier curves for exponential easing.
     */
    public static final CubicBezier EXPO_IN = new CubicBezier(0.7, 0, 0.84, 0);
    public static final CubicBezier EXPO_OUT = new CubicBezier(0.16, 1, 0.3, 1);
    public static final CubicBezier EXPO_BOTH = new CubicBezier(0.87, 0, 0.13, 1);

    /**
     * Cubic Bezier curves for circular easing.
     */
    public static final CubicBezier CIRC_IN = new CubicBezier(0.55, 0, 1, 0.45);
    public static final CubicBezier CIRC_OUT = new CubicBezier(0, 0.55, 0.45, 1);
    public static final CubicBezier CIRC_BOTH = new CubicBezier(0.85, 0, 0.15, 1);

    /**
     * Cubic Bezier curves for back easing (overshooting).
     */
    public static final CubicBezier BACK_IN = new CubicBezier(0.36, 0, 0.66, -0.56);
    public static final CubicBezier BACK_OUT = new CubicBezier(0.34, 1.56, 0.64, 1);
    public static final CubicBezier BACK_BOTH = new CubicBezier(0.68, -0.6, 0.32, 1.6);
}