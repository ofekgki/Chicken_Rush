package com.example.chickenrush.Managers

import android.content.Context
import com.example.chickenrush.utilities.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesManager private constructor(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        Constants.SPKEYS.DATA_FILE,
        Context.MODE_PRIVATE
    )

    private val gson = Gson()

    data class ScoreEntry(
        val playerName: String,
        val score: Int,
        val lat: Double,
        val lon: Double
    )

    companion object{
        @Volatile
        private var instance: SharedPreferencesManager? = null

        fun init(context: Context) : SharedPreferencesManager{
            return instance ?: synchronized(this){
                instance ?: SharedPreferencesManager(context).also { instance = it }
            }
        }

        fun getInstance(): SharedPreferencesManager {
            return instance ?: throw IllegalStateException(
                "SharedPreferencesManager must be initialized by calling init(context) before use."
            )

        }
    }

    fun putString( key: String, value: String){
        with(sharedPreferences.edit()){
            putString(key,value)
            apply()
        }

    }

    fun getString( key: String, defaultValue: String): String{
        return sharedPreferences
            .getString(
                key,
                defaultValue
            ) ?: defaultValue
    }

    fun saveTop10Scores(scores: List<ScoreEntry>){
        val json = gson.toJson(scores)
        putString(Constants.SPKEYS.TOP_10_SCORES, json)

    }

    fun getTop10Scores(): MutableList<ScoreEntry> {

        val json = getString(Constants.SPKEYS.TOP_10_SCORES, "[]")

        val type = object : TypeToken<MutableList<ScoreEntry>>() {}.type
        return gson.fromJson(json, type) ?: mutableListOf()
    }

    fun addNewScore(playerName: String, score: Int, lat: Double, lon: Double) {
        val scores = getTop10Scores()

        scores.add(ScoreEntry(playerName, score, lat, lon))

        val sortedTop10 = scores
            .sortedByDescending { it.score }
            .take(10)

        saveTop10Scores(sortedTop10)
    }

}