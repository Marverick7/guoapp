pluginManagement {
    val flutterSdkPath =
        run {
            val properties = java.util.Properties()
            file("local.properties").inputStream().use { properties.load(it) }
            val flutterSdkPath = properties.getProperty("flutter.sdk")
            require(flutterSdkPath != null) { "flutter.sdk not set in local.properties" }
            flutterSdkPath
        }

    // [红果漫剧补丁] 非ASCII路径(容器工作区)会让Gradle 9的included build路径编码错乱
    // (mojibake /workspace/çº¢æ)。优先用ASCII符号链接 /tmp/ft，避免 flutter.sdk 直写。
    includeBuild("/tmp/ft/packages/flutter_tools/gradle")
    // [红果漫剧补丁] 抢先注入 loader 用的 SDK 路径（ASCII），压掉后面读到的 mojibake 值
    extensions.extraProperties.set("flutterSdkPath", "/tmp/ft")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("dev.flutter.flutter-plugin-loader") version "1.0.0"
    id("com.android.application") version "9.1.0" apply false
    id("org.jetbrains.kotlin.android") version "2.4.0" apply false
}

include(":app")
