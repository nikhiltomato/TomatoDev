package com.wework.zombiedev.data.local.dao

import androidx.room.*
import com.wework.zombiedev.data.local.entity.TrendingDevEntity
import com.wework.zombiedev.data.local.entity.TrendingRepoEntity

@Dao
interface TrendingRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(trendingRepos: List<TrendingRepoEntity>)

    @Query("SELECT * FROM `trending_repo` ")
    fun getRepositories(): List<TrendingRepoEntity>

    @Query("Delete  from `trending_repo`")
    fun deleteAllEntries() : Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDevs(trendingDevs: List<TrendingDevEntity>)

    @Query("SELECT * FROM `trending_dev` ")
    fun getDevelopers(): List<TrendingDevEntity>

    @Query("Delete  from `trending_dev`")
    fun deleteAllDevs() : Int


}