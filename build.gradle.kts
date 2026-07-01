plugins {
    java
    id("maven-publish")
    id("com.vanniktech.maven.publish") version "0.37.0"
}

group = "io.github.learn-monitor"

version = "v1.0.5"

repositories {
    mavenCentral()
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/Learn-Monitor/student-database/")
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.13.1") // json library
    implementation("org.yaml:snakeyaml:2.2") // plugin imports

    // Logging
    compileOnly("org.slf4j:slf4j-api:2.0.13")

    // Main project (only used for registry, might be replaced with a separate API module in the future)
    compileOnly("igs-landstuhl:student-database:v2.0.0-SNAPSHOT-2")

    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4") // using JUnit 5 (latest)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("test.environment", "true")
}
tasks.jar {
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17)) // or another version you prefer
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = "de.igs-landstuhl"
            artifactId = "plugin-loader"
            version = project.version.toString()
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Learn-Monitor/plugin-loader/")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "plugin-loader", version.toString())

    pom {
        name = "Plugin Loader"
        description = "Plugin Loader for the Student Database project"
        url = "https://github.com/Learn-Monitor/plugin-loader"

        licenses {
            license {
                name = "GNU General Public License v3.0"
                url = "http://www.gnu.org/licenses/gpl-3.0.txt"
            }
        }
        developers {
            developer {
                id = "schlaumeier5"
                name = "Lukas Morgenstern"
                url = "https://github.com/schlaumeier5"
            }
        }
        scm {
            url = "https://github.com/Learn-Monitor/plugin-loader"
            connection = "scm:git:https://github.com/Learn-Monitor/plugin-loader.git"
            developerConnection = "scm:git:ssh://git@github.com/Learn-Monitor/plugin-loader.git"
        }
    }
}
tasks.withType<AbstractPublishToMaven>().configureEach {
    dependsOn(tasks.withType<Sign>())
}