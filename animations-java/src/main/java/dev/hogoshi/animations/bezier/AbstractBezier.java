package dev.hogoshi.animations.bezier;

import dev.hogoshi.animations.easing.Easing;

/**
 * Basic bezier abstraction
 */
public abstract class AbstractBezier implements Easing {

    public final Point start = new Point(0.0, 0.0);
    public final Point end = new Point(1.0, 1.0);

}