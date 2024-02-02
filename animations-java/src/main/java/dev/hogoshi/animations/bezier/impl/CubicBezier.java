package dev.hogoshi.animations.bezier.impl;

import dev.hogoshi.animations.bezier.AbstractBezier;
import dev.hogoshi.animations.bezier.Point;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Just realization for AbstractBezier.
 * Needed for custom easings without using math.
 * You'll just need to go to cubic-bezier.com,
 * Move some points and test how it'll interpolates.
 * If u need this bezier, you can use default constructor CubicBezier(firstPoint, secondPoint).
 * Or you can use CubicBezier(str), parsing str from cubic-bezier.com, just press on save to library at the bottom of bezier.
 *
 * @author hogoshi
 */
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class CubicBezier extends AbstractBezier {

    private final Point firstPoint;
    private final Point secondPoint;

    public CubicBezier(String str) {
        String[] splitted = str.split(",");
        if (splitted.length != 4)
            throw new IllegalArgumentException("Couldn't parse " + str + ", please follow this format: x1,y1,x2,y2");

        this.firstPoint = extractPoint(splitted[0] + "," + splitted[1]);
        this.secondPoint = extractPoint(splitted[2] + "," + splitted[3]);
    }

    private Point extractPoint(String str) {
        if (!str.contains(",")) return null;

        String[] splitted = str.split(",");
        if (splitted.length != 2) return null;

        String x = splitted[0];
        String y = splitted[1];

        try {
            return new Point((int) (Double.parseDouble(x.trim().replace("\uFEFF", ""))), (int) (Double.parseDouble(y.trim().replace("\uFEFF", ""))));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Point getPoint(double time) {
        if (firstPoint == null || secondPoint == null)
            throw new NullPointerException("firstPoint or secondPoint is null");

        Point controlPoint1 = firstPoint.copy();
        Point controlPoint2 = secondPoint.copy();

        double oneMinusT = 1.0 - time;
        return new Point(
                getStart().getX() * Math.pow(oneMinusT, 3) +
                        3 * controlPoint1.getX() * time * Math.pow(oneMinusT, 2) +
                        3 * controlPoint2.getX() * Math.pow(time, 2) * oneMinusT +
                        getEnd().getX() * Math.pow(time, 3),
                getStart().getY() * Math.pow(oneMinusT, 3) +
                        3 * controlPoint1.getY() * time * Math.pow(oneMinusT, 2) +
                        3 * controlPoint2.getY() * Math.pow(time, 2) * oneMinusT +
                        getEnd().getY() * Math.pow(time, 3)
        );
    }

    private List<Point> getPoints() {
        List<Point> res = new ArrayList<>();

        double increment = 1.0 / 30.0;
        double t = 0.0;
        while (t <= 1.0) {
            Point point = getPoint(t);
            res.add(new Point(point.getX(), 1.0).subtract(0.0, point.getY()));
            t += increment;
        }

        res.add(new Point(1.0, 0.0));

        return res;
    }

    private Map.Entry<Point, Point> getBoundingPoints(double x) {
        List<Point> points = getPoints();
        if (points.isEmpty()) return Map.entry(new Point(0.0, 0.0), new Point(0.0, 0.0));

        Point lowerPoint = points.stream().findFirst().get();
        Point upperPoint = points.get(points.size() - 1);

        for (Point point : points) {
            if (point.getX() < x)
                lowerPoint = point;
            else if (point.getX() > x && upperPoint.getX() >= point.getX()) {
                upperPoint = point;
                break;
            }
        }

        if (upperPoint.getX() < x) upperPoint = lowerPoint;
        if (lowerPoint.getX() > x) lowerPoint = upperPoint;

        return Map.entry(lowerPoint, upperPoint);
    }

    @Override
    public double ease(double time) {
        if (firstPoint == null || secondPoint == null) return 0.0;

        Map.Entry<Point, Point> points = getBoundingPoints(time);

        Point lowerPoint = points.getKey();
        Point upperPoint = points.getValue();

        if (lowerPoint == upperPoint) {
            return 1.0 - lowerPoint.getY();
        } else {
            double interpolatedY = ((upperPoint.getY() - lowerPoint.getY()) / (upperPoint.getX() - lowerPoint.getX())) * (time - lowerPoint.getX()) + lowerPoint.getY();
            return 1.0 - interpolatedY;
        }
    }

}