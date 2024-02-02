package dev.hogoshi.animations.bezier.impl

import dev.hogoshi.animations.bezier.AbstractBezier
import dev.hogoshi.animations.bezier.Point
import kotlin.math.pow

/**
 * Just realization for AbstractBezier.
 * Needed for custom easings without using math.
 * You'll just need to go to cubic-bezier.com,
 * Move some points and test how it'll interpolates.
 * If u need this bezier, you can use default constructor CubicBezier(firstPoint, secondPoint).
 * Or you can use CubicBezier(str), parsing str from cubic-bezier.com, just press on save to library at the bottom of bezier.
 * @author hogoshi
 */
class CubicBezier(
    private var firstPoint: Point,
    private var secondPoint: Point
) : AbstractBezier() {

    private val points = arrayListOf<Point>()

    constructor() : this(Point(0.0, 0.0), Point(1.0, 1.0))

    constructor(
        str: String
    ) : this() {
        val splitted = str.replace(" ", "").split(",")
        if (splitted.size != 4)
            throw IllegalArgumentException("Couldn't parse $str, please follow this format: x1, y1, x2, y2")

        this.firstPoint = extractPoint(splitted[0] + "," + splitted[1])
        this.secondPoint = extractPoint(splitted[2] + "," + splitted[3])
    }

    constructor(bezier: CubicBezier) : this(bezier.firstPoint, bezier.secondPoint)

    init {
        setupPoints()
    }

    fun copy() : CubicBezier = CubicBezier(this)

    private fun extractPoint(str: String): Point {
        if (!str.contains(",")) throw IllegalArgumentException("Couldn't parse $str, please follow this format: x, y")

        val splitted = str.split(",")
        if (splitted.size <= 1) throw IllegalArgumentException("Couldn't parse $str, please follow this format: x, y")

        val first = splitted[0]
        val second = splitted[1]

        fun String.fixedTrim() = trim().replace("\uFEFF", "")

        try {
            val firstDouble = first.fixedTrim().toDouble()
            val secondDouble = second.fixedTrim().toDouble()
            return Point(firstDouble, secondDouble)
        } catch (e: Exception) {
            throw e
        }
    }

    private fun setupPoints() {
        points.clear()

        val increment = 1.0 / 30.0
        var t = 0.0
        while (t <= 1.0) {
            val point = getPoint(t)
            points.add(Point(point.x, 1.0).subtract(0.0, point.y))
            t += increment
        }

        points.add(Point(1.0, 0.0))
    }

    private fun getPoint(time: Double): Point {
        val controlPoint1 = firstPoint.copy()
        val controlPoint2 = secondPoint.copy()

        val oneMinusT = 1.0 - time
        return Point(
            start.x * oneMinusT.pow(3) +
                    3 * controlPoint1.x * time * oneMinusT.pow(2) +
                    3 * controlPoint2.x * time.pow(2) * oneMinusT +
                    end.x * time.pow(3),
            start.y * oneMinusT.pow(3) +
                    3 * controlPoint1.y * time * oneMinusT.pow(2) +
                    3 * controlPoint2.y * time.pow(2) * oneMinusT +
                    end.y * time.pow(3)
        )
    }

    private fun getBoundingPoints(x: Double): Pair<Point, Point> {
        if (points.isEmpty()) return Point(0.0, 0.0) to Point(0.0, 0.0)

        var lowerPoint = points.first()
        var upperPoint = points.last()

        for (point in points) {
            if (point.x < x) {
                lowerPoint = point
            } else if (point.x > x && upperPoint.x >= point.x) {
                upperPoint = point
                break
            }
        }

        if (upperPoint.x < x) upperPoint = lowerPoint
        if (lowerPoint.x > x) lowerPoint = upperPoint

        return lowerPoint to upperPoint
    }

    override fun ease(time: Double): Double {
        val (lowerPoint, upperPoint) = getBoundingPoints(time)

        if (lowerPoint == upperPoint) {
            return 1.0 - lowerPoint.y
        } else {
            val interpolatedY =
                ((upperPoint.y - lowerPoint.y) / (upperPoint.x - lowerPoint.x)) * (time - lowerPoint.x) + lowerPoint.y
            return 1.0 - interpolatedY
        }
    }

}