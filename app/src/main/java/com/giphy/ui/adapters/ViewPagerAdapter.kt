package com.giphy.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.giphy.network.model.Giphy
import com.giphy.ui.details.DetailsGiphyFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private var data = listOf<Giphy>()

    fun setData(list: List<Giphy>){
        data = list
    }

    override fun getItemCount(): Int = data.size

    override fun createFragment(position: Int): Fragment =
        DetailsGiphyFragment.getInstance(data[position].id)

}