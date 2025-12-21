plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("maven-publish")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

repositories {
    maven {
        url = uri("https://maven.pkg.github.com/Naza1re/entity-framework")
        credentials {
            username = findProperty("USERNAME_GITHUB") as String? ?: System.getenv("USERNAME_GITHUB")
            password = findProperty("TOKEN_GITHUB") as String? ?: System.getenv("TOKEN_GITHUB")
        }
    }
    maven {
        url = uri("https://maven.pkg.github.com/Naza1re/outbox-starter")
        credentials {
            username = findProperty("USERNAME_GITHUB") as String? ?: System.getenv("USERNAME_GITHUB")
            password = findProperty("TOKEN_GITHUB") as String? ?: System.getenv("TOKEN_GITHUB")
        }
    }
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.liquibase:liquibase-core")
    runtimeOnly("org.postgresql:postgresql")
    implementation("com.vladmihalcea:hibernate-types-60:2.21.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")

    //entity-framework-starter
    implementation("com.kotlin:entity-framework:0.1.3")

    //outbox-starter
    implementation("com.kotlin:outbox-starter:0.0.7")

    //s3
    implementation("software.amazon.awssdk:s3:2.30.26")
    implementation("software.amazon.awssdk:url-connection-client:2.30.26")

    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
