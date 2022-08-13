plugins {
    val kotlinVersion = "1.6.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.12.1"
}

group = "com.samyyc.setu"
version = "1.0.0"

repositories {
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
