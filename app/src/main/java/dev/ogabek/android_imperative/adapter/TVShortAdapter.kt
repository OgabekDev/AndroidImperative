package dev.ogabek.android_imperative.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.ogabek.android_imperative.R
import dev.ogabek.android_imperative.databinding.ItemTvShortBinding
import dev.ogabek.android_imperative.model.TVShow

class TVShortAdapter(val items: ArrayList<String>): BaseAdapter() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tv_short, parent, false)
        return TVShortViewHolder(view)
    }

    class TVShortViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTvShortBinding.bind(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShort = items[position]

        if (holder is TVShortViewHolder) {
            holder.apply {
                Glide.with(binding.ivShort).load(tvShort).into(binding.ivShort)
            }
        }

    }

}