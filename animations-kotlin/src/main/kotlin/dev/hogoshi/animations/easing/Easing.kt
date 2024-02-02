package dev.hogoshi.animations.easing

/**
 * Functional interface for easy working with interpolation
 * @author hogoshi
 */
fun interface Easing {

    fun ease(time: Double): Double

}