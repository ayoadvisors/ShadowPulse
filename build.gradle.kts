plugins {
    id("com.android.application") version "8.7.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1" apply false
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}
