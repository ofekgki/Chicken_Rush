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
import com.example.chickenrush.Managers.GameManager
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


    //Fabs

    private lateinit var Main_FAB_Left: FloatingActionButton

    private lateinit var Main_FAB_Right: FloatingActionButton


    //Score
    private lateinit var Main_LBL_Score: MaterialTextView


    private var scoreFlag: Boolean = false

    //Seeds

    private lateinit var Main_IMG_seeds: Array<AppCompatImageView>


    //Eggs

    private lateinit var Main_IMG_eggs: Array<AppCompatImageView>


    //Others

    private var gameSpeed: Boolean = false // f - slow , t - fast

    private var gameMode: Boolean = false // f - buttons , t - sensors

    private lateinit var timerJob: Job //Timer For Coroutine

    private lateinit var gameManager: GameManager


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
        gameManager = GameManager(Main_IMG_hearts.size)
        initViews()
        startGame()


    }

    override fun onResume() {
        super.onResume()
        BackgroundMusicPlayer.getInstance().playMusic()
    }

    override fun onPause() {
        super.onPause()
        BackgroundMusicPlayer.getInstance().pauseMusic()
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

        Main_IMG_Rooster_3.visibility = View.VISIBLE

        val bundle: Bundle? = intent.extras

        val message = bundle?.getString(Constants.BundleKeys.MESSAGE_KEY) ?: "Button"

        if (message == "Button")
            gameMode = false
        else if (message == "Sensor")
            gameMode = true

        gameSpeed = bundle?.getBoolean(Constants.BundleKeys.SPEED_KEY) ?: false





        Main_FAB_Left.setOnClickListener { view ->
            gameManager.moveLeft()
            updateRooster()
        }
        Main_FAB_Right.setOnClickListener { view ->
            gameManager.moveRight()
            updateRooster()
        }


    }

    // Start Timer Coroutine For Pans UI Update
    private fun startGame() {

        timerJob = lifecycleScope.launch {
            while (isActive) {
                Log.d("Timer Runnable", "Active: \$isActive")

                gameManager.updatePanUI()
                gameManager.updateSeedUI()
                gameManager.updateEggUI()
                if (gameSpeed)
                    delay(Constants.Timer.DELAY / 2)
                else
                    delay(Constants.Timer.DELAY)

                if (scoreFlag)
                    gameManager.scoreUpdate()

                when(gameManager.checkForHit()){
                    0 -> makeHit(0)
                    1 -> makeHit(1)
                    2 -> makeHit(2)
                }
                refreshUI()
                checkIfGameOver()
                scoreFlag = !scoreFlag
            }
        }
    }

    private fun checkIfGameOver() {
        if (gameManager.isGameOver) {
            timerJob.cancel()
            val intent = Intent(this, GameEndScreen::class.java)
            val bundle = Bundle()
            val totalScore = gameManager.calculateFinalScore(gameSpeed)
            Log.d("total score", "score: $totalScore",)


            bundle.putInt(Constants.BundleKeys.SCORE_KEY, totalScore)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }
    }

    //Change Visibility Of Pans & Seed And 'Move' them Down
    private fun refreshUI() {
        Main_IMG_pans.forEachIndexed { index, img ->
            if (gameManager.isPanVisible[index] == 1) {
                img.visibility = View.VISIBLE
            } else
                img.visibility = View.INVISIBLE

        }
        Main_IMG_seeds.forEachIndexed { index, img ->
            if (gameManager.isSeedVisible[index] == 1) {
                img.visibility = View.VISIBLE
            } else
                img.visibility = View.INVISIBLE

        }
        Main_IMG_eggs.forEachIndexed { index, img ->
            if (gameManager.isEggVisible[index] == 1) {
                img.visibility = View.VISIBLE
            } else
                img.visibility = View.INVISIBLE

        }

        Main_LBL_Score.text = String.format("%03d", gameManager.score)


    }

    private fun makeHit(type: Int){ // 0 - pan, 1 - seed, 2 - egg
        val ssp = SingleSoundPlayer(this)

        when (type) {
            0 -> {
                ssp.playSound(R.raw.hitsound)
                heartDecrease()
                makeToast()
                makeVibration()
            }
            1 -> {
                ssp.playSound(R.raw.seedsound)
                heartIncrease()
            }
            else -> {
                ssp.playSound(R.raw.eggcrack)
                gameManager.eggsCollected++
            }
        }


    }

    //Heart Ui Update
    private fun heartDecrease() {
        if (gameManager.hits < Main_IMG_hearts.size) {
            gameManager.hits++
            Main_IMG_hearts[Main_IMG_hearts.size - gameManager.hits].visibility = View.INVISIBLE
        }
        gameManager.seedFlag = gameManager.hits > 0

        if (gameManager.hits == Main_IMG_hearts.size) {
            gameManager.isGameOver = true
        }

    }

    private fun heartIncrease() {
        if (gameManager.hits > 0) {
            gameManager.hits--
            Main_IMG_hearts[Main_IMG_hearts.size - 1 - gameManager.hits].visibility = View.VISIBLE
        }

        gameManager.seedFlag = gameManager.hits > 0

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

        when (gameManager.roosterPosition) {
            0 -> Main_IMG_Rooster_1.visibility = View.VISIBLE
            1 -> Main_IMG_Rooster_2.visibility = View.VISIBLE
            2 -> Main_IMG_Rooster_3.visibility = View.VISIBLE
            3 -> Main_IMG_Rooster_4.visibility = View.VISIBLE
            4 -> Main_IMG_Rooster_5.visibility = View.VISIBLE
        }
    }


}