import net.ltgt.gradle.errorprone.errorprone
import net.ltgt.gradle.nullaway.nullaway

plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.7.1"
    id("net.ltgt.errorprone") version "4.2.0"
    id("net.ltgt.nullaway") version "2.2.0"
}

group = "io.github.marattim"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    errorprone("com.google.errorprone:error_prone_core:2.48.0")
    errorprone("com.uber.nullaway:nullaway:0.12.9")
    compileOnly("com.uber.nullaway:nullaway-annotations:0.13.1")
    implementation("org.jspecify:jspecify:1.0.0")
    intellijPlatform {
        create("IC", "2025.1.4.1")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "252"
        }
    }
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"

        options.errorprone {
            nullaway {
                onlyNullMarked = true
                isJSpecifyMode = true
            }
        }
        options.compilerArgs.add("-XDaddTypeAnnotationsToSymbol=true")
        outputs.cacheIf { false }
    }
}
