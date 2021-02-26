plugins {
    java
    kotlin("jvm") version "1.4.10"
}

group = "com.github.netherald"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testCompile("junit", "junit", "4.12")
    implementation("mysql:mysql-connector-java:8.0.23")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
}
