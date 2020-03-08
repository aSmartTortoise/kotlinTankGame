import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
    dependencies {
        classpath(kotlin("gradle-plugin", "1.3.70"))
    }
}
plugins {
    kotlin("jvm") version "1.3.70"
    application
}

application {
    mainClassName = "MyApplicationKt"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.shaunxiao:kotlinGameEngine:v0.0.4")

}
repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}