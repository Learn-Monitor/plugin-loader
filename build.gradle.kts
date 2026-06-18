plugins {
    java
    id("maven-publish")
}

group = "de.igs-landstuhl"

version = "v1.0.4"

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