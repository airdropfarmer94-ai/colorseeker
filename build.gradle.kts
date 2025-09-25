plugins {
    // aici plugin-urile tale globale (dacă ai)
}

allprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin" && requested.name.startsWith("kotlin-stdlib")) {
                useVersion("1.7.20")
            }
        }
    }
}