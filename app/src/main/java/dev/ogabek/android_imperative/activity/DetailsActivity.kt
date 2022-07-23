package dev.ogabek.android_imperative.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dev.ogabek.android_imperative.R
import dev.ogabek.android_imperative.adapter.TVShortAdapter
import dev.ogabek.android_imperative.databinding.ActivityDetailsBinding
import dev.ogabek.android_imperative.utils.Logger
import dev.ogabek.android_imperative.viewmodel.DetailsViewModel

class DetailsActivity : BaseActivity() {

    private val viewModel: DetailsViewModel by viewModels()

    private val TAG = this::class.java.simpleName
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

    }

    private fun initObserves() {
        // Retrofit Related
        viewModel.tvShowDetails.observe(this) {
            Logger.d(TAG, it.toString())
            refreshAdapter(it.tvShow.pictures)
            binding.tvDetails.text = it.tvShow.description
        }

        viewModel.errorMessage.observe(this) {
            Logger.d(TAG, it.toString())
        }

        viewModel.isLoading.observe(this) {
            Logger.d(TAG, it.toString())
            if (it) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        }
    }

    private fun refreshAdapter(pictures: List<String>) {
        val adapter = TVShortAdapter(pictures as ArrayList)
        binding.rvShots.adapter = adapter
    }

    private fun initViews() {

        initObserves()

        binding.rvShots.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.ivClose.setOnClickListener {
            ActivityCompat.finishAfterTransition(this)
        }


        val extras = intent.extras

        val showId = extras!!.getLong("show_id")
        val showImg = extras.getString("show_img")
        val showName = extras.getString("show_name")
        val showNetwork = extras.getString("show_network")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imageTransitionName = extras.getString("iv_movie")
            binding.ivDetails.transitionName = imageTransitionName
        }

        binding.tvName.text = showName
        binding.tvType.text = showNetwork
        Glide.with(this).load(showImg).into(binding.ivDetails)

        viewModel.apiTVShowDetails(showId.toInt())

    }
}