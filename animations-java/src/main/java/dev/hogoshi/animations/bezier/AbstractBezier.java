package dev.hogoshi.animations.bezier;

import dev.hogoshi.animations.easing.Easing;
import lombok.Data;

/**
 * Basic bezier abstraction
 *
 * @author hogoshi
 */
@Data
public abstract class AbstractBezier implements Easing {

    private final Point start = new Point(0, 0);
    private final Point end = new Point(1, 1);

}