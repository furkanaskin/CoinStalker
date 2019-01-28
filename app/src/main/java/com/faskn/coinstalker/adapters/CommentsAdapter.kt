package com.faskn.coinstalker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faskn.coinstalker.R
import com.faskn.coinstalker.model.CommentsDTO
import kotlinx.android.synthetic.main.comment_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class CommentsAdapter(val comments: ArrayList<CommentsDTO>) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.txt_comment.text = comments[position].text
        val formattedDate = unixTimeStampFormatter(comments[position].timestamp)
        holder.itemView.txt_timestamp.text = formattedDate
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun unixTimeStampFormatter(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date = java.util.Date(timestamp)
        sdf.format(date)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        var hour = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"

        if (calendar.get(Calendar.MINUTE) < 10) {
            hour = "${calendar.get(Calendar.HOUR_OF_DAY)}:0${calendar.get(Calendar.MINUTE)}"
        }

        return "$day $month, $hour"
    }
}