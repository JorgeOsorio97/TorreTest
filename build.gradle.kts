import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.regex.Pattern.compile

plugins {
    kotlin("jvm") version "1.5.10"
    application
}

group = "me.jorge"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io" )
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.jkcclemens:khttp:0.1.0")
    compile("org.hashids:hashids:1.0.3")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}