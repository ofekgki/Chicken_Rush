package com.example.chickenrush.utilities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.example.chickenrush.Interfaces.TiltCallback
import com.example.chickenrush.R
import kotlin.math.abs

class TiltDetector(context: Context, private val tiltCallback: TiltCallback) {

    private val sensorManager = context.getSystemService(
        Context.SENSOR_SERVICE
    ) as SensorManager

    private val sensor = sensorManager.getDefaultSensor(
        Sensor.TYPE_ACCELEROMETER
    )

    var tiltX: Int = 0
        private set

    var tiltY: Int = 0
        private set


    private val delay: Long = R.integer.tilt_delay.toLong()

    private var timestamp: Long = 0L
    private lateinit var sensorEventListener: SensorEventListener

    init {
        initEventListener()
    }

    private fun initEventListener() {
        sensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                // Pass
            }

            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                calculateTile(x, y)
            }

        }
    }


    private fun calculateTile(x: Float, y: Float) {
        if (System.currentTimeMillis() - timestamp >= delay ) {
            timestamp = System.currentTimeMillis()
            if (abs(x) >= R.integer.tilt_threshold.toFloat()) {
                tiltX++
                tiltCallback?.tiltX()
            }

            if (abs(y) >= R.integer.tilt_threshold.toFloat()) {
                tiltY++
                tiltCallback?.tiltY()
            }

        }
    }

    fun start() {
        sensorManager
            .registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
    }


    fun stop() {
        sensorManager
            .unregisterListener(
                sensorEventListener,
                sensor
            )
    }
}
