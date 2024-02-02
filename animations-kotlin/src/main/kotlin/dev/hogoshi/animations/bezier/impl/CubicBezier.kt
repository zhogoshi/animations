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
    private var firstPoint: Point? = null,
    private var secondPoint: Point? = null
) : AbstractBezier() {

    constructor(
        str: String
    ) : this() {
        val splitted = str.split(",")
        if (splitted.size != 4)
            throw IllegalArgumentException("Couldn't parse $str, please follow this format: x1,y1,x2,y2")

        this.firstPoint = extractPoint(splitted[0] + "," + splitted[1])
        this.secondPoint = extractPoint(splitted[2] + "," + splitted[3])
    }

    private fun extractPoint(str: String): Point? {
        if (!str.contains(",")) return null

        val splitted = str.split(",")
        if (splitted.size <= 1) return null

        val first = splitted[0]
        val second = splitted[1]

        fun String.fixedTrim() = trim().replace("\uFEFF", "")

        try {
            val firstDouble = first.fixedTrim().toDouble()
            val secondDouble = second.fixedTrim().toDouble()
            return Point(firstDouble, secondDouble)
        } catch (_: Exception) {
        }
        return null;
    }

    private fun getPoint(time: Double): Point {
        if (firstPoint == null || secondPoint == null) throw NullPointerException("firstPoint or secondPoint is null")

        val controlPoint1 = firstPoint!!.copy()
        val controlPoint2 = secondPoint!!.copy()

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

    private fun getPoints(): List<Point> {
        val res = arrayListOf<Point>()

        val increment = 1.0 / 30.0
        var t = 0.0
        while (t <= 1.0) {
            val point = getPoint(t)
            res.add(Point(point.x, 1.0).subtract(0.0, point.y))
            t += increment
        }

        res.add(Point(1.0, 0.0))

        return res
    }

    private fun getBoundingPoints(x: Double): Pair<Point, Point> {
        val points = getPoints()
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
        if (firstPoint == null || secondPoint == null) return 0.0

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