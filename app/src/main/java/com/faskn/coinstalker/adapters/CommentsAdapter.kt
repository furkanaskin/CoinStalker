package com.faskn.coinstalker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faskn.coinstalker.R
import com.faskn.coinstalker.model.CommentsDTO
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentsAdapter(val comments: ArrayList<CommentsDTO>) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bindForecast(commentsDTOS[position])

        holder.itemView.txt_comment.text = comments[position].text
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val commentItem = itemView.findViewById<TextView>(R.id.txt_comment)!!

    }
}