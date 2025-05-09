package dev.hogoshi.animations.model;

import dev.hogoshi.animations.easing.Easing;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KeyFrame {
    private final double time;
    private final double value;
    private final Easing easing;

    public KeyFrame(double time, double value) {
        this(time, value, null);
    }
} 