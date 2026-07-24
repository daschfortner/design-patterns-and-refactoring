package org.example

/**
 * This file demonstrates the Factory method. This is a creational
 * pattern outlined here: https://refactoring.guru/design-patterns/factory-method
 *
 * This file demonstrates the pattern by creating a generic Sprite class, which
 * creates attacks. The attacks can be generated independently of the Sprite
 * class, allowing us to execute different attacks from the same Sprite handle.
 *
 * A potential real-world use-case for this would be a DnD type game where
 * Sprites spawn, and then they all generate attacks on their turn, which have
 * different magical effects and damage profiles.
 */

enum class DamageType {
    WIND, FIRE, WATER
}

abstract class Attack (val type: DamageType, val strength: Int) {
    abstract fun damage(): Int
}

class FireAttack (strength: Int) : Attack(DamageType.FIRE, strength) {
    override fun damage(): Int {
        return strength
    }
}

class WaterAttack (strength: Int) : Attack(DamageType.WATER, strength) {
    override fun damage(): Int {
        return strength
    }
}

class WindAttack(strength: Int): Attack(DamageType.WIND, strength) {
    override fun damage(): Int {
        return strength
    }
}

abstract class Sprite (val health: Int, val name: String) {

    fun calculateDamage(): Int {
        return (1..health).random()
    }

    abstract fun createAttack(): Attack
}

class DragonSprite (health: Int) : Sprite(health, "SMAUG") {
    override fun createAttack(): Attack {
        return FireAttack(calculateDamage())
    }
}

class GriffinSprite (health: Int) : Sprite(health, "BOB") {
    override fun createAttack(): Attack {
        return WindAttack(calculateDamage())
    }
}
class WhaleSprite (health: Int): Sprite(health, "MOBY DICK"){
    override fun createAttack(): Attack {
        return WaterAttack(calculateDamage())
    }
}

fun main() {
    val maxHealth: Int = 10

    val sprites: List<Sprite> = listOf(
        DragonSprite((5..maxHealth).random()),
        WhaleSprite((5..maxHealth).random()),
        GriffinSprite((5..maxHealth).random()),
    )

    val spriteToFight = sprites[sprites.indices.random()]

    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    println("  M  O  N  S  T  E  R              B  A  T  T  L  E")
    println()
    println(" you are walking through the woods, when suddenly")
    println(" ${spriteToFight.name} appears!")
    println()

    val attack = spriteToFight.createAttack()

    println(" ${spriteToFight.name} deals ${attack.strength} ${attack.type} damage!")
    println()
    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
}