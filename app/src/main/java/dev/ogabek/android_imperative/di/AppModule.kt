package dev.ogabek.android_imperative.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ogabek.android_imperative.db.AppDatabase
import dev.ogabek.android_imperative.db.TVShowDao
import dev.ogabek.android_imperative.network.Server
import dev.ogabek.android_imperative.network.Server.IS_TESTER
import dev.ogabek.android_imperative.network.TVShowService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     *  Retrofit Related
     */

    @Provides
    fun server(): String {
        return if (IS_TESTER) Server.getDevelopment()
        else Server.getProduction()
    }

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit {
        return Retrofit.Builder().baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun tvShowService(): TVShowService = retrofitClient().create(TVShowService::class.java)

    /**
     *  Room Related
     */

    @Provides
    @Singleton
    fun appDatabase(context: Application): AppDatabase {
        return AppDatabase.getAppDBInstance(context)
    }

    @Provides
    @Singleton
    fun tvShowDao(appDatabase: AppDatabase): TVShowDao {
        return appDatabase.getTVShowDao()
    }

}