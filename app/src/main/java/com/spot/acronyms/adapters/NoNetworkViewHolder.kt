package com.spot.acronyms.adapters

import androidx.recyclerview.widget.RecyclerView
import com.spot.acronyms.databinding.InflaterNoNetworkItemBinding

class NoNetworkViewHolder(private val binding: InflaterNoNetworkItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindTo(openSettings: ()->Unit = {}){
        binding.openSetting.setOnClickListener {
            openSettings.invoke()
        }
    }
}