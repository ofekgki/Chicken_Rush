package com.example.chickenrush.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chickenrush.R
import com.example.chickenrush.adapters.TopTenListAdapter
import com.example.chickenrush.interfaces.ScoreClickedCallback
import com.example.chickenrush.managers.SharedPreferencesManager

class TopTenFragment : Fragment() {
        private lateinit var topten_FRG_RV_list: RecyclerView

        var scoreClickedCallback: ScoreClickedCallback? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(
            R.layout.fragment_top_ten_list,
            container,
            false
        )
        findViews(v)
        initViews(v)
        return v
    }

    private fun findViews(v: View) {
        topten_FRG_RV_list = v.findViewById(R.id.topten_FRG_RV_list)
    }

    private fun initViews(v: View) {

        topten_FRG_RV_list.layoutManager = LinearLayoutManager(requireContext())

        val scores = SharedPreferencesManager
            .getInstance()
            .getTop10Scores()

        topten_FRG_RV_list.adapter = TopTenListAdapter(scores) {
            entry ->
                scoreClickedCallback?.scoreClicked(entry.lat, entry.lon)
            }
    }

}
