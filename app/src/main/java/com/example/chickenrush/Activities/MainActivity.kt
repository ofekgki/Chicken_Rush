package com.example.chickenrush.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.chickenrush.R
import com.example.chickenrush.utilities.BackgroundMusicPlayer
import com.example.chickenrush.utilities.Constants
import com.example.chickenrush.utilities.SignalManager
import com.example.chickenrush.utilities.SingleSoundPlayer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    //Hearts
    private lateinit var Main_IMG_hearts: Array<AppCompatImageView>

    //Roosters

    private lateinit var Main_IMG_Rooster_1: AppCompatImageView

    private lateinit var Main_IMG_Rooster_2: AppCompatImageView

    private lateinit var Main_IMG_Rooster_3: AppCompatImageView

    private lateinit var Main_IMG_Rooster_4: AppCompatImageView

    private lateinit var Main_IMG_Rooster_5: AppCompatImageView

    private lateinit var Main_IMG_pans: Array<AppCompatImageView>

    private val isVisible = IntArray(30)

    //Fabs

    private lateinit var Main_FAB_Left: FloatingActionButton

    private lateinit var Main_FAB_Right: FloatingActionButton


    //Score
    private lateinit var Main_LBL_Score: MaterialTextView

    private var score = 0

    private var scoreFlag: Boolean = false


    //Seeds

    private lateinit var Main_IMG_seeds: Array<AppCompatImageView>

    private var seedFlag: Boolean = false

    private var seedOnBoard: Boolean = false

    private val isSeedVisible = IntArray(30)

    //Eggs

    private lateinit var Main_IMG_eggs: Array<AppCompatImageView>

    private val isEggVisible = IntArray(30)

    private var eggsCollected: Int = 0

    private var eggOnBoard = false


    //Others

    private var gameSpeed: Boolean = false // f - slow , t - fast

    private var gameMode: Boolean = false // f - buttons , t - sensors

    private var roosterPosition: Int = 2 //0 - 4

    private lateinit var timerJob: Job //Timer For Coroutine

    private var spaceFlag: Int = 0 //Boolean For Spacing The Lines

    private var lastSpawnLane = -1

    private var sameLaneStreak = 0

    private var hits: Int = 0

    private var didNavigateToEndScreen = false
    private var isGameOver: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        findViews()
        initViews()
        startGame()


    }

    override fun onResume() {
        super.onResume()
        BackgroundMusicPlayer.Companion.getInstance().playMusic()
    }

    override fun onPause() {
        super.onPause()
        BackgroundMusicPlayer.Companion.getInstance().pauseMusic()
    }

    private fun findViews() {

        Main_IMG_hearts = arrayOf(
            findViewById(R.id.Main_IMG_Heart_L),
            findViewById(R.id.Main_IMG_Heart_M),
            findViewById(R.id.Main_IMG_Heart_R)
        )

        Main_IMG_Rooster_1 = findViewById(R.id.Main_IMG_Rooster_1)
        Main_IMG_Rooster_2 = findViewById(R.id.Main_IMG_Rooster_2)
        Main_IMG_Rooster_3 = findViewById(R.id.Main_IMG_Rooster_3)
        Main_IMG_Rooster_4 = findViewById(R.id.Main_IMG_Rooster_4)
        Main_IMG_Rooster_5 = findViewById(R.id.Main_IMG_Rooster_5)

        Main_IMG_pans = arrayOf(
            findViewById(R.id.Main_IMG_Pan1),
            findViewById(R.id.Main_IMG_Pan2),
            findViewById(R.id.Main_IMG_Pan3),
            findViewById(R.id.Main_IMG_Pan4),
            findViewById(R.id.Main_IMG_Pan5),
            findViewById(R.id.Main_IMG_Pan6),
            findViewById(R.id.Main_IMG_Pan7),
            findViewById(R.id.Main_IMG_Pan8),
            findViewById(R.id.Main_IMG_Pan9),
            findViewById(R.id.Main_IMG_Pan10),
            findViewById(R.id.Main_IMG_Pan11),
            findViewById(R.id.Main_IMG_Pan12),
            findViewById(R.id.Main_IMG_Pan13),
            findViewById(R.id.Main_IMG_Pan14),
            findViewById(R.id.Main_IMG_Pan15),
            findViewById(R.id.Main_IMG_Pan16),
            findViewById(R.id.Main_IMG_Pan17),
            findViewById(R.id.Main_IMG_Pan18),
            findViewById(R.id.Main_IMG_Pan19),
            findViewById(R.id.Main_IMG_Pan20),
            findViewById(R.id.Main_IMG_Pan21),
            findViewById(R.id.Main_IMG_Pan22),
            findViewById(R.id.Main_IMG_Pan23),
            findViewById(R.id.Main_IMG_Pan24),
            findViewById(R.id.Main_IMG_Pan25),
            findViewById(R.id.Main_IMG_Pan26),
            findViewById(R.id.Main_IMG_Pan27),
            findViewById(R.id.Main_IMG_Pan28),
            findViewById(R.id.Main_IMG_Pan29),
            findViewById(R.id.Main_IMG_Pan30)
        )

        Main_IMG_seeds = arrayOf(
            findViewById(R.id.Main_IMG_Seed1),
            findViewById(R.id.Main_IMG_Seed2),
            findViewById(R.id.Main_IMG_Seed3),
            findViewById(R.id.Main_IMG_Seed4),
            findViewById(R.id.Main_IMG_Seed5),
            findViewById(R.id.Main_IMG_Seed6),
            findViewById(R.id.Main_IMG_Seed7),
            findViewById(R.id.Main_IMG_Seed8),
            findViewById(R.id.Main_IMG_Seed9),
            findViewById(R.id.Main_IMG_Seed10),
            findViewById(R.id.Main_IMG_Seed11),
            findViewById(R.id.Main_IMG_Seed12),
            findViewById(R.id.Main_IMG_Seed13),
            findViewById(R.id.Main_IMG_Seed14),
            findViewById(R.id.Main_IMG_Seed15),
            findViewById(R.id.Main_IMG_Seed16),
            findViewById(R.id.Main_IMG_Seed17),
            findViewById(R.id.Main_IMG_Seed18),
            findViewById(R.id.Main_IMG_Seed19),
            findViewById(R.id.Main_IMG_Seed20),
            findViewById(R.id.Main_IMG_Seed21),
            findViewById(R.id.Main_IMG_Seed22),
            findViewById(R.id.Main_IMG_Seed23),
            findViewById(R.id.Main_IMG_Seed24),
            findViewById(R.id.Main_IMG_Seed25),
            findViewById(R.id.Main_IMG_Seed26),
            findViewById(R.id.Main_IMG_Seed27),
            findViewById(R.id.Main_IMG_Seed28),
            findViewById(R.id.Main_IMG_Seed29),
            findViewById(R.id.Main_IMG_Seed30)
        )
        Main_IMG_eggs = arrayOf(
            findViewById(R.id.Main_IMG_Egg1),
            findViewById(R.id.Main_IMG_Egg2),
            findViewById(R.id.Main_IMG_Egg3),
            findViewById(R.id.Main_IMG_Egg4),
            findViewById(R.id.Main_IMG_Egg5),
            findViewById(R.id.Main_IMG_Egg6),
            findViewById(R.id.Main_IMG_Egg7),
            findViewById(R.id.Main_IMG_Egg8),
            findViewById(R.id.Main_IMG_Egg9),
            findViewById(R.id.Main_IMG_Egg10),
            findViewById(R.id.Main_IMG_Egg11),
            findViewById(R.id.Main_IMG_Egg12),
            findViewById(R.id.Main_IMG_Egg13),
            findViewById(R.id.Main_IMG_Egg14),
            findViewById(R.id.Main_IMG_Egg15),
            findViewById(R.id.Main_IMG_Egg16),
            findViewById(R.id.Main_IMG_Egg17),
            findViewById(R.id.Main_IMG_Egg18),
            findViewById(R.id.Main_IMG_Egg19),
            findViewById(R.id.Main_IMG_Egg20),
            findViewById(R.id.Main_IMG_Egg21),
            findViewById(R.id.Main_IMG_Egg22),
            findViewById(R.id.Main_IMG_Egg23),
            findViewById(R.id.Main_IMG_Egg24),
            findViewById(R.id.Main_IMG_Egg25),
            findViewById(R.id.Main_IMG_Egg26),
            findViewById(R.id.Main_IMG_Egg27),
            findViewById(R.id.Main_IMG_Egg28),
            findViewById(R.id.Main_IMG_Egg29),
            findViewById(R.id.Main_IMG_Egg30)
        )

        Main_FAB_Right = findViewById(R.id.Main_FAB_Right)
        Main_FAB_Left = findViewById(R.id.Main_FAB_Left)

        Main_LBL_Score = findViewById(R.id.Main_LBL_Score)

    }

    private fun initViews() {
        updateRooster()

        val bundle: Bundle? = intent.extras

        val message = bundle?.getString(Constants.BundleKeys.MESSAGE_KEY) ?: "Button"

        if (message == "Button")
            gameMode = false
        else if (message == "Sensor")
            gameMode = true

        gameSpeed = bundle?.getBoolean(Constants.BundleKeys.SPEED_KEY) ?: false





        Main_FAB_Left.setOnClickListener { view ->
            moveLeft()
        }
        Main_FAB_Right.setOnClickListener { view ->
            moveRight()
        }


    }

    // Start Timer Coroutine For Pans UI Update
    private fun startGame() {

        timerJob = lifecycleScope.launch {
            while (isActive) {
                Log.d("Timer Runnable", "Active: \$isActive")
                // Refresh UI:
                updatePanUI()
                updateSeedUI()
                updateEggUI()
                if (gameSpeed)
                    delay(Constants.Timer.DELAY / 2)
                else
                    delay(Constants.Timer.DELAY)

                if (scoreFlag)
                    updateScore()
                else
                    scoreFlag = !scoreFlag
                checkIfGameOver()
            }
        }
    }

    private fun updateScore() {
        score++
        Main_LBL_Score.text = String.format("%03d", score)
    }

    private fun checkIfGameOver() {
        if (isGameOver) {
            timerJob.cancel()
            val intent = Intent(this, GameEndScreen::class.java)
            val bundle = Bundle()
            val totalScore =
                if (gameSpeed) {
                ((score * 7) + (eggsCollected * 25)) * 2
            } else {
                (score * 7) + (eggsCollected * 25)
            }

            bundle.putInt(Constants.BundleKeys.MESSAGE_KEY, totalScore)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }
    }

    private fun updateSeedUI() {
        updateSeedVisibleArray()
        if (seedFlag && !seedOnBoard) {
            val lane = Random.Default.nextInt(5)
            isSeedVisible[lane] = 1
            seedOnBoard = true
        }

        preventSeedPanEggOverlap()

    }

    private fun updateSeedVisibleArray() {
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
        seedOnBoard = anySeed
    }

    private fun updateEggUI() {
        updateEggVisibleArray()
        if (!eggOnBoard) {
            val chance = Random.Default.nextInt(100)
            if (chance < 15) {
                val lane = Random.Default.nextInt(5)
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


    // Choose random Pan To Add & Call For Func To - Update The Game Board, Check If Hit Happened
    private fun updatePanUI() {
        updatePanVisibleArray()
        if (spaceFlag == 0) {
            var lane = Random.Default.nextInt(5)

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

            isVisible[lane] = 1
            spaceFlag = 1
        } else
            spaceFlag = 0
        preventSeedPanEggOverlap()

        checkForHit()

    }

    //Update The Array That Track Visibility Of The Pans
    private fun updatePanVisibleArray() {
        Main_IMG_pans.forEachIndexed { index, img ->
            if (img.visibility == View.VISIBLE && index < 25) {
                isVisible[index] = 0
                isVisible[index + 5] = 1
            }
            if (img.visibility == View.VISIBLE && index >= 25) {
                isVisible[index] = 0
            }

        }
    }

    private fun preventSeedPanEggOverlap() {
        for (i in 0 until 30) {
            if (isVisible[i] == 1) {
                if (isSeedVisible[i] == 1) {
                    isSeedVisible[i] = 0
                    seedOnBoard = false
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


    //Change Visibility Of Pans & Seed And 'Move' them Down
    private fun refreshUI() {
        Main_IMG_pans.forEachIndexed { index, img ->
            if (isVisible[index] == 1) {
                img.visibility = View.VISIBLE
            } else
                img.visibility = View.INVISIBLE

        }
        Main_IMG_seeds.forEachIndexed { index, img ->
            if (isSeedVisible[index] == 1) {
                img.visibility = View.VISIBLE
            } else
                img.visibility = View.INVISIBLE

        }
        Main_IMG_eggs.forEachIndexed { index, img ->
            if (isEggVisible[index] == 1) {
                img.visibility = View.VISIBLE
            } else
                img.visibility = View.INVISIBLE

        }
    }

    //Check If There Is A Collision Between A Rooster And [A Pan Or A Seed]
    private fun checkForHit() {
        val hitIndex = when (roosterPosition) {
            0 -> 25
            1 -> 26
            2 -> 27
            3 -> 28
            else -> 29
        }
        if (isVisible[hitIndex] == 1) {
            isVisible[hitIndex] = 0
            makePanHit()
        }
        if (isSeedVisible[hitIndex] == 1) {
            isSeedVisible[hitIndex] = 0
            makeSeedHit()
            seedOnBoard = false
        }
        if (isEggVisible[hitIndex] == 1) {
            isEggVisible[hitIndex] = 0
            makeEggHit()
        }

        refreshUI()
    }

    //Calling a Function For Heart Decrease & Calling Functions For Toast & Vibrate
    private fun makePanHit() {
        val ssp = SingleSoundPlayer(this)
        ssp.playSound(R.raw.hitsound)
        heartDecrease()
        makeToast()
        makeVibration()

    }

    private fun makeSeedHit() {
        val ssp = SingleSoundPlayer(this)
        ssp.playSound(R.raw.seedsound)
        heartIncrease()
    }
    private fun makeEggHit() {
        val ssp = SingleSoundPlayer(this)
        ssp.playSound(R.raw.eggcrack)
        eggsCollected++
    }

    //Heart Ui Update
    private fun heartDecrease() {
        if (hits < Main_IMG_hearts.size) {
            hits++
            Main_IMG_hearts[Main_IMG_hearts.size - hits].visibility = View.INVISIBLE
        }
        seedFlag = hits > 0

        if (hits == Main_IMG_hearts.size) {
            isGameOver = true
        }

    }

    private fun heartIncrease() {
        if (hits > 0) {
            hits--
            Main_IMG_hearts[Main_IMG_hearts.size - 1 - hits].visibility = View.VISIBLE
        }

        seedFlag = hits > 0

    }

    //Toast Function
    private fun makeToast() {
        SignalManager.Companion
            .getInstance()
            .toast(
                "You've Been Hit!",
                SignalManager.ToastLength.Long
            )
    }

    //Vibrate Function
    private fun makeVibration() {
        SignalManager.Companion
            .getInstance()
            .vibrate()
    }

    //update rooster position in the Main Activity
    private fun updateRooster() {
        Main_IMG_Rooster_1.visibility = View.INVISIBLE
        Main_IMG_Rooster_2.visibility = View.INVISIBLE
        Main_IMG_Rooster_3.visibility = View.INVISIBLE
        Main_IMG_Rooster_4.visibility = View.INVISIBLE
        Main_IMG_Rooster_5.visibility = View.INVISIBLE

        when (roosterPosition) {
            0 -> Main_IMG_Rooster_1.visibility = View.VISIBLE
            1 -> Main_IMG_Rooster_2.visibility = View.VISIBLE
            2 -> Main_IMG_Rooster_3.visibility = View.VISIBLE
            3 -> Main_IMG_Rooster_4.visibility = View.VISIBLE
            4 -> Main_IMG_Rooster_5.visibility = View.VISIBLE
        }
    }

    //updating the rooster position in the variable for right move
    private fun moveRight() {
        if (roosterPosition < 4) {
            roosterPosition++
            updateRooster()
        }
    }

    //updating the rooster position in the variable for left move
    private fun moveLeft() {
        if (roosterPosition > 0) {
            roosterPosition--
            updateRooster()
        }
    }

}