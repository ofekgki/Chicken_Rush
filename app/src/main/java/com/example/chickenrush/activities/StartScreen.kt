package com.example.chickenrush.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chickenrush.R
import com.example.chickenrush.utilities.Constants
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView

class StartScreen : AppCompatActivity() {


    private lateinit var start_STC_fastslow: SwitchMaterial

    private lateinit var start_BTN_sensor: MaterialButton

    private lateinit var start_BTN_button: MaterialButton

    private lateinit var start_BTN_top10: MaterialButton

    private lateinit var start_LBL_heading: MaterialTextView

    private var gameSpeed: Boolean = false // f - slow,  t - fast


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViews()
        initviews()
    }

    private fun initviews() {
        start_STC_fastslow.setOnCheckedChangeListener { start_STC_fastslow, isChecked ->
            if(isChecked) {
                gameSpeed = true
                start_STC_fastslow.text = getString(R.string.fast)
            }
            else{
                gameSpeed = false
                start_STC_fastslow.text = getString(R.string.slow)
            }
        }

        start_BTN_sensor.setOnClickListener {
            changeActivity(getString(R.string.mode_sensor), gameSpeed)

        }

        start_BTN_button.setOnClickListener {
            changeActivity(getString(R.string.mode_button), gameSpeed)
        }

        start_BTN_top10.setOnClickListener {

        }
    }

    private fun findViews() {
        start_STC_fastslow = findViewById(R.id.start_STC_fastslow)
        start_BTN_sensor = findViewById(R.id.start_BTN_sensor)
        start_BTN_button = findViewById(R.id.start_BTN_button)
        start_BTN_top10 = findViewById(R.id.start_BTN_top10)
        start_LBL_heading = findViewById(R.id.start_LBL_heading)

    }

    private fun changeActivity(mode: String, speed: Boolean) {
        val intent = Intent(this, MainActivity::class.java)
        var bundle = Bundle()
        bundle.putString(Constants.BundleKeys.MODE_KEY, mode)
        bundle.putBoolean(Constants.BundleKeys.SPEED_KEY, speed)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

}