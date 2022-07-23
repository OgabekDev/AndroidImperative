package dev.ogabek.android_imperative.repository

import dev.ogabek.android_imperative.db.TVShowDao
import dev.ogabek.android_imperative.model.TVShow
import dev.ogabek.android_imperative.network.TVShowService
import javax.inject.Inject

class TVShowRepository @Inject constructor(
    private val tvShowService: TVShowService,
    private val dao: TVShowDao
) {

    /**
     *  Retrofit Related
     */

    suspend fun apiTVShowPopular(page: Int) = tvShowService.apiTVShowPopular(page)
    suspend fun apiTVShowDetails(q: Int) = tvShowService.apiTVShowDetails(q)

    /**
     *  Room Related
     */

    suspend fun getTVShowFromDB() = dao.getTVShowFromDB()
    suspend fun insertTVShowToDB(tvShow: TVShow) = dao.insertTVShowToDB(tvShow)
    suspend fun deleteTVShowsFromDB() = dao.deleteTVShowsFromDB()

}