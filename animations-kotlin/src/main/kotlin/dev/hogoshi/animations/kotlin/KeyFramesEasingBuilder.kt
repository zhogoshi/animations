package dev.hogoshi.animations.kotlin

import dev.hogoshi.animations.easing.Easing
import dev.hogoshi.animations.easing.KeyFramesEasing
import dev.hogoshi.animations.model.KeyFrame

class KeyFramesEasingBuilder {
    private val keyframes = mutableListOf<KeyFrame>()

    fun keyframe(time: Double, value: Double, easing: Easing? = null) {
        keyframes.add(KeyFrame(time, value, easing))
    }

    fun build(): Easing {
        return KeyFramesEasing(keyframes)
    }
}

fun keyFramedEasing(block: KeyFramesEasingBuilder.() -> Unit): Easing {
    val builder = KeyFramesEasingBuilder()
    builder.block()
    return builder.build()
} 