package com.wework.zombiedev.data.repository


import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wework.zombiedev.Resource
import com.wework.zombiedev.data.local.dao.TrendingRepoDao
import com.wework.zombiedev.data.local.entity.TrendingRepoEntity
import com.wework.zombiedev.data.remote.api.Api
import com.wework.zombiedev.data.remote.repository.FetchTrendingRepository

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import retrofit2.Response
import org.mockito.Mockito



@RunWith(MockitoJUnitRunner::class)
class FetchTrendingRepositoryTest {

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

    private lateinit var repository: FetchTrendingRepository



    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        this.editor = Mockito.mock(SharedPreferences.Editor::class.java)
        this.sharedPref = Mockito.mock(SharedPreferences::class.java)
        this.ctx= Mockito.mock(Context::class.java)
        `when`(ctx.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPref)
        `when`(editor.putBoolean(anyString(), anyBoolean())).thenReturn(editor) // Also mock `putString`, `putFloat` and so on
        `when`(sharedPref.edit()).thenReturn(editor)
        repository = FetchTrendingRepository(githubDao, githubApiService,sharedPref)
    }

    @Test
    fun getRepositoriesTest() {
        `when`<Observable<Response<List<TrendingRepoEntity>>>>(githubApiService.fetchTrendingRepositories())
                .thenReturn(Observable.empty<Response<List<TrendingRepoEntity>>>())

        val data = repository.getRepositories()
        verify<Api>(githubApiService).fetchTrendingRepositories()
        val testObserver = TestObserver<Resource<List<TrendingRepoEntity>>>()
        data.subscribe(testObserver)
        testObserver.assertNoErrors()
    }

    @Test
    fun getDevsTest() {
        `when`<Observable<Response<List<TrendingRepoEntity>>>>(githubApiService.fetchTrendingRepositories())
            .thenReturn(Observable.empty<Response<List<TrendingRepoEntity>>>())

        val data = repository.getRepositories()
        verify<Api>(githubApiService).fetchTrendingRepositories()
        val testObserver = TestObserver<Resource<List<TrendingRepoEntity>>>()
        data.subscribe(testObserver)
        testObserver.assertNoErrors()
    }
}
