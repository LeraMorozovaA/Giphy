package com.giphy.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.giphy.R
import com.giphy.databinding.ItemGiphyBinding
import com.giphy.api.model.Giphy

class GiphyListAdapter(
    private val onClick: (String) -> Unit,
    private val onLongClick: (Giphy) -> Unit
) : PagingDataAdapter<Giphy, GiphyListAdapter.GiphyViewHolder>(GiphyDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {
        val binding = ItemGiphyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GiphyViewHolder(binding, onClick, onLongClick)
    }

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    @SuppressLint("NotifyDataSetChanged")
    inner class GiphyViewHolder(
        private val binding: ItemGiphyBinding,
        private val onClick: (String) -> Unit,
        private val onLongClick: (Giphy) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var giphy: Giphy

        init {
            binding.itemGiphy.setOnClickListener { onClick.invoke(giphy.id) }
            binding.itemGiphy.setOnLongClickListener {
                val snapshot = this@GiphyListAdapter.snapshot().firstOrNull { snapshot ->
                        snapshot?.id == giphy.id
                    }
                snapshot?.isSelected = true
                this@GiphyListAdapter.notifyDataSetChanged()
                onLongClick.invoke(giphy)
                return@setOnLongClickListener true
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        fun bind(item: Giphy?) {
            giphy = item ?: return
            Glide.with(itemView.context)
                .asGif()
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .load(giphy.url)
                .into(binding.itemGiphy)

            val visibility = if (giphy.isSelected) View.VISIBLE else View.INVISIBLE
            binding.imgSelect.visibility = visibility
        }
    }
}

class GiphyDiffCallBack : DiffUtil.ItemCallback<Giphy>() {
    override fun areItemsTheSame(oldItem: Giphy, newItem: Giphy): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Giphy, newItem: Giphy): Boolean {
        return oldItem.id == newItem.id && oldItem.url == newItem.url
    }
}