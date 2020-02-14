package com.wework.zombiedev.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wework.zombiedev.data.local.dao.TrendingRepoDao
import com.wework.zombiedev.data.local.entity.TrendingDevEntity
import com.wework.zombiedev.data.local.entity.TrendingRepoEntity
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration
import android.icu.lang.UCharacter.GraphemeClusterBreak.V



@Database(entities = [TrendingRepoEntity::class , TrendingDevEntity::class], version = 2, exportSchema = false)

abstract class AppDb : RoomDatabase() {

    abstract fun trendingRepoDao(): TrendingRepoDao

}