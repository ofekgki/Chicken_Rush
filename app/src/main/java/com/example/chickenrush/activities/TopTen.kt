package com.example.chickenrush.activities

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chickenrush.R
import com.example.chickenrush.interfaces.ScoreClickedCallback
import com.example.chickenrush.ui.MapsFragment
import com.example.chickenrush.ui.TopTenFragment
import com.google.android.material.button.MaterialButton

class TopTen : AppCompatActivity() {


    private lateinit var topten_FRAME_list: FrameLayout

    private lateinit var topten_FRAME_map: FrameLayout


    private lateinit var topten_BTN_menu: MaterialButton

    private lateinit var mapFragment: MapsFragment

    private lateinit var toptenFragment: TopTenFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_top_ten)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        findViews()
        initViews()

    }

    private fun initViews() {

        topten_BTN_menu.setOnClickListener {
            changeActivity()
        }

        mapFragment = MapsFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.topten_FRAME_map, mapFragment)
            .commit()

        toptenFragment = TopTenFragment(object : ScoreClickedCallback {
            override fun scoreClicked(lat: Double, lon: Double) {
                mapFragment.focusOn(lat, lon)
            }
        })

        supportFragmentManager
            .beginTransaction()
            .add(R.id.topten_FRAME_list, toptenFragment)
            .commit()

    }

    private fun findViews() {

        topten_BTN_menu = findViewById(R.id.topten_BTN_menu)
        topten_FRAME_list = findViewById(R.id.topten_FRAME_list)
        topten_FRAME_map = findViewById(R.id.topten_FRAME_map)

    }

    private fun changeActivity() {
        val intent = Intent(this , StartScreen::class.java)
        startActivity(intent)
        finish()
    }
}