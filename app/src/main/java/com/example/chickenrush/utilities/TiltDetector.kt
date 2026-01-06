package com.example.chickenrush.utilities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.chickenrush.interfaces.TiltCallback
import com.example.chickenrush.R

class TiltDetector(context: Context, private val tiltCallback: TiltCallback) {

    private val sensorManager = context.getSystemService(
        Context.SENSOR_SERVICE
    ) as SensorManager

    private val sensor = sensorManager.getDefaultSensor(
        Sensor.TYPE_ACCELEROMETER
    )

    var tiltDiractionX: Int = 0
        private set

    var tiltDiractionY: Int = 0
        private set


    private val delay: Long =
        context.resources.getInteger(R.integer.tilt_delay).toLong()

    private val threshold =
        context.resources.getInteger(R.integer.tilt_threshold).toFloat()

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
            when
            {
                x >= threshold -> {
                    tiltDiractionX = 1 // Left
                    tiltCallback?.tiltX()
                }
                x <= -threshold -> {
                    tiltDiractionX = -1 // Right
                    tiltCallback?.tiltX()
                }

                else -> {
                    tiltDiractionX = 0
                }
            }
            when
            {
                y >= threshold -> {
                    tiltDiractionY = 1 // Slow
                    tiltCallback?.tiltY()
                }
                y <= -threshold -> {
                    tiltDiractionY = -1 // Fast
                    tiltCallback?.tiltY()
                }

                else -> {
                    tiltDiractionY = 0
                }
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
