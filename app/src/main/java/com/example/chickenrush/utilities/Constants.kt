package com.example.chickenrush.utilities

class Constants {
    object Timer {
        const val DELAY: Long = 700L
    }

    object Tilt{
        const val TILT_DELAY: Long = 500L
        const val TILT_THRESHOLD: Float = 3F

    }

    object BundleKeys{
        const val MESSAGE_KEY: String = "MESSAGE_KEY"
        const val MODE_KEY: String = "MODE_KEY"
        const val SPEED_KEY: String = "SPEED_KEY"
        const val SCORE_KEY: String = "SCORE_KEY"

        const val MUSIC_KEY: String = "MUSIC_KEY"


    }

    object SPKEYS{
        const val DATA_FILE = "game_data"
        const val TOP_10_SCORES = "top_10_scores"
    }
}