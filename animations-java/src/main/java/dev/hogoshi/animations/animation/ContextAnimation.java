package dev.hogoshi.animations.animation;

import dev.hogoshi.animations.AnimationContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * Animation which stores as this class/object inside of context
 *
 * @author hogoshi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class ContextAnimation extends AbstractAnimation {

    private final String name;
    private final AnimationContext context;

    /**
     * Use for animating animation inside AnimationContext's animates runnable
     */
    @Override
    public void setValue(double valueTo) {
        if (valueTo == getValue()) return;

        setDebug(getContext().isDebug());

        if (isAlive() && (valueTo == getFrom() || valueTo == getTo() || valueTo == getValue())) {
            if (isDebug()) System.out.println("Animating " + name + " cancelled due to valueTo equals fromValue");
            return;
        }

        setEasing(getContext().getEasing());
        setDuration(getContext().getDuration() * 1000);
        setStart(System.currentTimeMillis());
        setFrom(getValue());
        setTo(valueTo);

        if (isDebug())
            System.out.println(name + "#animate {\n    to value: " + getTo() + "\n    from value: " + getValue() + "\n    duration: " + getDuration() + "\n}");
    }

}