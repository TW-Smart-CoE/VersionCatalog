plugins {
    `version-catalog`
    `maven-publish`
}

catalog {
    versionCatalog {
        from(files("toml/libs.versions.toml"))
    }
}

group = "com.thoughtworks.ark"

fun readConfig(name: String): String {
    return project.properties[name] as String? ?: System.getenv(name) ?: ""
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
        create<MavenPublication>("libs") {
            from(components["versionCatalog"])
            groupId = "com.thoughtworks.ark"
            artifactId = "versioncatalog"
            version = "1.0-SNAPSHOT"
        }
    }
}
