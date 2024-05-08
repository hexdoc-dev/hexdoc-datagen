plugins {
    id("hexdoc-datagen.common-conventions")
}

commonConventions {
    platform("common")
}

architectury {
    common(listOf("fabric"))
}

dependencies {
    modApi(libs.architectury)
}
