package com.example.chickenrush.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.chickenrush.R
import com.example.chickenrush.activities.MainActivity
import com.example.chickenrush.interfaces.ScoreClickedCallback
import com.example.chickenrush.utilities.Constants
import com.google.android.material.button.MaterialButton


class TopTenFragment : Fragment() {
    companion object {

        private lateinit var topten_FRG_BTN_menu: MaterialButton
        private lateinit var topten_FRG_RV_list: RecyclerView
        var scoreClickedCallback: ScoreClickedCallback? = null
    }

    object a : View.OnClickListener {
        override fun onClick(v: View?) {
            var coordinates = topten_FRG_RV_list?.split(",")
            var lat: Double = coordinates?.getOrNull(0)?.toDoubleOrNull() ?: 0.0
            var lon: Double = coordinates?.getOrNull(1)?.toDoubleOrNull() ?: 0.0

            scoreClickedCallback?.scoreClicked(lat, lon)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v: View = inflater.inflate(
            R.layout.fragment_top_ten_list,
            container,
            false
        )
        findViews(v)
        initViews(v)
        return v
    }

    private fun findViews(v: View) {
        highScore_ET_text = v.findViewById(R.id.highScore_ET_text)
        highScore_BTN_send = v.findViewById(R.id.highScore_BTN_send)
    }

    private fun initViews(v: View) {

        topten_FRG_BTN_menu.setOnClickListener {
            changeActivity(v)
        }

    }

    private fun changeActivity(v: View) {
        val intent = Intent(, MainActivity::class.java)
        startActivity(intent)
        View.finish()
    }

}
    companion object {

    }
}