package org.example

/**
 * This file demonstrates the Chain of Responsibility pattern. For
 * more information see the design pattern here:
 * https://refactoring.guru/design-patterns/chain-of-responsibility
 *
 * In this example we are providing functionality similar to something
 * like Python's pathlib.Path.home(), where I can get a home directory
 * independent of OS. In this example, we are using the Filesystem
 * class to detect the OS and handle OS-specific concerns in the
 * Configuration class.
 *
 * Another good use case of this pattern is a scenario where you have
 * several file types which come in as the same kind of data (e.g.,
 * 3D frame data), but can be stored in multiple supported file formats.
 * Chain of Responsibility can be useful in that scenario.
 */

abstract class Filesystem (val delimiter: String) {
    fun detect(): Boolean {
        return listOf<Boolean>(true, false).random()
    }

    abstract fun root(): String

    abstract fun home(): String
}

class LinuxFilesystem : Filesystem("/") {
    override fun root(): String {
        return "/"
    }

    override fun home(): String {
        return "~"
    }
}

class WindowsFilesystem : Filesystem("\\") {
    override fun root(): String {
        return "C:$delimiter"
    }

    override fun home(): String {
        return "C:${delimiter}Users${delimiter}me"
    }
}


class Configuration {
    var supportedFilesystems: List<Filesystem> = emptyList()

    fun addSupportedFilesystem(filesystem: Filesystem) {
        supportedFilesystems += filesystem
    }

    fun configPath(): String {
        val filesystem = supportedFilesystems.find { it.detect() } ?: run {
            throw Exception("You are on an unsupported filesystem!")
        }

        return "${filesystem.home()}${filesystem.delimiter}.config"
    }
}

fun main() {
    val appConfig = Configuration()

    appConfig.addSupportedFilesystem(LinuxFilesystem())
    appConfig.addSupportedFilesystem(WindowsFilesystem())

    println("Saving configuration...")

    try {
        val configPath = appConfig.configPath()
        println("  configuration saved to $configPath")
    } catch (e: Exception) {
        println("!!! ERROR: ${e.message}")
    }
}