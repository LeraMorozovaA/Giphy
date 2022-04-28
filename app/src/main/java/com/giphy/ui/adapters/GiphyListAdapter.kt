package com.giphy.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.giphy.databinding.ItemGiphyBinding
import com.giphy.network.model.Giphy

@SuppressLint("NotifyDataSetChanged")
class GiphyListAdapter(private var data: List<Giphy>, private val onClick:(Int)-> Unit) : RecyclerView.Adapter<GiphyListAdapter.GiphyViewHolder>() {

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
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .load(images.original.url)
                    .into(binding.itemGiphy)

                holder.itemView.setOnClickListener { onClick.invoke(position) }
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