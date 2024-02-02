package dev.hogoshi.animations.easing

import dev.hogoshi.animations.bezier.impl.CubicBezier

object Beziers {

    val LINEAR = CubicBezier()
    val SINE_IN = CubicBezier("0.12, 0, 0.39, 0")
    val SINE_OUT = CubicBezier("0.61, 1, 0.88, 1")
    val SINE_BOTH = CubicBezier("0.37, 0, 0.63, 1")
    val QUAD_IN = CubicBezier("0.11, 0, 0.5, 0")
    val QUAD_OUT = CubicBezier("0.5, 1, 0.89, 1")
    val QUAD_BOTH = CubicBezier("0.45, 0, 0.55, 1")
    val CUBIC_IN = CubicBezier("0.32, 0, 0.67, 0")
    val CUBIC_OUT = CubicBezier("0.33, 1, 0.68, 1")
    val CUBIC_BOTH = CubicBezier("0.65, 0, 0.35, 1")
    val QUART_IN = CubicBezier("0.5, 0, 0.75, 0")
    val QUART_OUT = CubicBezier("0.25, 1, 0.5, 1")
    val QUART_BOTH = CubicBezier("0.76, 0, 0.24, 1")
    val QUINT_IN = CubicBezier("0.64, 0, 0.78, 0")
    val QUINT_OUT = CubicBezier("0.22, 1, 0.36, 1")
    val QUINT_BOTH = CubicBezier("0.83, 0, 0.17, 1")
    val EXPO_IN = CubicBezier("0.7, 0, 0.84, 0")
    val EXPO_OUT = CubicBezier("0.16, 1, 0.3, 1")
    val EXPO_BOTH = CubicBezier("0.87, 0, 0.13, 1")
    val CIRC_IN = CubicBezier("0.55, 0, 1, 0.45")
    val CIRC_OUT = CubicBezier("0, 0.55, 0.45, 1")
    val CIRC_BOTH = CubicBezier("0.85, 0, 0.15, 1")
    val BACK_IN = CubicBezier("0.36, 0, 0.66, -0.56")
    val BACK_OUT = CubicBezier("0.34, 1.56, 0.64, 1")
    val BACK_BOTH = CubicBezier("0.68, -0.6, 0.32, 1.6")

}