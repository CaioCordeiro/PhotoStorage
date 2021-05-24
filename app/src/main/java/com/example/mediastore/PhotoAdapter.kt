package com.example.mediastore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.net.Uri
import kotlinx.android.synthetic.main.photo_item.view.*

class PhotoAdapter(private val photoList: List<PhotoItem>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> ()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return PhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = photoList[position]

        holder.imageView.setImageURI(Uri.parse(currentItem.image))
        holder.imageText.text = currentItem.name
    }

    override fun getItemCount() = photoList.size

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.photoImage
        val imageText: TextView = itemView.imageName
    }
}