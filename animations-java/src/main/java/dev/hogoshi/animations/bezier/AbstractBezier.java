package dev.hogoshi.animations.bezier;

import dev.hogoshi.animations.easing.Easing;

/**
 * Basic bezier abstraction
 *
 * @author hogoshi
 */
public abstract class AbstractBezier implements Easing {

    private final Point start = new Point(0, 0);
    private final Point end = new Point(1, 1);

    public Point getEnd() {
        return end;
    }

    public Point getStart() {
        return start;
    }

}