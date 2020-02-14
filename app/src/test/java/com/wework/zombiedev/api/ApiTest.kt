package com.wework.zombiedev.api


import android.util.Log
import com.wework.zombiedev.data.remote.api.Api
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import java.io.IOException

class ApiTest : ApiMockBase<Api>() {

    private lateinit var service: Api

    @Before
    fun initService() {
        this.service = createService(Api::class.java)
    }

    @Test
    @Throws(IOException::class)
    fun fetchPostsTest() {
        enqueueResponse("test_repositories.json")
        val response = service.fetchTrendingRepositories().blockingFirst()
        Assert.assertEquals(true, response.isSuccessful)

        val apiResponse = response.body()
        Assert.assertEquals(25, apiResponse?.size)
    }


    @Test
    @Throws(IOException::class)
    fun fetchDevsTest() {
        enqueueResponse("test_developers.json")
        val response = service.fetchTrendingDevelopers().blockingFirst()
        Assert.assertEquals(true, response.isSuccessful)
        val apiResponse = response.body()
        Assert.assertEquals(25, apiResponse?.size)
    }
}
