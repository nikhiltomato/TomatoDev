package com.wework.zombiedev.data.remote.repository


import android.content.SharedPreferences

import com.wework.zombiedev.Resource
import com.wework.zombiedev.common.values.Constants
import com.wework.zombiedev.data.NetOperationSource
import com.wework.zombiedev.data.local.dao.TrendingRepoDao
import com.wework.zombiedev.data.local.entity.TrendingDevEntity
import com.wework.zombiedev.data.local.entity.TrendingRepoEntity
import com.wework.zombiedev.data.remote.api.Api


import io.reactivex.Flowable
import io.reactivex.Observable

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class FetchTrendingRepository @Inject constructor (private val gitDao: TrendingRepoDao, private val apiService: Api , private val sharedPref : SharedPreferences) {

    var forceFetch = false
    fun getRepositories(): Observable<Resource<List<TrendingRepoEntity>>>{
        return object : NetOperationSource<List<TrendingRepoEntity>>() {

            override fun saveCallResult(item: List<TrendingRepoEntity>) {
                gitDao.insertRepositories(item)
                sharedPref.edit().putBoolean(Constants.IS_REPO_CACHE_EXIST,true).putLong(Constants.TRENDING_DATA_UPDATE_TIME,System.currentTimeMillis()).apply()
            }

            override fun shouldFetch(): Boolean {

                if(shouldCallRemoteRepository()){
                    deleteTrendingRepo()
                    return true
                }else{
                    return false
                }
            }

            override fun loadFromDb(): Flowable<List<TrendingRepoEntity>> {
                val repositories = gitDao.getRepositories()
                return Flowable.just(repositories)
            }

            override fun createCall(): Observable<Resource<List<TrendingRepoEntity>>> {
                return apiService.fetchTrendingRepositories().map {
                    if (it.isSuccessful)
                        Resource.success(it.body()!!)
                    else
                        Resource.error("", mutableListOf())
                }
            }


        }.getAsObservable()
    }

    fun deleteTrendingRepo() {
        sharedPref.edit().putBoolean(Constants.IS_REPO_CACHE_EXIST,false).apply()
        gitDao.deleteAllEntries()
    }

    fun deleteTrendingDevs() {
        sharedPref.edit().putBoolean(Constants.IS_DEV_CACHE_EXIST,false).apply()
        gitDao.deleteAllDevs()
    }

    private fun isTimeElapsed():Boolean{
        val timeNow = System.currentTimeMillis()
        val timeDiff = timeNow - sharedPref.getLong(Constants.TRENDING_DATA_UPDATE_TIME,System.currentTimeMillis())

        return (timeDiff / (1000 * 60 * 60)) > 2

    }

    private fun shouldCallRemoteRepository():Boolean{
        return  forceFetch || !sharedPref.getBoolean(Constants.IS_REPO_CACHE_EXIST,false) || isTimeElapsed()
    }

    private fun shouldCallRemoteDev():Boolean{
        return  forceFetch || !sharedPref.getBoolean(Constants.IS_DEV_CACHE_EXIST,false) || isTimeElapsed()
    }


    fun getDevs(): Observable<Resource<List<TrendingDevEntity>>>{
        return object : NetOperationSource<List<TrendingDevEntity>>() {

            override fun saveCallResult(item: List<TrendingDevEntity>) {
                gitDao.insertDevs(item)
                sharedPref.edit().putBoolean(Constants.IS_DEV_CACHE_EXIST,true).putLong(Constants.TRENDING_DATA_UPDATE_TIME,System.currentTimeMillis()).apply()
            }

            override fun shouldFetch(): Boolean {

                if(shouldCallRemoteDev()){
                    deleteTrendingDevs()
                    return true
                }else{
                    return false
                }
            }

            override fun loadFromDb(): Flowable<List<TrendingDevEntity>> {
                val repositories = gitDao.getDevelopers()
                return Flowable.just(repositories)
            }

            override fun createCall(): Observable<Resource<List<TrendingDevEntity>>> {
                return apiService.fetchTrendingDevelopers().map {
                    if (it.isSuccessful)
                        Resource.success(it.body()!!)
                    else
                        Resource.error("", mutableListOf())
                }
            }


        }.getAsObservable()
    }

}