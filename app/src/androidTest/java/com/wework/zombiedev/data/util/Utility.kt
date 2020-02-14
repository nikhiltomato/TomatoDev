package com.wework.zombiedev.data.util


import com.wework.zombiedev.data.local.entity.TrendingDevEntity
import com.wework.zombiedev.data.local.entity.TrendingRepoEntity
import java.util.ArrayList

object Utility {


    fun getMockedRepositories(): List<TrendingRepoEntity> {
        val repositories = ArrayList<TrendingRepoEntity>()

        val items = 8
        for (i in 0 until items) {

            val trendingRepoEntity = TrendingRepoEntity(
                id = 12,
                author = "hippy $i",
                name = "HelloWorld $i",
                avatar = " $i",
                url = "http://bitbucket.com/tulseja $i",
                description = "Tomato Dev $i",
                language = "kotlin/Javascript $i",
                languageColor = "#343434",
                stars = 98 + i,
                forks = 22 + i
            )
            repositories.add(trendingRepoEntity)
        }

        return repositories
    }

    fun mockEmptyRepositoryList() : List<TrendingRepoEntity>{
        return ArrayList()
    }

    fun getMockedDevs(): List<TrendingDevEntity> {
        val devs = ArrayList<TrendingDevEntity>()

        val items = 8
        for (i in 0 until items) {


            val trendingdevEntity = TrendingDevEntity(
                id = 12,
                username = "hippy $i",
                name = "HelloWorld $i",
                url = "http://bitbucket.com/tulseja $i",
                avatar = " $i"
            )
            devs.add(trendingdevEntity )
        }

        return devs
    }

    fun mockEmptyDevList() : List<TrendingDevEntity>{
        return ArrayList()
    }
}
