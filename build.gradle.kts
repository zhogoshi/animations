plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("maven-publish")
    kotlin("jvm") version "1.8.21"
}

repositories {
    mavenCentral()
}

allprojects {
    group = "dev.hogoshi.animations"
    version = "5.0"
}

subprojects {
    when(name) {
        "animations-java" -> apply(plugin = "java")
        "animations-kotlin" -> apply(plugin = "org.jetbrains.kotlin.jvm")
    }
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
    }

    dependencies {
        if(name == "animations-java") {
            compileOnly("org.projectlombok:lombok:1.18.24")
            annotationProcessor("org.projectlombok:lombok:1.18.24")
        }
    }

    publishing {
        publications {
            register<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()
                from(components.getByName("java"))
            }
        }
    }
}