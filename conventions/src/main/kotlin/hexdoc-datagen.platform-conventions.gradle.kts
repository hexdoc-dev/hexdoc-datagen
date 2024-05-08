plugins {
    id("hexdoc-datagen.common-conventions")
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
    "common"(project(path = ":common", configuration = "namedElements")) {
        isTransitive = false
    }
}
