package dev.hogoshi.animations.easing;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * Collection of predefined easing functions for animations.
 * Provides various easing functions including linear, quadratic, cubic, elastic, bounce, etc.
 */
public class Easings {
    /**
     * Constants used in back easing calculations.
     */
    public static final double c1 = 1.70158D;
    public static final double c2 = c1 * 1.525D;
    public static final double c3 = c1 + 1.D;
    public static final double c4 = 2.0D * PI / 3.D;
    public static final double c5 = 2.0D * PI / 4.5D;

    /**
     * Back easing that overshoots both at the start and end.
     */
    public static final Easing BACK_BOTH = value -> {
        if (value < 0.5D) {
            return pow(2.0D * value, 2.0D) * ((c2 + 1.0D) * 2.0D * value - c2) / 2.0D;
        } else {
            return (pow(2.0D * value - 2.0D, 2.0D) * ((c2 + 1.0D) * (value * 2.0D - 2.0D) + c2) + 2.0D) / 2.0D;
        }
    };

    /**
     * Back easing that overshoots at the start.
     */
    public static final Easing BACK_IN = value -> c3 * pow(value, 3.0D) - c1 * pow(value, 2.0D);

    /**
     * Back easing that overshoots at the end.
     */
    public static final Easing BACK_OUT = value -> 1.0D + c3 * pow(value - 1.0D, 3.0D) + c1 * pow(value - 1.0D, 2.0D);

    /**
     * Linear easing (no acceleration or deceleration).
     */
    public static final Easing LINEAR = value -> value;

    /**
     * Quadratic easing functions.
     */
    public static final Easing QUAD_IN = powIn(2);
    public static final Easing QUAD_OUT = powOut(2);
    public static final Easing QUAD_BOTH = powBoth(2);

    /**
     * Cubic easing functions.
     */
    public static final Easing CUBIC_IN = powIn(3);
    public static final Easing CUBIC_OUT = powOut(3);
    public static final Easing CUBIC_BOTH = powBoth(3);

    /**
     * Quartic easing functions.
     */
    public static final Easing QUART_IN = powIn(4);
    public static final Easing QUART_OUT = powOut(4);
    public static final Easing QUART_BOTH = powBoth(4);

    /**
     * Quintic easing functions.
     */
    public static final Easing QUINT_IN = powIn(5);
    public static final Easing QUINT_OUT = powOut(5);
    public static final Easing QUINT_BOTH = powBoth(5);

    /**
     * Sine-based easing functions.
     */
    public static final Easing SINE_IN = value -> 1.0D - cos(value * PI / 2.0D);
    public static final Easing SINE_OUT = value -> sin(value * PI / 2.0D);
    public static final Easing SINE_BOTH = value -> {
        if (value < 0.5D) {
            return (1.0D - cos(value * PI)) / 2.0D;
        } else {
            return (1.0D + sin((value - 0.5D) * PI)) / 2.0D;
        }
    };

    /**
     * Circular easing functions.
     */
    public static final Easing CIRC_IN = value -> 1.0D - sqrt(1.0D - pow(value, 2.0D));
    public static final Easing CIRC_OUT = value -> sqrt(1.0D - pow(value - 1.0D, 2));
    public static final Easing CIRC_BOTH = value -> {
        if (value < 0.5D) {
            return (1.0D - sqrt(1.0D - pow(2.0D * value, 2.0D))) / 2.0D;
        } else {
            return (sqrt(1.0D - pow(-2.0D * value + 2.0D, 2.0D)) + 1.0D) / 2.0D;
        }
    };

