plugins {
    `version-catalog`
    `maven-publish`
    signing
}

catalog {
    versionCatalog {
        from(files("toml/libs.versions.toml"))
    }
}

group = "com.thoughtworks.ark"

fun readConfig(name: String, default: String = ""): String {
    return project.properties[name] as String? ?: System.getenv(name) ?: default
}

publishing {
    repositories {
        maven {
            url = uri(readConfig("MAVEN_REPO"))
            isAllowInsecureProtocol = true
            credentials {
                username = readConfig("MAVEN_USER")
                password = readConfig(("MAVEN_PWD"))
            }
        }
    }

    publications {
        create<MavenPublication>("versionCatalog") {
            from(components["versionCatalog"])
            groupId = readConfig("MAVEN_GROUP_ID")
            artifactId = "VersionCatalog"
            version = readConfig("MAVEN_VERSION")

            pom {
                name.set("VersionCatalog")
                description.set("A lib for version management")
                url.set("https://github.com/TW-Smart-CoE/VersionCatalog")

                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        name.set("Zhang Guo")
                        email.set("guo.zhang@thoughtworks.com")
                    }
                }

                scm {
                    connection.set("https://github.com/TW-Smart-CoE/VersionCatalog")
                    developerConnection.set("https://github.com/TW-Smart-CoE/VersionCatalog.git")
                    url.set("https://github.com/TW-Smart-CoE/VersionCatalog")
                }
            }
        }
    }

    signing {
        sign(publishing.publications)
    }
}
