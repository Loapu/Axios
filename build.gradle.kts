defaultTasks("build")

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    group = "software.axios"
    version = "1.0-SNAPSHOT"

    plugins.withType<JavaPlugin> {
        configure<JavaPluginExtension> {
            toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        }
    }


    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    fun determinePatchVersion(): Any {
        val tagInfo = exec {
            commandLine("git", "describe", "--tags", "--abbrev=0")
        }.toString()
        if (!tagInfo.contains('-')) return 0
        return tagInfo.split('-')[1]
    }

    rootProject.extra.set("majorVersion", 1)
    rootProject.extra.set("minorVersion", 0)
    rootProject.extra.set("patchVersion", determinePatchVersion())
    rootProject.extra.set("apiVersion", rootProject.extra.get("majorVersion").toString() + "." + rootProject.extra.get("minorVersion"))
    rootProject.extra.set("fullVersion", rootProject.extra.get("apiVersion").toString() + "." + rootProject.extra.get("patchVersion"))

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.codemc.org/repository/maven-public/")
    }
}