    /**
     * Elastic easing functions that create a spring-like effect.
     */
    public static final Easing ELASTIC_IN = value -> {
        if (value == 0.0D || value == 1.0D) {
            return value;
        } else {
            return -pow(2.0D, 10.0D * value - 10.0D) * sin((value * 10.0D - 10.75D) * c4);
        }
    };
    public static final Easing ELASTIC_OUT = value -> {
        if (value == 0.0D || value == 1.0D) {
            return value;
        } else {
            return pow(2.0D, -10.0D * value) * sin((value * 10.0D - 0.75D) * c4) + 1.0D;
        }
    };
    public static final Easing ELASTIC_BOTH = value -> {
        if (value == 0.0D || value == 1.0D) {
            return value;
        } else if (value < 0.5D) {
            return -(pow(2.0, 20.0D * value - 10.0D) * sin((20.0D * value - 11.125D) * c5)) / 2.0D;
        } else {
            return pow(2.0, -20.0D * value + 10.0D) * sin((20.0D * value - 11.125D) * c5) / 2.0D + 1.0D;
        }
    };

    /**
     * Exponential easing functions.
     */
    public static final Easing EXPO_IN = value -> {
        if (value != 0.0D) {
            return pow(2.0D, 10.0D * value - 10.0D);
        } else {
            return value;
        }
    };
    public static final Easing EXPO_OUT = value -> {
        if (value != 1.0D) {
            return 1.0D - pow(2.0D, -10.0D * value);
        } else {
            return value;
        }
    };
    public static final Easing EXPO_BOTH = value -> {
        if (value == 0.0D || value == 1.0D) {
            return value;
        } else if (value < 0.5D) {
            return pow(2.0D, 20.0D * value - 10.0D) / 2.0D;
        } else {
            return (2.0D - pow(2.0D, -20.0D * value + 10)) / 2.0D;
        }
    };

    /**
     * Bounce easing functions that create a bouncing effect.
     */
    public static final Easing BOUNCE_OUT = x -> {
        double n1 = 7.5625D;
        double d1 = 2.75D;
        if (x < 1.0D / d1) {
            return n1 * pow(x, 2.0D);
        } else if (x < 2.0D / d1) {
            return n1 * pow(x - 1.5D / d1, 2.0D) + 0.75D;
        } else if (x < 2.5D / d1) {
            return n1 * pow(x - 2.25D / d1, 2.0D) + 0.9375D;
        } else {
            return n1 * pow(x - 2.625D / d1, 2.0D) + 0.984375D;
        }
    };
    public static final Easing BOUNCE_IN = value -> 1.0D - BOUNCE_OUT.ease(1.0D - value);
    public static final Easing BOUNCE_BOTH = value -> {
        if (value < 0.5) {
            return (1 - BOUNCE_OUT.ease(1.0D - 2.0D * value)) / 2.0D;
        } else {
            return (1 + BOUNCE_OUT.ease(2.0D * value - 1.0D)) / 2.0D;
        }
    };

    private Easings() {
    }

    /**
     * Creates a power-based easing function that accelerates at the start.
     *
     * @param n power to use
     * @return easing function
     */
    public static Easing powIn(double n) {
        return value -> pow(value, n);
    }

    /**
     * Creates a power-based easing function that accelerates at the start.
     *
     * @param n power to use
     * @return easing function
     */
    public static Easing powIn(int n) {
        return powIn((double) n);
    }

    /**
     * Creates a power-based easing function that decelerates at the end.
     *
     * @param n power to use
     * @return easing function
     */
    public static Easing powOut(double n) {
        return value -> 1.0D - pow(1.0D - value, n);
    }

    /**
     * Creates a power-based easing function that decelerates at the end.
     *
     * @param n power to use
     * @return easing function
     */
    public static Easing powOut(int n) {
        return powOut((double) n);
    }

    /**
     * Creates a power-based easing function that accelerates at the start and decelerates at the end.
     *
     * @param n power to use
     * @return easing function
     */
    public static Easing powBoth(double n) {
        return value -> {
            if (value < 0.5D) {
                return pow(2.0D * value, n) / 2.0D;
            } else {
                return 1.0D - pow(2.0D * (1.0D - value), n) / 2.0D;
            }
        };
    }
}
