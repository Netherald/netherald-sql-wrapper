import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    kotlin("jvm") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "4.0.4"
    maven
}

group = "com.github.netherald"
version = "1.7-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    mavenLocal()
}

dependencies {
    implementation(kotlin("stdlib"))
    testCompile("junit", "junit", "4.12")
    implementation("mysql:mysql-connector-java:8.0.23")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("shadow")
        mergeServiceFiles()
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
