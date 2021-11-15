package com.spot.acronyms.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spot.acronyms.databinding.InflaterAcronymItemBinding
import com.spot.acronyms.databinding.InflaterNoNetworkItemBinding
import com.spot.acronyms.services.model.LongFormData

class AcronymsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = mutableListOf<LongFormData>()
    private var hasExtraRow = false
    var openSettings: ()->Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        if(viewType == LIST_ITEM){
            val itemBinding = InflaterAcronymItemBinding.inflate(layoutInflater, parent, false)
            return AcronymsViewHolder(
                itemBinding
            )
        }else {
            val itemBinding = InflaterNoNetworkItemBinding.inflate(layoutInflater, parent, false)
            return NoNetworkViewHolder(
                itemBinding
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is AcronymsViewHolder){
            data.getOrNull(position)?.let {
                holder.bindTo(data[position])
            }
        }else if(holder is NoNetworkViewHolder){
            holder.bindTo(openSettings)
        }

    }

    override fun getItemCount(): Int = data.size + if (hasExtraRow) 1 else 0

    fun updateExtraRow(hasExtra: Boolean) {
        val hadExtra = this.hasExtraRow
        if (hadExtra != hasExtra) {
            this.hasExtraRow = hasExtra
            if (hadExtra) {
                //Remove extra
                notifyItemRemoved(0)
            } else {
                //Add extra
                notifyItemInserted(1)
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position == 0 && hasExtraRow) {
            EXTRA_ROW
        } else {
            LIST_ITEM
        }

    private val LIST_ITEM = 1
    private val EXTRA_ROW = 2

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<LongFormData>) {
        this.data = data.toMutableList()
        notifyDataSetChanged()
    }

    inner class AcronymsViewHolder(private val viewBinding: InflaterAcronymItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindTo(data: LongFormData) {
            viewBinding.item = data
        }
    }
}