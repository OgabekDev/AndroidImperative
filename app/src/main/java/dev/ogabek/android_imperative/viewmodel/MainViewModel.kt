package dev.ogabek.android_imperative.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ogabek.android_imperative.db.TVShowDao
import dev.ogabek.android_imperative.model.TVShow
import dev.ogabek.android_imperative.model.TVShowDetails
import dev.ogabek.android_imperative.model.TVShowPopular
import dev.ogabek.android_imperative.repository.TVShowRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TVShowRepository): ViewModel() {

    /**
     *  Retrofit Related
     */

    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    val tvShowsFromApi = MutableLiveData<ArrayList<TVShow>>()
    val tvShowsFromDB = MutableLiveData<List<TVShow>>()

    val tvShowPopular = MutableLiveData<TVShowPopular>()
    private val tvShowDetails = MutableLiveData<TVShowDetails>()

    fun apiTVShowPopular(page: Int) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.apiTVShowPopular(page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val resp = response.body()
                    tvShowPopular.postValue(resp)

                    tvShowsFromApi.postValue(resp!!.tv_shows)
                    isLoading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }

    /**
     *  Room Related
     */

    fun getTVShowsFromDB() {
        viewModelScope.launch {
            val tvShows = repository.getTVShowFromDB()
            tvShowsFromDB.postValue(tvShows)
        }
    }

    fun insertTVShowsToDB(tvShow: TVShow) {
        viewModelScope.launch {
            repository.insertTVShowToDB(tvShow)
        }
    }

    fun deleteTVShowsFromDB() {
        viewModelScope.launch {
            repository.deleteTVShowsFromDB()
        }
    }

}