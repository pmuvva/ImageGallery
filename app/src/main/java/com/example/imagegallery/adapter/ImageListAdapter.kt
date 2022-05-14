package com.example.imagegallery.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagegallery.activity.ImageDetailActivity
import com.example.imagegallery.R
import com.example.imagegallery.data.ImageDetails
import com.example.imagegallery.util.Constants

class ImageListAdapter(var context: Context) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    var dataList = emptyList<ImageDetails>()

    internal fun setDataList(dataList: List<ImageDetails>) {
        this.dataList = dataList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var title: TextView

        init {
            image = itemView.findViewById(R.id.image)
            title = itemView.findViewById(R.id.title)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // Inflate the custom layout
        var view = LayoutInflater.from(parent.context).
        inflate(R.layout.layout_list_item, parent, false)
        return ViewHolder(view)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = dataList[position]
        holder.title.text = data.title
        Glide.with(holder.itemView.context).load(data.media.m).into(holder.image)
        holder.image.setOnClickListener {
            val intent = Intent(context, ImageDetailActivity::class.java)
            intent.putExtra(Constants.IMAGE_DETAILS, data)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataList.size

}
