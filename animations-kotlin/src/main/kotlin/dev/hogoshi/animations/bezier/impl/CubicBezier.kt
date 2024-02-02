package dev.hogoshi.animations.bezier.impl

import dev.hogoshi.animations.bezier.AbstractBezier
import dev.hogoshi.animations.bezier.Point
import java.util.*
import kotlin.math.pow

/**
 * Just realization for AbstractBezier.
 * Needed for custom easings without using math.
 * You'll just need to go to cubic-bezier.com,
 * Move some points and test how it'll interpolates.
 * If u need this bezier, you can use default constructor CubicBezier(firstPoint, secondPoint).
 * Or you can use CubicBezier(str), parsing str from cubic-bezier.com, just press on save to library at the bottom of bezier.
 */
class CubicBezier : AbstractBezier {

    private val firstPoint: Point?
    private val secondPoint: Point?
    private val points: MutableList<Point> = ArrayList()

    constructor() {
        this.firstPoint = Point(0.0, 0.0)
        this.secondPoint = Point(1.0, 1.0)

        setupPoints()
    }

    constructor(firstPoint: Point?, secondPoint: Point?) {
        this.firstPoint = firstPoint
        this.secondPoint = secondPoint
    }

    constructor(bezier: CubicBezier) {
        this.firstPoint = bezier.getFirstPoint()
        this.secondPoint = bezier.getSecondPoint()
    }

    constructor(str: String) {
        val splitted = str.replace(" ", "").split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        require(splitted.size == 4) { "Couldn't parse $str, please follow this format: x1,y1,x2,y2" }

        val firstPoint = extractPoint(splitted[0] + "," + splitted[1])
        val secondPoint = extractPoint(splitted[2] + "," + splitted[3])

        if (firstPoint != null) this.firstPoint = firstPoint else this.firstPoint = Point(0.0, 0.0)
        if (secondPoint != null) this.secondPoint = secondPoint else this.secondPoint = Point(1.0, 1.0)

        setupPoints()
    }

    private fun setupPoints() {
        if (firstPoint == null || secondPoint == null) return
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

    private fun extractPoint(str: String): Point? {
        if (!str.contains(",")) return null

        val splitted = str.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (splitted.size <= 1) return null

        val first = splitted[0]
        val second = splitted[1]

        try {
            val firstDouble = first.trim { it <= ' ' }.replace("\uFEFF", "").toDouble()
            val secondDouble = second.trim { it <= ' ' }.replace("\uFEFF", "").toDouble()
            return Point(firstDouble, secondDouble)
        } catch (e: Exception) {
            return null
        }
    }

    private fun getPoint(time: Double): Point {
        if (firstPoint == null || secondPoint == null) throw NullPointerException("firstPoint or secondPoint is null")

        val controlPoint1 = firstPoint.copy()
        val controlPoint2 = secondPoint.copy()

        val oneMinusT = 1.0 - time
        return Point(
            start.x * oneMinusT.pow(3.0) + 3 * controlPoint1.x * time * oneMinusT.pow(2.0) + 3 * controlPoint2.x * time.pow(
                2.0
            ) * oneMinusT + end.x * time.pow(3.0),
            start.y * oneMinusT.pow(3.0) + 3 * controlPoint1.y * time * oneMinusT.pow(2.0) + 3 * controlPoint2.y * time.pow(
                2.0
            ) * oneMinusT + end.y * time.pow(3.0)
        )
    }

    private fun getBoundingPoints(x: Double): Map.Entry<Point, Point> {
        if (points.isEmpty()) return AbstractMap.SimpleEntry(Point(0.0, 0.0), Point(0.0, 0.0))

        var lowerPoint = points[0]
        var upperPoint = points[points.size - 1]

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

        return AbstractMap.SimpleEntry(lowerPoint, upperPoint)
    }

    override fun ease(time: Double): Double {
        if (firstPoint == null || secondPoint == null) return 0.0

        val points = getBoundingPoints(time)

        val lowerPoint = points.key
        val upperPoint = points.value

        if (lowerPoint == upperPoint) {
            return 1.0 - lowerPoint.y
        } else {
            val interpolatedY =
                ((upperPoint.y - lowerPoint.y) / (upperPoint.x - lowerPoint.x)) * (time - lowerPoint.x) + lowerPoint.y
            return 1.0 - interpolatedY
        }
    }

    private fun getFirstPoint(): Point {
        return firstPoint!!.copy()
    }

    private fun getSecondPoint(): Point {
        return secondPoint!!.copy()
    }

    fun getPoints(): List<Point> {
        return Collections.unmodifiableList(points)
    }

    fun copy(): CubicBezier {
        return CubicBezier(this)
    }

}