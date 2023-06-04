import org.apache.groovy.util.Maps

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

dependencies {
    implementation(project(":api"))
    implementation(libs.paper)
    implementation(libs.commandapi.shade)
}

tasks {
    processResources {
        outputs.upToDateWhen { false }
        filesMatching("**/*.yml") {
            val properties = Maps.of(
                "name", "Axios",
                "version", rootProject.extra.get("fullVersion"),
                "group", project.group
            )
            expand(properties)
        }
    }
    shadowJar {
        dependencies {
            include(dependency("software.axios:.*"))
            include(dependency(libs.commandapi.shade.get()))
        }
        relocate("dev.jorel.commandapi", "${project.group}.libs.commandapi")
        archiveFileName.set("Axios-Paper-${rootProject.extra.get("fullVersion")}.jar")
    }

    artifacts {
        archives(shadowJar)
    }
}