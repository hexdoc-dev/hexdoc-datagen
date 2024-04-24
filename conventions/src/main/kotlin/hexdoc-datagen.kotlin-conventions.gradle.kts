// plugin config

interface KotlinConventionsPluginExtension {
    val versions: MapProperty<String, String>
}

val extension = extensions.create<KotlinConventionsPluginExtension>("kotlinConventions")

// build logic

plugins {
    java
    kotlin("jvm")
    id("architectury-plugin")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.shedaniel.me/") }
    maven { url = uri("https://maven.parchmentmc.org") }
    maven { url = uri("https://maven.blamejared.com/") }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(kotlin("reflect"))

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

val modVersion: String by project
val mavenGroup: String by project
val javaVersion = libs.versions.java.get().toInt()

group = mavenGroup
version = modVersion

java {
    toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
    withSourcesJar()
    withJavadocJar()
}

kotlin {
    jvmToolchain(javaVersion)
}

tasks {
    withType<JavaCompile>().configureEach {
        options.apply {
            encoding = "UTF-8"
            release = javaVersion
        }
    }

    withType<Jar>().configureEach {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    withType<GenerateModuleMetadata>().configureEach {
        enabled = false
    }

    withType<Javadoc>().configureEach {
        options {
            this as StandardJavadocDocletOptions
            addStringOption("Xdoclint:none", "-quiet")
        }
    }

    processResources.configure {
        exclude(".cache")

        extension.versions.convention(provider {
            val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
            versionCatalog.versionAliases.associate {
                // both "." and "-" cause issues with expand :/
                it.replace(".", "_") to versionCatalog.findVersion(it).get().requiredVersion
            }
        })

        // allow referencing values from libs.versions.toml in Fabric/Forge mod configs
        val dependencyVersions = mapOf(
            "modVersion" to modVersion,
            "versions" to extension.versions.get(),
        )

        // for incremental builds
        inputs.properties(dependencyVersions)

        filesMatching(listOf("fabric.mod.json", "META-INF/mods.toml")) {
            expand(dependencyVersions)
        }
    }

    test.configure {
        useJUnitPlatform()
    }

    processTestResources.configure {
        exclude(".cache")
    }
}
