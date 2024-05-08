plugins {
    id("hexdoc-datagen.platform-conventions")
}

commonConventions {
    platform("fabric")
}

kotlinConventions {
    versions = versions.get().mapValues { (_, version) ->
        version
            .replace(",", " ")
            .replace(Regex("""\s+"""), " ")
            .replace(Regex("""\[(\S+)"""), ">=$1")
            .replace(Regex("""(\S+)\]"""), "<=$1")
            .replace(Regex("""\](\S+)"""), ">$1")
            .replace(Regex("""(\S+)\["""), "<$1")
    }
}

architectury {
    fabric()
}

configurations {
    "developmentFabric" {
        extendsFrom(get("common"))
    }
}

repositories {
    maven { url = uri("https://maven.terraformersmc.com/releases/") }
    maven { url = uri("https://maven.ladysnake.org/releases") }
    maven { url = uri("https://maven.jamieswhiteshirt.com/libs-release/") }
    maven { url = uri("https://mvn.devos.one/snapshots/") }
    maven { url = uri("https://jitpack.io") }
    exclusiveContent {
        forRepository {
            maven { url = uri("https://api.modrinth.com/maven") }
        }
        filter {
            includeGroup("maven.modrinth")
        }
    }
}

dependencies {
    modApi(libs.architectury.fabric)

    modApi(libs.fabric.api)
    modImplementation(libs.fabric.loader)

    modImplementation(libs.hexcasting.fabric) {
        exclude(module = "lithium")
        exclude(module = "phosphor")
        exclude(module = "gravity-api")
    }
}

fabricApi {
    configureDataGeneration {
        modId = "hexdoc_datagen"
    }
}

loom {
    runs {
        get("datagen").apply {
            property("hexdoc-datagen.root-dir", rootDir.toString())
            property(
                "hexdoc-datagen.src-dirs",
                listOf(project(":common"), project)
                    .flatMap { it.sourceSets.main.get().kotlin.srcDirs }
                    .distinct()
                    .joinToString(";"),
            )
        }
    }
}
