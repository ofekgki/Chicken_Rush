package com.example.chickenrush.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chickenrush.R
import com.example.chickenrush.utilities.Constants
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class GameEndScreen : AppCompatActivity() {

    private lateinit var End_LBL_Massage: MaterialTextView

    private lateinit var End_BTN_StartGame: AppCompatImageButton

    private lateinit var End_LBL_StartGame: MaterialTextView


    private lateinit var End_BTN_Topten: AppCompatImageButton

    private lateinit var End_LBL_TopTen: MaterialTextView


    private lateinit var highScore_ET_text: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_end_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViews()
        initViews()
    }

    private fun initViews() {
        val bundle: Bundle? = intent.extras

        val score = bundle?.getInt(Constants.BundleKeys.SCORE_KEY, 0)

        End_LBL_Massage.text = String.format("%03d", score)

        End_BTN_StartGame.setOnClickListener { view: View ->
            val intent = Intent(this, StartScreen::class.java)
            startActivity(intent)
            finish()
        }

        End_BTN_Topten.setOnClickListener { view: View ->
            val intent = Intent(this, TopTen::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun findViews() {
        highScore_ET_text = findViewById(R.id.highScore_ET_text)
        End_LBL_Massage = findViewById(R.id.End_LBL_Massage)
        End_BTN_StartGame = findViewById(R.id.End_BTN_StartGame)
        End_LBL_StartGame = findViewById(R.id.End_LBL_StartGame)
        End_BTN_Topten = findViewById(R.id.End_BTN_Topten)
        End_LBL_TopTen = findViewById(R.id.End_LBL_TopTen)
    }
}