import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "3.0.0-RC1"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.20"
	kotlin("plugin.spring") version "1.7.20"
	kotlin("plugin.serialization") version "1.7.20"
}

group = "net.iceyleagons"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")

	implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.10")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.0-rc3")
	implementation("com.fasterxml.woodstox:woodstox-core:6.2.5")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0-rc3")
	implementation("com.apptasticsoftware:rssreader:3.2.2")

	implementation("com.google.api-client:google-api-client:2.0.0")
	implementation("com.google.apis:google-api-services-calendar:v3-rev20220715-2.0.0")
	implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
	implementation("com.google.apis:google-api-services-youtube:v3-rev222-1.25.0")
	implementation("com.google.auth:google-auth-library-oauth2-http:1.12.1")
	implementation("com.google.maps:google-maps-services:2.1.2")
	implementation("org.slf4j:slf4j-simple:1.7.25")
	implementation("se.michaelthelin.spotify:spotify-web-api-java:7.2.2")
}
tasks.register("processFrontendResources", Copy::class) {
	group = "Frontend"
	description = "Process frontend resources"
	dependsOn(project(":frontend").tasks.named("assembleFrontend"))

	from("../frontend/dist/")
	into("../backend/src/main/resources/templates/")
}

tasks.named("processResources") {
	// dependsOn(tasks.named("processFrontendResources"))
}

tasks.withType<BootJar> {
	manifest {
		attributes["Start-Class"] = "net.iceyleagons.butler.ButlerApplicationKt"
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjdk-release=17")
		languageVersion = "1.8"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
