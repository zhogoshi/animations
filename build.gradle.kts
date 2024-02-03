plugins {
    `java-library`
    `maven-publish`
    signing
}

subprojects {
    apply("plugin" to "java")
    apply("plugin" to "maven-publish")

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "dev.hogoshi.animations"
                artifactId = this@subprojects.name
                version = "5.5"

                from(components["java"])
            }
        }
    }
}