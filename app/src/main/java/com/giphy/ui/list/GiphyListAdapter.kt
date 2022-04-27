package com.giphy.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.giphy.databinding.ItemGiphyBinding
import com.giphy.network.model.Giphy
import com.google.android.material.shape.RoundedCornerTreatment

@SuppressLint("NotifyDataSetChanged")
class GiphyListAdapter(private var data: List<Giphy>, private val onClick:(Giphy)-> Unit) : RecyclerView.Adapter<GiphyListAdapter.GiphyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {
        val binding = ItemGiphyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GiphyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        with(holder){
            with(data[position]) {
                Glide.with(holder.itemView.context)
                    .asGif()
                    .centerCrop()
                    .load(images.original.url)
                    .into(binding.itemGiphy)

                holder.itemView.setOnClickListener { onClick.invoke(this) }
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun updateList(list: List<Giphy>){
        data = list
        notifyDataSetChanged()
    }

    inner class GiphyViewHolder(val binding: ItemGiphyBinding) : RecyclerView.ViewHolder(binding.root)

}