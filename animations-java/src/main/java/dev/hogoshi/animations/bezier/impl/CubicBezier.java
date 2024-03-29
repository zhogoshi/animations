package dev.hogoshi.animations.bezier.impl;

import dev.hogoshi.animations.bezier.AbstractBezier;
import dev.hogoshi.animations.bezier.Point;

import java.util.*;

/**
 * Just realization for AbstractBezier.
 * Needed for custom easings without using math.
 * You'll just need to go to cubic-bezier.com,
 * Move some points and test how it'll interpolates.
 * If u need this bezier, you can use default constructor CubicBezier(firstPoint, secondPoint).
 * Or you can use CubicBezier(str), parsing str from cubic-bezier.com, just press on save to library at the bottom of bezier.
 */
public class CubicBezier extends AbstractBezier {
    private final Point firstPoint;
    private final Point secondPoint;
    private final List<Point> points = new ArrayList<>();
    private int pointsAmount = 0;

    public CubicBezier() {
        this.firstPoint = new Point(0, 0);
        this.secondPoint = new Point(1, 1);
        setPointsAmount(30);
    }

    public CubicBezier(int pointsAmount) {
        this();
        setPointsAmount(pointsAmount);
    }

    public CubicBezier(Point firstPoint, Point secondPoint) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        setPointsAmount(30);
    }

    public CubicBezier(Point firstPoint, Point secondPoint, int pointsAmount) {
        this(firstPoint, secondPoint);
        setPointsAmount(pointsAmount);
    }

    public CubicBezier(CubicBezier bezier) {
        this.firstPoint = bezier.getFirstPoint();
        this.secondPoint = bezier.getSecondPoint();
        setPointsAmount(30);
    }

    public CubicBezier(CubicBezier bezier, int pointsAmount) {
        this(bezier);
        setPointsAmount(pointsAmount);
    }

    public CubicBezier(String str) {
        String[] splitted = str.replace(" ", "").split(",");
        if (splitted.length != 4)
            throw new IllegalArgumentException("Couldn't parse " + str + ", please follow this format: x1,y1,x2,y2");

        this.firstPoint = new Point(splitted[0] + "," + splitted[1]);
        this.secondPoint = new Point(splitted[2] + "," + splitted[3]);
        setPointsAmount(30);
    }

    public CubicBezier(String str, int pointsAmount) {
        this(str);
        setPointsAmount(pointsAmount);
    }

    private void setupPoints() {
        if (firstPoint == null || secondPoint == null) return;
        points.clear();

        double increment = 1.0 / 30.0;
        double t = 0.0;
        while (t <= 1.0) {
            Point point = getPoint(t);
            points.add(new Point(point.x, 1.0).subtract(0.0, point.y));
            t += increment;
        }

        points.add(new Point(1.0, 0.0));
    }

    private Point getPoint(double time) {
        if (firstPoint == null || secondPoint == null)
            throw new NullPointerException("firstPoint or secondPoint is null");

        Point controlPoint1 = firstPoint.copy();
        Point controlPoint2 = secondPoint.copy();

        double oneMinusT = 1.0 - time;
        return new Point(
                start.x * Math.pow(oneMinusT, 3) +
                        3 * controlPoint1.x * time * Math.pow(oneMinusT, 2) +
                        3 * controlPoint2.x * Math.pow(time, 2) * oneMinusT +
                        end.x * Math.pow(time, 3),
                start.y * Math.pow(oneMinusT, 3) +
                        3 * controlPoint1.y * time * Math.pow(oneMinusT, 2) +
                        3 * controlPoint2.y * Math.pow(time, 2) * oneMinusT +
                        end.y * Math.pow(time, 3)
        );
    }

    private AbstractMap.Entry<Point, Point> getBoundingPoints(double x) {
        if (points.isEmpty()) return new AbstractMap.SimpleEntry<>(new Point(0.0, 0.0), new Point(0.0, 0.0));

        Point lowerPoint = points.get(0);
        Point upperPoint = points.get(points.size() - 1);

        for (Point point : points) {
            if (point.x < x) {
                lowerPoint = point;
            } else if (point.x > x && upperPoint.x >= point.x) {
                upperPoint = point;
                break;
            }
        }

        if (upperPoint.x < x) upperPoint = lowerPoint;
        if (lowerPoint.x > x) lowerPoint = upperPoint;

        return new AbstractMap.SimpleEntry<>(lowerPoint, upperPoint);
    }

    @Override
    public double ease(double time) {
        if (firstPoint == null || secondPoint == null) return 0.0;

        Map.Entry<Point, Point> points = getBoundingPoints(time);

        Point lowerPoint = points.getKey();
        Point upperPoint = points.getValue();

        if (lowerPoint.equals(upperPoint)) {
            return 1.0 - lowerPoint.y;
        } else {
            double interpolatedY =
                    ((upperPoint.y - lowerPoint.y) / (upperPoint.x - lowerPoint.x)) * (time - lowerPoint.x) + lowerPoint.y;
            return 1.0 - interpolatedY;
        }
    }

    public Point getFirstPoint() {
        return firstPoint.copy();
    }

    public Point getSecondPoint() {
        return secondPoint.copy();
    }

    public List<Point> getPoints() {
        return Collections.unmodifiableList(points);
    }

    public int getPointsAmount() {
        return pointsAmount;
    }

    public void setPointsAmount(int pointsAmount) {
        if(this.pointsAmount == pointsAmount) return;

        this.pointsAmount = pointsAmount;
        setupPoints();
    }

    public CubicBezier copy() {
        return new CubicBezier(this);
    }

}