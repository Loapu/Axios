import org.apache.groovy.util.Maps

plugins {
    id("io.github.goooler.shadow") version "8.1.7"
    id("io.papermc.paperweight.userdev") version "1.+"
}

dependencies {
    paperweight.paperDevBundle(libs.paper.get().version)
    implementation(project(":api"))
    implementation(libs.commandapi.shade)
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

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