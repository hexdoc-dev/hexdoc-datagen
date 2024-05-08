plugins {
    id("hexdoc-datagen.kotlin-conventions")
    `maven-publish`
    id("dev.architectury.loom")
}

loom {
    silentMojangMappingsLicense()
    accessWidenerPath = projectPath(":common") { file("src/main/resources/hexdoc_datagen.accesswidener") }
}

dependencies {
    minecraft(libs.minecraft)

    mappings(loom.layered {
        officialMojangMappings()
        parchment(libs.parchment)
    })

    annotationProcessor(libs.bundles.asm)

    modApi(libs.hexcasting.common)
}

sourceSets {
    main {
        kotlin.srcDirs += projectPath { dir("src/main/java") }
        resources.srcDirs += projectPath { dir("src/generated/resources") }
    }
}

publishing {
    repositories {
        maven {
            url = uri(System.getenv("local_maven_url") ?: "")
        }
    }
}
