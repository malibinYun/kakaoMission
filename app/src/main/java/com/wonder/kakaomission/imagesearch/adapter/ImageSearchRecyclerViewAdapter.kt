package com.wonder.kakaomission.imagesearch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wonder.kakaomission.R
import com.wonder.kakaomission.network.response.ImageSearchDocument

/**
 * Created By Yun Hyeok
 * on 8월 02, 2019
 */

class ImageSearchRecyclerViewAdapter(private val ctx: Context) :
    RecyclerView.Adapter<ImageSearchRecyclerViewAdapter.Holder>() {

    val imageDocuments = ArrayList<ImageSearchDocument>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_image_search, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = imageDocuments.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = imageDocuments[position]
        Glide
            .with(ctx)
            .load(data.thumbnail_url)
            .into(holder.image)
    }

    fun addItems(items: List<ImageSearchDocument>) {
        val previousCount = itemCount
        imageDocuments.addAll(items)
        notifyItemRangeChanged(previousCount, itemCount)
    }

    fun initFirstSearch(items: List<ImageSearchDocument>) {
        deleteAllItems()
        addItems(items)
    }

    fun deleteAllItems() {
        val totalItemCount = itemCount
        imageDocuments.clear()
        notifyItemRangeRemoved(0, totalItemCount)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_item_image_search_image)
    }
}