import org.apache.groovy.util.Maps
import java.nio.file.Paths

plugins {
    id("com.gradleup.shadow") version "9.+"
    id("io.papermc.paperweight.userdev") version "2.+"
    id("com.modrinth.minotaur") version "2.+"
}

dependencies {
    paperweight.paperDevBundle(libs.paper.get().version)
    implementation(project(":api"))
    implementation(libs.commandapi.shade)
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

modrinth {
    val modrinthToken = project.findProperty("modrinth.token") ?: System.getenv("MODRINTH_TOKEN") ?: ""
    token.set(modrinthToken as String)
    versionType.set("release")
    projectId.set("g4shpRpJ")
    versionNumber.set(rootProject.extra.get("fullVersion") as String)
    uploadFile.set(tasks.shadowJar)
    gameVersions.addAll("1.21.8")
    loaders.add("paper")
    dependencies {
        embedded.version("ExxvCi0y", "epl0dnHR")
    }
    syncBodyFrom.set(rootProject.file(Paths.get("modrinth", "DESCRIPTION.md")).readText())
    changelog.set(rootProject.file(Paths.get("modrinth", "CHANGELOG.md")).readText())
}
tasks.modrinth.get().dependsOn(tasks.modrinthSyncBody)
tasks {
    processResources {
        outputs.upToDateWhen { false }
        filesMatching("**/*.yml") {
            val properties = Maps.of(
                "name", "Axios",
                "version", rootProject.extra.get("fullVersion"),
                "group", "software.axios"
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