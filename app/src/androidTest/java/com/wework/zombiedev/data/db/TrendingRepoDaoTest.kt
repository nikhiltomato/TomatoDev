package com.wework.zombiedev.data.db





import com.wework.zombiedev.data.util.Utility

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TrendingRepoDaoTest : RoomDbAndroidTest() {

    @Test
    fun insertRepositoriesTest() {
        val repositories = Utility.getMockedRepositories()
        db.trendingRepoDao().deleteAllEntries()
        db.trendingRepoDao().insertRepositories(repositories)

        val storedRepos = db.trendingRepoDao().getRepositories()
        Assert.assertEquals(12, storedRepos[0].id)
        Assert.assertEquals("hippy 7", storedRepos[0].author)
    }

    @Test
    fun insertEmptyPostsTest() {
        val repositories = Utility.mockEmptyRepositoryList()
        db.trendingRepoDao().deleteAllEntries()
        db.trendingRepoDao().insertRepositories(repositories)

        val storedRepos = db.trendingRepoDao().getRepositories()
        Assert.assertEquals(0, storedRepos.size)
    }

    @Test
    fun deleteAllEntriesTest(){
        db.trendingRepoDao().deleteAllEntries()
        val storedRepos = db.trendingRepoDao().getRepositories()
        Assert.assertEquals(0, storedRepos.size)
    }


    @Test
    fun insertDevsTest() {
        val devs = Utility.getMockedDevs()
        db.trendingRepoDao().deleteAllDevs()
        db.trendingRepoDao().insertDevs(devs)

        val storedRepos = db.trendingRepoDao().getDevelopers()
        Assert.assertEquals(12, storedRepos[0].id)
        Assert.assertEquals("hippy 7", storedRepos[0].username)
    }

    @Test
    fun insertEmptyDevsTest() {
        val devs = Utility.mockEmptyDevList()
        db.trendingRepoDao().deleteAllDevs()
        db.trendingRepoDao().insertDevs(devs)

        val storedRepos = db.trendingRepoDao().getDevelopers()
        Assert.assertEquals(0, storedRepos.size)
    }

    @Test
    fun deleteAllDevTest(){
        db.trendingRepoDao().deleteAllDevs()
        val storedRepos = db.trendingRepoDao().getDevelopers()
        Assert.assertEquals(0, storedRepos.size)
    }
}
