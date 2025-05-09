# Animations

A powerful and flexible animation library for Java and Kotlin applications, providing smooth, configurable animations with support for keyframes and custom easing functions.

## Features

- 🎯 **Keyframe-based Animations**: Define precise animation states at specific time points
- 🎨 **Customizable Easing**: Built-in easing functions and support for custom ones
- 🔄 **Multiple Executors**: Choose between Swing, simple, or parallel animation executors or custom ones
- 🎭 **Kotlin DSL**: Fluent, type-safe API for Kotlin users
- ⚡ **High Performance**: Optimized for smooth animations with minimal overhead
- 🛠 **Extensible**: Easy to extend with custom easing functions and executors

## Installation Java Version

### Gradle (Kotlin DSL)

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.hogoshi.animations:animations:1.0.2")
    // or kotlin one
    implementation("dev.hogoshi.animations:animations-kotlin:1.0.2")
}
```

### Gradle (Groovy)

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'dev.hogoshi.animations:animations:1.0.2'
    // or kotlin one
    implementation 'dev.hogoshi.animations:animations-kotlin:1.0.2'
}
```

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>dev.hogoshi.animations</groupId>
        <artifactId>animations</artifactId>
        <version>1.0.2</version>
    </dependency>
    <!-- or kotlin one
    <dependency>
        <groupId>com.github.hogoshi</groupId>
        <artifactId>animations-kotlin</artifactId>
        <version>1.0.2</version>
    </dependency>
    -->
</dependencies>
```

> Note: Replace `1.0.2` with the desired version tag from the [releases page](https://github.com/zhogoshi/animations/releases).

> Another Note: Versions under `1.0.2` are compatible only with 17+ Java, versions higher supports 8+ Java

## Usage

### Java

```java
// Create a simple animation
AnimationConfig config = new AnimationConfig()
    .duration(1.0)
    .easing(Easings.EASE_IN_OUT);

List<KeyFrame> keyFrames = List.of(
    new KeyFrame(0.0, 0.0),
    new KeyFrame(1.0, 100.0)
);

Animation animation = new Animation(config, keyFrames);

// Add callbacks
animation.onUpdate(value -> {
    // Update your UI or state here
    System.out.println("Current value: " + value);
});

animation.onComplete(() -> {
    System.out.println("Animation completed!");
});

// Execute the animation
SimpleAnimationExecutor executor = new SimpleAnimationExecutor();
executor.execute(animation);
```

### Kotlin DSL

```kotlin
// Create an animation using the DSL
val animation = animation {
    config {
        duration(1.0)
        delay(0.5)
        easing(Easings.EASE_IN_OUT)
    }
    
    keyframes {
        keyframe(0.0, 0.0)
        keyframe(0.5, 50.0)
        keyframe(1.0, 100.0)
    }
    
    onUpdate { value ->
        // Update your UI or state here
        println("Current value: $value")
    }
    
    onComplete {
        println("Animation completed!")
    }
}

// Execute the animation
animation(Executors.SIMPLE) {
    config {
        duration(1.0)
    }
    keyframes {
        keyframe(0.0, 0.0)
        keyframe(1.0, 100.0)
    }
}
```

### Custom Easing Functions

```kotlin
// Create a custom easing function
val customEasing = Easing { value ->
    // Your custom easing logic here
    value * value * (3 - 2 * value)
}

// Use it in an animation
val animation = animation {
    config {
        easing(customEasing)
    }
    // ... rest of the configuration
}
```

### Multiple Executors

```kotlin
// Swing executor (for UI animations)
val swingExecutor = Executors.SWING

// Simple executor (single-threaded)
val simpleExecutor = Executors.SIMPLE

// Parallel executor (multi-threaded)
val parallelExecutor = Executors.PARALLEL

// Custom executor
val customExecutor = executor {
    parallel(true)
    fps(60.0)
    onUpdate { animations, deltaTime ->
        // Custom update logic
    }
}
```

## Use Cases

- **UI Animations**: Smooth transitions and effects in desktop applications
- **Game Development**: Character movements, camera transitions, and visual effects
- **Data Visualization**: Animated charts and graphs
- **Interactive Applications**: User feedback and state transitions
- **Simulations**: Physics-based animations and simulations

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.