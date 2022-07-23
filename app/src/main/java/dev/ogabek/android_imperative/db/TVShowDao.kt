package dev.ogabek.android_imperative.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.ogabek.android_imperative.model.TVShow

@Dao
interface TVShowDao {

    @Query("SELECT * FROM tv_show")
    suspend fun getTVShowFromDB(): List<TVShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShowToDB(tvShow: TVShow)

    @Query("DELETE FROM tv_show")
    suspend fun deleteTVShowsFromDB()

}
