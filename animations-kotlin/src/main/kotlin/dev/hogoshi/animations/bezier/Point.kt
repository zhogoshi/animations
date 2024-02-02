package dev.hogoshi.animations.bezier

/**
 * Data class which allows to easily manipulate with 2D points
 * @author hogoshi
 */
data class Point(
    var x: Double = 0.0,
    var y: Double = 0.0
) {

    constructor(point: Point) : this(point.x, point.y)

    fun copy(): Point {
        return Point(this)
    }

    fun multiply(x: Double, y: Double): Point {
        this.x *= x
        this.y *= y

        return this
    }

    fun multiply(factor: Double): Point {
        return multiply(factor, factor)
    }

    fun multiply(point: Point): Point {
        return multiply(point.x, point.y)
    }

    fun add(x: Double, y: Double): Point {
        this.x += x
        this.y += y

        return this
    }

    fun add(point: Point): Point {
        return add(point.x, point.y)
    }

    fun add(addition: Double): Point {
        return add(addition, addition)
    }

    fun subtract(x: Double, y: Double): Point {
        this.x -= x
        this.y -= y

        return this
    }

    fun subtract(point: Point): Point {
        return add(point.x, point.y)
    }

    fun subtract(subtraction: Double): Point {
        return add(subtraction, subtraction)
    }

    fun divide(x: Double, y: Double): Point {
        this.x /= x
        this.y /= y

        return this
    }

    fun divide(point: Point): Point {
        return divide(point.x, point.y)
    }

    fun divide(division: Double): Point {
        return divide(division, division)
    }

    fun set(x: Double, y: Double): Point {
        this.x = x
        this.y = y

        return this
    }

    fun set(point: Point): Point {
        return set(point.x, point.y)
    }

}