package org.example

/**
 * This file demonstrates the Adapter pattern. For more information see here:
 * https://refactoring.guru/design-patterns/adapter
 *
 * This example shows applying the Adapter pattern to isolate a legacy
 * class to be able to provide a clean, consistent interface to the
 * rest of the program. While this demos a legacy thing you could easily
 * use Adapter pattern for something like interfacing with a Database
 * or some other highly specific, unchangeable area of the software.
 */

class GrossLegacyEngine {
    fun obscureWhosieWhatsit(): String {
        return "<xml>12</xml>"
    }
}

abstract class Engine {
    abstract fun mpg(): Int
}

class GreenEngine : Engine() {
    override fun mpg(): Int {
        return 40
    }
}

class LegacyEngineAdapter (val legacyEngine: GrossLegacyEngine) : Engine() {
    override fun mpg(): Int {
        return legacyEngine.obscureWhosieWhatsit().removePrefix("<xml>").removeSuffix("</xml>").toInt()
    }
}

class Car (val engine: Engine) {
    fun drive() {
        println(" > driving at ${engine.mpg()}mpg")
    }
}

fun main() {
    val newCar = Car(GreenEngine())
    val oldCar = Car(LegacyEngineAdapter(GrossLegacyEngine()))

    println("Driving cars:")
    newCar.drive()
    oldCar.drive()
}