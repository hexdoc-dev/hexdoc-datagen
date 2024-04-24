plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://maven.architectury.dev/") }
    maven { url = uri("https://maven.fabricmc.net/") }
    maven { url = uri("https://maven.minecraftforge.net/") }
}

dependencies {
    // https://github.com/gradle/gradle/issues/15383#issuecomment-1855984127
    implementation(libs.kotlin.plugin)
    implementation(libs.architectury.plugin)
    implementation(libs.architectury.loom)

    // https://stackoverflow.com/a/70878181
    // https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
