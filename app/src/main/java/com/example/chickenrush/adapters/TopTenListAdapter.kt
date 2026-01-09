package com.example.chickenrush.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chickenrush.R
import com.example.chickenrush.models.ScoreEntry
import com.google.android.material.textview.MaterialTextView

class TopTenListAdapter (
    private val items : List<ScoreEntry>,
    private val onItemClick: (ScoreEntry) -> Unit
) : RecyclerView.Adapter<TopTenListAdapter.ScoreVH>(){

    class ScoreVH(itemView: View) : RecyclerView.ViewHolder(itemView){


        var scoreitem_LBL_name: MaterialTextView = itemView.findViewById(R.id.scoreitem_LBL_name)

        var scoreitem_LBL_score: MaterialTextView = itemView.findViewById(R.id.scoreitem_LBL_score)

        var scoreitem_LBL_lat: MaterialTextView = itemView.findViewById(R.id.scoreitem_LBL_lat)

        var scoreitem_LBL_lon: MaterialTextView = itemView.findViewById(R.id.scoreitem_LBL_lon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreVH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.score_item,
                parent,
                false
            )
        return ScoreVH(v)
    }

    override fun onBindViewHolder(holder: ScoreVH, position: Int) {
        val item = items[position]

        holder.scoreitem_LBL_name.text = item.playerName
        holder.scoreitem_LBL_score.text = item.score.toString()
        holder.scoreitem_LBL_lat.text = item.lat.toString()
        holder.scoreitem_LBL_lon.text = item.lon.toString()

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

}