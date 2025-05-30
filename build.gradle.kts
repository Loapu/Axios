import java.io.ByteArrayOutputStream

defaultTasks("build")

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    group = "software.axios"
    version = "1.1-SNAPSHOT"

    plugins.withType<JavaPlugin> {
        configure<JavaPluginExtension> {
            toolchain.languageVersion.set(JavaLanguageVersion.of(21))
        }
    }


    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    fun determinePatchVersion(): Any {
        val process = ProcessBuilder("git", "describe", "--tags")
            .redirectErrorStream(true)
            .start()
        process.waitFor()
        val tagInfoString = process.inputReader().readText().trim()
        if (!tagInfoString.contains('-')) return 0
        return tagInfoString.split('-')[1]
    }

    rootProject.extra.set("majorVersion", 1)
    rootProject.extra.set("minorVersion", 1)
    rootProject.extra.set("patchVersion", determinePatchVersion())
    rootProject.extra.set("apiVersion", rootProject.extra.get("majorVersion").toString() + "." + rootProject.extra.get("minorVersion"))
    rootProject.extra.set("fullVersion", rootProject.extra.get("apiVersion").toString() + "." + rootProject.extra.get("patchVersion"))

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.codemc.org/repository/maven-public/")
    }
}