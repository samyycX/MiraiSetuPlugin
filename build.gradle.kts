plugins {
    val kotlinVersion = "1.6.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("net.mamoe.mirai-console") version "2.13.0"
}

group = "github.samyycx"
version = "2.0"

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    maven("https://repo1.maven.org/maven2")
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

dependencies {
    implementation("com.alibaba:fastjson:2.0.10")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("org.projectlombok:lombok:1.18.24")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.yaml:snakeyaml:1.30")
}
