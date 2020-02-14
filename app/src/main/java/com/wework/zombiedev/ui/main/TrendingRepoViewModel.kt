package com.wework.zombiedev.ui.main


import android.annotation.SuppressLint


import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.wework.zombiedev.Resource
import com.wework.zombiedev.common.values.Constants
import com.wework.zombiedev.data.local.dao.TrendingRepoDao
import com.wework.zombiedev.data.local.entity.TrendingDevEntity

import com.wework.zombiedev.data.local.entity.TrendingRepoEntity

import com.wework.zombiedev.data.remote.repository.FetchTrendingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import javax.inject.Inject

class TrendingRepoViewModel @Inject constructor(private val repository: FetchTrendingRepository) : ViewModel() {

    var trendLiveData = MutableLiveData<Resource<List<TrendingRepoEntity>>>()
    var trendDevLivedata = MutableLiveData<Resource<List<TrendingDevEntity>>>()
    private val uiScope = CoroutineScope(Dispatchers.Main)

    @Inject lateinit var gitDao: TrendingRepoDao

    @SuppressLint("CheckResult")
    fun fetchTrendingRepos(forceFetch : Boolean = false) {
        repository.forceFetch = forceFetch
        trendLiveData.value = Resource.loading(null)

        repository.getRepositories().subscribe { resource ->

                if (resource.isLoaded) {
                    resource.data?.let {
                        if(it.isNotEmpty())
                            trendLiveData.value = Resource.success(it)
                        else
                            trendLiveData.value = Resource.error(Constants.SOME_WENT_WRONG,it)
                    }
                } else {
                    trendLiveData.value = Resource.loading(null)
                }
            }
    }


    @SuppressLint("CheckResult")
    fun fetchTrendingDevs(forceFetch : Boolean = false) {
        repository.forceFetch = forceFetch
        trendDevLivedata.value = Resource.loading(null)

        repository.getDevs().subscribe { resource ->

            if (resource.isLoaded) {
                resource.data?.let {
                    if(it.isNotEmpty())
                        trendDevLivedata.value = Resource.success(it)
                    else
                        trendDevLivedata.value = Resource.error(Constants.SOME_WENT_WRONG,it)
                }
            } else {
                trendDevLivedata.value = Resource.loading(null)
            }
        }
    }

    fun searchRepoAndDevUi(query : String){
        uiScope.launch {
            searchRepoAndDevs(query)
        }
    }


     suspend fun searchRepoAndDevs(query : String) = withContext(Dispatchers.IO){

        val filteredRepos = gitDao.getRepositories().filter {
                it.name!!.contains(query)
        }

        val filteredDevs = gitDao.getDevelopers().filter {
            it.name!!.contains(query)
        }

        trendDevLivedata.postValue(Resource.success(filteredDevs))
        trendLiveData.postValue(Resource.success(filteredRepos))
    }

    fun getTrendingLiveData() = trendLiveData

    fun getTrendingDevsLiveData() = trendDevLivedata

}