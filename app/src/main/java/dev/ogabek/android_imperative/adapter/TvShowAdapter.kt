package dev.ogabek.android_imperative.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.ogabek.android_imperative.R
import dev.ogabek.android_imperative.databinding.ItemTvShowBinding
import dev.ogabek.android_imperative.model.TVShow

class TvShowAdapter(private val items: ArrayList<TVShow>): BaseAdapter() {

    var onClick:((tvShow: TVShow, image: ImageView) -> Unit)? = null
    var saveTVShow:((tvShow: TVShow) -> Unit)? = null

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false)

        return TVShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShow = items[position]

        if (holder is TVShowViewHolder) {
            Glide.with(holder.binding.ivMovie).load(tvShow.image_thumbnail_path).into(holder.binding.ivMovie)
            holder.binding.tvName.text = tvShow.name
            holder.binding.tvType.text = tvShow.network

            // Click the tv show
            ViewCompat.setTransitionName(holder.binding.ivMovie, tvShow.name)
            holder.binding.ivMovie.setOnClickListener {
                // Save TVShow into Room
                saveTVShow?.invoke(tvShow)

                // Call Details Activity
                onClick?.invoke(tvShow, holder.binding.ivMovie)
            }

        }
    }

    inner class TVShowViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemTvShowBinding.bind(view)

    }

    fun setNewTVShows(tvShows: ArrayList<TVShow>) {
//        items.clear()
        items.addAll(tvShows)
        notifyDataSetChanged()
    }

}