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


publishing {
    publications {
        create<MavenPublication>("libs") {
            from(components["versionCatalog"])
            artifact(file("${buildDir.path}/version-catalog/libs.versions.toml")) {
                classifier = "versions"
                extension = "toml"
            }
//            groupId = "com.thoughtworks.ark"
//            artifactId = "VersionCatalog"
            version = "1.0-SNAPSHOT"
        }
    }
}