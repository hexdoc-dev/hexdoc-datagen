// plugin config

abstract class PlatformConventionsExtension(private val project: Project) {
    fun developmentConfiguration(name: String) = project.run {
        configurations {
            named(name) {
                extendsFrom(get("common"))
            }
        }
    }

    fun shadowCommonConfiguration(configuration: String) = project.run {
        dependencies {
            "shadowCommon"(project(":common", configuration)) { isTransitive = false }
        }
    }
}

val extension = extensions.create<PlatformConventionsExtension>("platformConventions")

// build logic

plugins {
    id("hexdoc-datagen.common-conventions")
    id("com.github.johnrengelman.shadow")
}

architectury {
    platformSetupLoomIde()
}

configurations {
    register("common")
    register("shadowCommon")
    compileClasspath {
        extendsFrom(get("common"))
    }
    runtimeClasspath {
        extendsFrom(get("common"))
    }
}

dependencies {
    "common"(project(":common", "namedElements")) { isTransitive = false }
}

tasks {
    shadowJar {
        exclude("architectury.common.json")
        configurations = listOf(project.configurations["shadowCommon"])
        archiveClassifier = "dev-shadow"
    }

    remapJar {
        dependsOn(shadowJar)
        inputFile = shadowJar.get().archiveFile
        archiveClassifier = null
    }

    jar {
        archiveClassifier = "dev"
    }

    kotlinSourcesJar {
        val commonSources = project(":common").tasks.kotlinSourcesJar
        dependsOn(commonSources)
        from(commonSources.flatMap { it.archiveFile }.map(::zipTree))
    }
}

components {
    named<AdhocComponentWithVariants>("java") {
        withVariantsFromConfiguration(configurations.shadowRuntimeElements.get()) {
            skip()
        }
    }
}
