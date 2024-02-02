package dev.hogoshi.animations.bezier;

/**
 * Data class which allows to easily manipulate with 2D points
 */
public class Point {

    public double x;
    public double y;

    public Point() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public Point copy() {
        return new Point(this);
    }

    public Point multiply(double x, double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Point multiply(double factor) {
        return multiply(factor, factor);
    }

    public Point multiply(Point point) {
        return multiply(point.x, point.y);
    }

    public Point add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Point add(Point point) {
        return add(point.x, point.y);
    }

    public Point add(double addition) {
        return add(addition, addition);
    }

    public Point subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Point subtract(Point point) {
        return add(point.x, point.y);
    }

    public Point subtract(double subtraction) {
        return add(subtraction, subtraction);
    }

    public Point divide(double x, double y) {
        this.x /= x;
        this.y /= y;
        return this;
    }

    public Point divide(Point point) {
        return divide(point.x, point.y);
    }

    public Point divide(double division) {
        return divide(division, division);
    }

    public Point set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Point set(Point point) {
        return set(point.x, point.y);
    }
}
