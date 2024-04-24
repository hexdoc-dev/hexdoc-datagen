import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

// --- build logic ---

plugins {
    id("hexdoc-datagen.common-conventions")
}

architectury {
    platformSetupLoomIde()
}

configurations {
    val common = create("common")
    create("shadowCommon")
    compileClasspath {
        extendsFrom(common)
    }
    runtimeClasspath {
        extendsFrom(common)
    }
}

dependencies {
    "common"(project(path = ":common", configuration = "namedElements")) {
        isTransitive = false
    }
}
