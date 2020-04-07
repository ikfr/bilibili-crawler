plugins {
    kotlin("jvm") version "1.3.71"
}

group = "org.github.hzqd.ikfr.bilibili.crawler"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    implementation("io.arrow-kt:arrow-core:0.10.5")
    implementation("org.jsoup:jsoup:1.13.1")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}