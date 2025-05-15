# Animations

A powerful animation library for Java and Kotlin applications, providing smooth and customizable animations with support for various easing functions and keyframe-based animations.

## Showcase
https://github.com/user-attachments/assets/7f73e334-089a-44ae-826f-9acac5ed33c6
> You can check [demo](demo/Test.java) or find all info on [wiki](https://github.com/zhogoshi/animations/wiki) page

## Installation

### Gradle

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.hogoshi.animations:animations:1.1.1")
    // or kotlin one
    implementation("dev.hogoshi.animations:animations-kotlin:1.1.1")
}
```

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>dev.hogoshi.animations</groupId>
        <artifactId>animations</artifactId>
        <version>1.1.1</version>
    </dependency>
    <!-- or kotlin one
    <dependency>
        <groupId>com.github.hogoshi</groupId>
        <artifactId>animations-kotlin</artifactId>
        <version>1.1.1</version>
    </dependency>
    -->
</dependencies>
```

> Note: Replace `1.1.1` with the desired version tag from the [releases page](https://github.com/zhogoshi/animations/releases).

> Another Note: Versions under `1.0.2` are compatible only with 17+ Java, versions higher supports 8+ Java

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
