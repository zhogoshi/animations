package dev.hogoshi.animations.bezier;

/**
 * Data class which allows to easily manipulate with 2D points
 *
 * @author hogoshi
 */
public class Point {

    private double x;
    private double y;

    public Point() {
        setX(0);
        setY(0);
    }

    public Point(double x, double y) {
        setX(x);
        setY(y);
    }

    public Point(Point point) {
        setX(point.getX());
        setY(point.getY());
    }

    public Point copy() {
        return new Point(this);
    }

    public Point multiply(double x, double y) {
        setX(getX() * x);
        setY(getY() * y);

        return this;
    }

    public Point multiply(double factor) {
        return multiply(factor, factor);
    }

    public Point multiply(Point point) {
        return multiply(point.getX(), point.getY());
    }

    public Point add(double x, double y) {
        setX(getX() + x);
        setY(getY() + y);

        return this;
    }

    public Point add(Point point) {
        return add(point.getX(), point.getY());
    }

    public Point add(double addition) {
        return add(addition, addition);
    }

    public Point subtract(double x, double y) {
        setX(getX() - x);
        setY(getY() - y);

        return this;
    }

    public Point subtract(Point point) {
        return add(point.getX(), point.getY());
    }

    public Point subtract(double subtraction) {
        return add(subtraction, subtraction);
    }

    public Point divide(double x, double y) {
        setX(getX() / x);
        setY(getY() / y);

        return this;
    }

    public Point divide(Point point) {
        return divide(point.getX(), point.getY());
    }

    public Point divide(double division) {
        return divide(division, division);
    }

    public Point set(double x, double y) {
        setX(x);
        setY(y);

        return this;
    }

    public Point set(Point point) {
        return set(point.getX(), point.getY());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}