package com.wework.zombiedev.presentation.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.wework.zombiedev.Resource
import com.wework.zombiedev.data.Status
import com.wework.zombiedev.data.local.dao.TrendingRepoDao
import com.wework.zombiedev.data.local.entity.TrendingRepoEntity
import com.wework.zombiedev.data.remote.api.Api
import com.wework.zombiedev.data.remote.repository.FetchTrendingRepository
import io.reactivex.Observable

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response

class TrendingRepoViewModelTest {

    private lateinit var trendLiveData: MutableLiveData<Resource<List<TrendingRepoEntity>>>

    @Mock
    private lateinit var fetchRepo: FetchTrendingRepository


    @Mock
    lateinit var ctx : Context

    @Mock
    internal lateinit var githubDao: TrendingRepoDao

    @Mock
    internal lateinit var githubApiService: Api

    @Mock
    internal lateinit var sharedPref: SharedPreferences

    @Mock
    internal lateinit var editor: SharedPreferences.Editor

    @get:Rule
    val rule = InstantTaskExecutorRule()



    @Before
    fun setUp() {
        trendLiveData = MutableLiveData()
        this.editor = Mockito.mock(SharedPreferences.Editor::class.java)
        this.sharedPref = Mockito.mock(SharedPreferences::class.java)
        this.githubDao = Mockito.mock(TrendingRepoDao::class.java)
        this.githubApiService = Mockito.mock(Api::class.java)
        this.ctx= Mockito.mock(Context::class.java)
        Mockito.`when`(
            ctx.getSharedPreferences(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(sharedPref)
        Mockito.`when`(
            editor.putBoolean(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyBoolean()
            )
        ).thenReturn(editor) // Also mock `putString`, `putFloat` and so on
        Mockito.`when`(sharedPref.edit()).thenReturn(editor)
        fetchRepo = FetchTrendingRepository(githubDao, githubApiService,sharedPref)
    }


    private fun mockViewModel() {
        Mockito.`when`(githubApiService.fetchTrendingRepositories()).thenReturn(Observable.empty<Response<List<TrendingRepoEntity>>>() )
        Mockito.`when`(fetchRepo.getRepositories()).then {

            trendLiveData.value = Resource.loading(null)

            //wait
            Thread.sleep(2000)


            val list = ArrayList<TrendingRepoEntity>()

            val items = 8
            for (i in 0 until items) {

                val trendingRepoEntity = TrendingRepoEntity(
                    id = 12,
                    author = "scds $i",
                    name = "HelloWorld $i",
                    avatar = " $i",
                    url = "http://bitbucket.com/tulseja $i",
                    description = "Tomato Dev $i",
                    language = "kotlin/Javascript $i",
                    languageColor = "#343434",
                    stars = 98 + i,
                    forks = 22 + i
                )
                list.add(trendingRepoEntity)
            }

            trendLiveData.value = Resource.success(list)
            Any()
        }


    }

    @Test
    fun fetchTrendingRepo() {
        mockViewModel()
        trendLiveData.observeForever {

            when (it.status) {
                Status.SUCCESS -> {
                    assertNull(it)
                    assertNotNull(it.data)
                    assert(it.data is List<TrendingRepoEntity>)
                }

                Status.LOADING -> {
                    assertNull(it)
                    assertNull(it.data)
                }

                Status.ERROR -> {
                    assertNull(it.data)
                    assertNotNull(it.error)
                }
            }
        }
    }
}