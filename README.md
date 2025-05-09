# Animations

A powerful animation library for Java and Kotlin applications, providing smooth and customizable animations with support for various easing functions and keyframe-based animations.

## Features

- 🎨 **Customizable Easing**: Built-in easing functions, or beziers, or keyframe based easings and support for custom ones
- 🔄 **Multiple Executors**: Choose between Swing, simple, or parallel animation executors or custom ones
- 🎭 **Kotlin DSL**: Fluent, type-safe API for Kotlin users
- ⚡ **High Performance**: Optimized for smooth animations with minimal overhead
- 🛠 **Extensible**: Easy to extend with custom easing functions and executors

## Showcase
https://github.com/user-attachments/assets/7f73e334-089a-44ae-826f-9acac5ed33c6
> You can look for [demo](demo/Test.java) or find all info on [wiki](https://github.com/zhogoshi/animations/wiki) page

## Installation

### Gradle

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.hogoshi.animations:animations:1.1.0")
    // or kotlin one
    implementation("dev.hogoshi.animations:animations-kotlin:1.1.0")
}
```

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>dev.hogoshi.animations</groupId>
        <artifactId>animations</artifactId>
        <version>1.1.0</version>
    </dependency>
    <!-- or kotlin one
    <dependency>
        <groupId>com.github.hogoshi</groupId>
        <artifactId>animations-kotlin</artifactId>
        <version>1.1.0</version>
    </dependency>
    -->
</dependencies>
```

> Note: Replace `1.1.0` with the desired version tag from the [releases page](https://github.com/zhogoshi/animations/releases).

> Another Note: Versions under `1.0.2` are compatible only with 17+ Java, versions higher supports 8+ Java

## Star History
[![Star History Chart](https://api.star-history.com/svg?repos=zhogoshi/animations&type=Date)](https://www.star-history.com/#zhogoshi/animations&Date)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
