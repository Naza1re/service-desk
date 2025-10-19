plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.kotlin"
version = "0.0.1-SNAPSHOT"
description = "service-desk"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter") // уже есть
    implementation("org.springframework.boot:spring-boot-starter-aop") // уже есть
    implementation("org.springframework.boot:spring-boot-starter-web") // уже есть
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // <- вот это нужно добавить
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.liquibase:liquibase-core")
    runtimeOnly("org.postgresql:postgresql") // драйвер базы
    implementation(files("C:\\Users\\mcari\\IdeaProjects\\aop\\build\\libs\\entity-framework-0.2.0.jar"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.vladmihalcea:hibernate-types-60:2.21.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
