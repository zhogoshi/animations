package dev.hogoshi.animations.bezier

import dev.hogoshi.animations.easing.Easing

/**
 * Basic bezier abstraction
 * @author hogoshi
 */
abstract class AbstractBezier : Easing {

    val start = Point(0.0, 0.0)
    val end = Point(1.0, 1.0)

}