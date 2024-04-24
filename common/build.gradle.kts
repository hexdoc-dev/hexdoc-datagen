plugins {
    id("hexdoc-datagen.common-conventions")
}

architectury {
    common(listOf("fabric"))
}

dependencies {
    modApi(libs.architectury)
}
