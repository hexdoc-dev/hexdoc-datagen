import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

// --- build logic ---

plugins {
    id("hexdoc-datagen.kotlin-conventions")
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

fun projectPath(projectName: String? = null, action: Directory.() -> FileSystemLocation): File {
    val project = if (projectName != null) project(projectName) else project
    return action.invoke(project.layout.projectDirectory).asFile
}
