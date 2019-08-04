package com.wonder.kakaomission.imagesearch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.wonder.kakaomission.R

/**
 * Created By Yun Hyeok
 * on 8월 02, 2019
 */

class ImageSearchRecyclerViewAdapter(private val ctx: Context) :
    RecyclerView.Adapter<ImageSearchRecyclerViewAdapter.Holder>() {

    val imageUrls = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_image_search, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = imageUrls.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_item_image_search_image)
    }
}