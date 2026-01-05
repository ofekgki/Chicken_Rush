package com.example.chickenrush.Managers

import android.view.View
import kotlin.collections.get
import kotlin.collections.set
import kotlin.random.Random

class GameManager(private val lifeCount: Int = 3) {

    var eggsCollected: Int = 0

    var hits: Int = 0

    var isGameOver: Boolean = false

    var roosterPosition: Int = 2

    var score: Int = 0

    val isPanVisible = IntArray(30)

    val isEggVisible = IntArray(30)

    val isSeedVisible = IntArray(30)

    var seedSeenOnBoard = false

    private var eggOnBoard = false

    var seedFlag: Boolean = false

    private var lastSpawnLane = -1

    private var sameLaneStreak = 0

    private var spaceFlag: Int = 0 //Boolean For Spacing The Lines


    // Managing Pan Visibility Logic
    fun updatePanUI() {
        updatePanVisibleArray()
        if (spaceFlag == 0) {
            var lane = Random.nextInt(5)

            if (lane == lastSpawnLane) {
                sameLaneStreak++
                if (sameLaneStreak >= 2) {
                    lane = (0..2).filter { it != lastSpawnLane }.random()
                    sameLaneStreak = 0
                }
            } else {
                lastSpawnLane = lane
                sameLaneStreak = 0
            }

            isPanVisible[lane] = 1
            spaceFlag = 1
        } else
            spaceFlag = 0
        preventSeedPanEggOverlap()

    }

    private fun updatePanVisibleArray() {
        for (i in 29 downTo 0) {
            if (isPanVisible[i] == 1) {
                isPanVisible[i] = 0
                val next = i + 5
                if (next <= 29) {
                    isPanVisible[next] = 1
                }
            }
        }
    }

    // Managing Egg Visibility Logic
     fun updateEggUI() {
        updateEggVisibleArray()
        if (!eggOnBoard) {
            val chance = Random.nextInt(100)
            if (chance < 15) {
                val lane = Random.nextInt(5)
                isEggVisible[lane] = 1
                eggOnBoard = true
            }
        }
        preventSeedPanEggOverlap()

    }

    private fun updateEggVisibleArray() {
        var anyEgg = false

        for (i in 29 downTo 0) {
            if (isEggVisible[i] == 1) {
                isEggVisible[i] = 0
                val next = i + 5
                if (next <= 29) {
                    isEggVisible[next] = 1
                    anyEgg = true
                }
            }
        }

        eggOnBoard = anyEgg
    }
    // Managing Seed Visibility Logic
    fun updateSeedUI() {
        updateSeedVisibleArray()
        if (seedFlag && !seedSeenOnBoard) {
            val lane = Random.nextInt(5)
            isSeedVisible[lane] = 1
            seedSeenOnBoard = true
        }

        preventSeedPanEggOverlap()

    }
    fun updateSeedVisibleArray() {
        var anySeed = false
        for (i in 29 downTo 0) {
            if (isSeedVisible[i] == 1) {
                isSeedVisible[i] = 0
                val next = i + 5
                if (next <= 29) {
                    isSeedVisible[next] = 1
                    anySeed = true
                }
            }
        }
        seedSeenOnBoard = anySeed
    }

    //Calculating The Final Score
    //Eggs Worth 25 Points
    //Fast Mode Worth Double The Point
    //The Distance Is Worth 7 Times The Distance Passed
    fun calculateFinalScore(gameSpeed: Boolean): Int {
        return if (gameSpeed) {
            ((score * 7) + (eggsCollected * 25)) * 2
        } else {
            (score * 7) + (eggsCollected * 25)
        }

    }

    //Rooster Movement Logic
    fun moveRight() {
        if (roosterPosition < 4) {
            roosterPosition++
        }
    }

    fun moveLeft() {
        if (roosterPosition > 0) {
            roosterPosition--

        }
    }


    private fun preventSeedPanEggOverlap() {
        for (i in 0 until 30) {
            if (isPanVisible[i] == 1) {
                if (isSeedVisible[i] == 1) {
                    isSeedVisible[i] = 0
                    seedSeenOnBoard = false
                }
                if (isEggVisible[i] == 1) {
                    isEggVisible[i] = 0
                    eggOnBoard = false
                }
            }
            else if (isSeedVisible[i] == 1 && isEggVisible[i] == 1) {
                isEggVisible[i] = 0
                eggOnBoard = false
            }
        }
    }

    fun checkForHit(): Int { // 0 for pan hit , 1 for seed hit , 2 for egg hit, -1 for no hit
        val hitIndex = when (roosterPosition) {
            0 -> 25
            1 -> 26
            2 -> 27
            3 -> 28
            else -> 29
        }
        if (isPanVisible[hitIndex] == 1) {
            isPanVisible[hitIndex] = 0
            return 0
        }
        if (isSeedVisible[hitIndex] == 1) {
            isSeedVisible[hitIndex] = 0
            seedSeenOnBoard = false
            return 1
        }
        if (isEggVisible[hitIndex] == 1) {
            isEggVisible[hitIndex] = 0
            return 2
        }
        else
            return -1

    }


}