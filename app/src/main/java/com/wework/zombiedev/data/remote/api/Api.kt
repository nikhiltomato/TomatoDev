package com.wework.zombiedev.data.remote.api

import com.wework.zombiedev.data.local.entity.TrendingDevEntity
import com.wework.zombiedev.data.local.entity.TrendingRepoEntity
import io.reactivex.Observable
import retrofit2.Response

import retrofit2.http.GET


interface Api {

    @GET("repositories")
    fun fetchTrendingRepositories(): Observable<Response<List<TrendingRepoEntity>>>

    @GET("developers")
    fun fetchTrendingDevelopers(): Observable<Response<List<TrendingDevEntity>>>

}