package com.example.chickenrush.activities

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chickenrush.R
import com.example.chickenrush.interfaces.ScoreClickedCallback
import com.example.chickenrush.ui.MapFragment
import com.example.chickenrush.ui.TopTenFragment

class TopTen : AppCompatActivity() {


    private lateinit var topten_FRAME_list: FrameLayout

    private lateinit var topten_FRAME_map: FrameLayout

    private lateinit var mapFragment: MapFragment

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
        mapFragment = MapFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.topten_FRAME_map, mapFragment)
            .commit()

        toptenFragment = TopTenFragment()
        toptenFragment.sc =
            object : ScoreClickedCallback {

                override fun ScoreClicked(lat: Double, lon: Double) {
                    mapFragment.zoom(lat,lon)
                }

            }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.topten_FRAME_list, toptenFragment)
            .commit()

    }

    private fun findViews() {

        topten_FRAME_list = findViewById(R.id.topten_FRAME_list)
        topten_FRAME_map = findViewById(R.id.topten_FRAME_map)

    }
}