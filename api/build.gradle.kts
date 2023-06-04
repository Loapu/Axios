project.version = "1.0"

dependencies {
    compileOnly(libs.checker.qual)
    api(libs.paper)
}

tasks.withType<Javadoc> {
    options.overview = "javadoc/overview.html"
    options.encoding = "UTF-8"
    title = "Axios API (v${rootProject.ext.get("apiVersion")})"
}

java {
    withJavadocJar()
    withSourcesJar()
}
if (project.hasProperty("publish")) {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "software.axios"
                artifactId = "api"
                version = rootProject.ext.get("apiVersion").toString()
                from(components["java"])
                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }
                pom {
                    name.set("Axios")
                    description.set("A simple and lightweight Minecraft server plugin API")
                    url.set("https://axios.software")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    developers {
                        developer {
                            id.set("loapu")
                            name.set("Loapu")
                            url.set("https://loapu.dev")
                            email.set("benjamin@loapu.dev")
                        }
                    }
                    scm {
                        connection.set("scm:git:https://github.com/Loapu/Axios.git")
                        developerConnection.set("scm:git:git@github.com:Loapu/Axios.git")
                        url.set("https://github.com/Loapu/Axios")
                    }
                    issueManagement {
                        system.set("GitHub Issues")
                        url.set("https://github.com/Loapu/Axios/issues")
                    }
                }
            }
        }
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/Loapu/Axios")
                credentials {
                    val usr = project.findProperty("gpr.user") ?: System.getenv("GITHUB_ACTOR") ?: ""
                    val key = project.findProperty("gpr.key") ?: System.getenv("GITHUB_TOKEN") ?: ""
                    username = usr as String
                    password = key as String
                }
            }
        }
    }
}
