package dev.hogoshi.animations.easing

import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * All pre-default easings, you can create your own or use CubicBezier
 * @author hogoshi
 */
object Easings {

    const val c1 = 1.70158
    const val c2 = c1 * 1.525
    const val c3 = c1 + 1.0
    const val c4 = 2.0 * Math.PI / 3.0
    const val c5 = 2.0 * Math.PI / 4.5

    val LINEAR = Easing { it }

    val QUAD_IN = powIn(2.0)
    val QUAD_OUT = powOut(2.0)
    val QUAD_BOTH = powBoth(2.0)

    val CUBIC_IN = powIn(3.0)
    val CUBIC_OUT = powOut(3.0)
    val CUBIC_BOTH = powBoth(3.0)

    val QUART_IN = powIn(4.0)
    val QUART_OUT = powOut(4.0)
    val QUART_BOTH = powBoth(4.0)

    val QUINT_IN = powIn(5.0)
    val QUINT_OUT = powOut(5.0)
    val QUINT_BOTH = powBoth(5.0)

    val SINE_IN = Easing { 1.0 - cos(it * Math.PI / 2.0) }
    val SINE_OUT = Easing { sin(it * Math.PI / 2.0) }
    val SINE_BOTH = Easing { -(cos(Math.PI * it) - 1.0) / 2.0 }

    val CIRC_IN = Easing { 1.0 - sqrt(1.0 - it.pow(2.0)) }
    val CIRC_OUT = Easing { sqrt(1.0 - (it - 1.0).pow(2.0)) }
    val CIRC_BOTH = Easing {
        if (it < 0.5)
            (1.0 - sqrt(1.0 - (2.0 * it).pow(2.0))) / 2.0
        else (sqrt(1.0 - (-2.0 * it + 2.0).pow(2.0)) + 1.0) / 2.0
    }

    val ELASTIC_IN = Easing {
        if (it == 0.0 || it == 1.0)
            it
        else
            (-2.0).pow(10.0 * it - 10.0) * sin((it * 10.0 - 10.75) * c4)
    }
    val ELASTIC_OUT = Easing {
        if (it == 0.0 || it == 1.0)
            it
        else
            2.0.pow(-10.0 * it) * sin((it * 10.0 - 0.75) * c4) + 1.0

    }
    val ELASTIC_BOTH = Easing {
        if (it == 0.0 || it == 1.0)
            it
        else if (it < 0.5)
            -(2.0.pow(20.0 * it - 10.0) * sin((20.0 * it - 11.125) * c5)) / 2.0
        else
            2.0.pow(-20.0 * it + 10.0) * sin((20.0 * it - 11.125) * c5) / 2.0 + 1.0
    }

    val EXPO_IN = Easing { if (it != 0.0) 2.0.pow(10.0 * it - 10.0) else it }
    val EXPO_OUT = Easing { if (it != 1.0) 1.0 - 2.0.pow(-10.0 * it) else it }
    val EXPO_BOTH = Easing {
        if (it == 0.0 || it == 1.0)
            it
        else if (it < 0.5)
            2.0.pow(20.0 * it - 10.0) / 2.0
        else (2.0 - 2.0.pow(-20.0 * it + 10)) / 2.0
    }

    val BACK_IN = Easing { c3 * it.pow(3.0) - c1 * it.pow(2.0) }
    val BACK_OUT = Easing { 1.0 + c3 * (it - 1.0).pow(3.0) + c1 * (it - 1.0).pow(2.0) }
    val BACK_BOTH = Easing {
        if (it < 0.5)
            (2.0 * it).pow(2.0) * ((c2 + 1.0) * 2.0 * it - c2) / 2.0
        else
            ((2.0 * it - 2.0).pow(2.0) * ((c2 + 1.0) * (it * 2.0 - 2.0) + c2) + 2.0) / 2.0
    }

    val BOUNCE_OUT = Easing {
        val x = it / 2.75
        val y = 7.5625 * x.pow(2.0)

        return@Easing when {
            x < 1.0 -> y
            x < 2.0 -> y + 0.75 - 0.5 * x
            x < 2.5 -> y + 0.9375 - 0.25 * x
            else -> y + 0.984375 - 0.125 * x
        }
    }
    val BOUNCE_IN = Easing { 1.0 - BOUNCE_OUT.ease(1.0 - it) }
    val BOUNCE_BOTH = Easing {
        if (it < 0.5)
            (1 - BOUNCE_OUT.ease(1.0 - 2.0 * it)) / 2.0
        else
            (1 + BOUNCE_OUT.ease(2.0 * it - 1.0)) / 2.0
    }

    private fun powIn(n: Double) = Easing { it.pow(n) }

    private fun powOut(n: Double) = Easing { 1.0 - (1.0 - it).pow(n) }

    private fun powBoth(n: Double) = Easing {
        if (it < 0.5)
            2.0.pow(n - 1) * it.pow(n)
        else
            1.0 - (-2.0 * it + 2.0).pow(n) / 2.0
    }

}