plugins {
    id("java")
    id("maven-publish")
    kotlin("jvm") version "1.8.21"
}

repositories {
    mavenCentral()
    maven {
        setUrl("https://jitpack.io")
    }
}

allprojects {
    group = "dev.hogoshi.animations"
    version = "5.0"

    repositories {
        maven {
            setUrl("https://jitpack.io")
        }
    }
}

subprojects {
    when(name) {
        "animations-java" -> apply(plugin = "java")
        "animations-kotlin" -> apply(plugin = "org.jetbrains.kotlin.jvm")
    }
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
    }

    java {
        withSourcesJar()
        withJavadocJar()
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