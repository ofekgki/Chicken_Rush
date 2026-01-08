package com.example.chickenrush.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chickenrush.R
import com.example.chickenrush.utilities.Constants
import com.example.chickenrush.managers.SharedPreferencesManager
import com.example.chickenrush.managers.SignalManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class GameEndScreen : AppCompatActivity() {

    private lateinit var End_LBL_Massage: MaterialTextView

    private lateinit var End_BTN_StartGame: AppCompatImageButton

    private lateinit var End_LBL_StartGame: MaterialTextView


    private lateinit var End_BTN_TopTen: AppCompatImageButton

    private lateinit var End_LBL_TopTen: MaterialTextView


    private lateinit var highScore_ET_text: TextInputEditText

    private lateinit var playerName: String

    private var lat: Double = 0.0

    private var lon: Double = 0.0

    private val LOCATION_PERMISSION_REQUEST = 1001

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_end_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        findViews()
        initViews()
    }

    @SuppressLint("DefaultLocale")
    private fun initViews() {

        val bundle: Bundle? = intent.extras

        getCurrentLocation()

        val score = bundle?.getInt(Constants.BundleKeys.SCORE_KEY, 0) ?: 0

        End_LBL_Massage.text = String.format("%03d", score)

        End_BTN_StartGame.setOnClickListener {
            if (validateName()) {
                SharedPreferencesManager.getInstance().addNewScore(playerName, score, lat, lon)
                val intent = Intent(this, StartScreen::class.java)
                startActivity(intent)
                finish()
            }
        }

        End_BTN_TopTen.setOnClickListener {
            if (validateName()) {
                SharedPreferencesManager.getInstance().addNewScore(playerName, score, lat, lon)
                val intent = Intent(this, TopTen::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun findViews() {
        highScore_ET_text = findViewById(R.id.highScore_ET_text)
        End_LBL_Massage = findViewById(R.id.End_LBL_Massage)
        End_BTN_StartGame = findViewById(R.id.End_BTN_StartGame)
        End_LBL_StartGame = findViewById(R.id.End_LBL_StartGame)
        End_BTN_TopTen = findViewById(R.id.End_BTN_Topten)
        End_LBL_TopTen = findViewById(R.id.End_LBL_TopTen)
    }

    private fun validateName(): Boolean {

        val name = highScore_ET_text.text?.toString()

        if (name.isNullOrEmpty()) {
            makeToast()
            makeVibarate()
            return false
        } else {
            playerName = highScore_ET_text.text.toString().trim()
            return true
        }

    }

    private fun makeToast() {
        SignalManager
            .getInstance()
            .toast(
                "Must Enter Name!",
                SignalManager.ToastLength.LONG
            )
    }

    private fun makeVibarate() {
        SignalManager
            .getInstance()
            .vibrate()

    }

    //Location

    private fun hasLocationPermission(): Boolean{
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getCurrentLocation()
            }
            else {
                lon = 0.0
                lat = 0.0
            }
        }

    }

    private fun getCurrentLocation() {
        if (!hasLocationPermission()) {
            requestLocationPermission()
            return
        }


        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    lat = it.latitude
                    lon = it.longitude
                 }
            }
    }
}