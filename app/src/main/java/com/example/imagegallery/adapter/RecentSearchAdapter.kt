package com.example.imagegallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imagegallery.R

class RecentSearchAdapter(var context: Context, var onRecentSearchListener: OnRecentSearchListener) :
    RecyclerView.Adapter<RecentSearchAdapter.ViewHolder>() {

    private var recentSearchList = emptyList<String>()

    internal fun setSearchList(recentSearchList: List<String>) {
        this.recentSearchList = recentSearchList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var searchItem: TextView

        init {
            searchItem = itemView.findViewById(R.id.tv_recent_item)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // Inflate the custom layout
        var view = LayoutInflater.from(parent.context).
        inflate(R.layout.layout_recent_list_item, parent, false)
        return ViewHolder(view)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = recentSearchList[position]
        holder.searchItem.text = data
        holder.searchItem.setOnClickListener {
            onRecentSearchListener.onRecentItemSelected(data)
        }
    }

    override fun getItemCount() = recentSearchList.size

    interface OnRecentSearchListener{
        fun onRecentItemSelected(recentItem: String)
    }

}
