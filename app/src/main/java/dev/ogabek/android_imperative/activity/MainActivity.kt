package dev.ogabek.android_imperative.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.ogabek.android_imperative.adapter.TvShowAdapter
import dev.ogabek.android_imperative.databinding.ActivityMainBinding
import dev.ogabek.android_imperative.model.TVShow
import dev.ogabek.android_imperative.utils.Logger
import dev.ogabek.android_imperative.viewmodel.MainViewModel

class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val viewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: TvShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        initObserves()

        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.rvHome.layoutManager = gridLayoutManager
        refreshAdapter(ArrayList())
        adapter.saveTVShow = {
            viewModel.insertTVShowsToDB(it)
        }

        adapter.onClick = { tvShow, image ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("show_id", tvShow.id)
            intent.putExtra("show_img", tvShow.image_thumbnail_path)
            intent.putExtra("show_name", tvShow.name)
            intent.putExtra("show_network", tvShow.network)
            intent.putExtra("iv_movie", ViewCompat.getTransitionName(image))

            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(this, image, ViewCompat.getTransitionName(image)!!)

            startActivity(intent, option.toBundle())

        }

        binding.rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    val nextPage = viewModel.tvShowPopular.value!!.page + 1
                    val totalPages = viewModel.tvShowPopular.value!!.pages
                    if (nextPage <= totalPages) {
                        viewModel.apiTVShowPopular(nextPage)
                    }
                }
            }
        })

        viewModel.apiTVShowPopular(1)

        binding.btnFab.setOnClickListener {
            binding.rvHome.smoothScrollToPosition(0)
        }

    }

    private fun initObserves() {
        // Retrofit Related

        viewModel.tvShowsFromApi.observe(this) {
            Logger.d(TAG, it.size.toString())

            adapter.setNewTVShows(it)

        }

        viewModel.errorMessage.observe(this) {
            Logger.d(TAG, "Loading $it")
        }

        viewModel.isLoading.observe(this) {
            Logger.d(TAG, it.toString())

            if (it) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(this) {
            Logger.d(TAG, "List Size $it")
        }

        // Room Related
    }

    fun refreshAdapter(items: ArrayList<TVShow>) {
        adapter = TvShowAdapter(items)
        binding.rvHome.adapter = adapter
    }

}