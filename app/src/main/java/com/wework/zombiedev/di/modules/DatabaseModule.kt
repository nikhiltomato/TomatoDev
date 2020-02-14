package com.wework.zombiedev.di.modules


import android.content.Context
import androidx.room.Room

import com.wework.zombiedev.data.AppDb
import com.wework.zombiedev.data.local.dao.TrendingRepoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideDatabase(ctx: Context): AppDb {

        return Room.databaseBuilder(ctx,
            AppDb::class.java, "Trending.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    internal fun provideTrendingRepoDao(appDb: AppDb): TrendingRepoDao {
        return appDb.trendingRepoDao()
    }
}