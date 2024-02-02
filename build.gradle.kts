plugins {
    `maven-publish`
    signing
}

subprojects {
    apply("plugin" to "java")
    apply("plugin" to "maven-publish")

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "dev.hogoshi.animations"
                artifactId = this@subprojects.name
                version = "5.0"

                from(components["java"])
            }
        }
    }
}