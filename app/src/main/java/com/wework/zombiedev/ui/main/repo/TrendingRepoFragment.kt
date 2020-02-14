package com.wework.zombiedev.ui.main.repo






import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wework.zombiedev.R
import com.wework.zombiedev.common.utils.getViewModel

import com.wework.zombiedev.common.utils.hide
import com.wework.zombiedev.common.utils.show
import com.wework.zombiedev.common.values.Constants
import com.wework.zombiedev.data.Status
import com.wework.zombiedev.data.local.entity.TrendingRepoEntity

import com.wework.zombiedev.di.factory.DaggerViewModelFactory

import com.wework.zombiedev.ui.main.TrendingRepoViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.error_lyt.*
import kotlinx.android.synthetic.main.trending_repo_frag_lyt.*
import javax.inject.Inject



class TrendingRepoFragment: Fragment() , TrendingRepoAdapter.OnItemClickListener {

    override fun onItemClicked(repoEntity: TrendingRepoEntity) {
            RepoDetailsActivity.startActivity(context!!,repoEntity.author!!,repoEntity.stars.toString(),repoEntity.description!!,repoEntity.forks.toString(),repoEntity.name!!)
    }


    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory
    private lateinit var viewModel: TrendingRepoViewModel
    private lateinit var mHomeAdapter: TrendingRepoAdapter
    private var shouldShowShimmer = true


    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.trending_repo_frag_lyt, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        initializeRecyclerView()
        setRetryListener()
        setPullToRefreshListener()
        viewModel.fetchTrendingRepos()
    }


    private fun setPullToRefreshListener(){
        swipe_rv.setOnRefreshListener {

            viewModel.fetchTrendingRepos(forceFetch = true)
        }
    }

    private fun setRetryListener(){
        retry_tv.setOnClickListener {
            shouldShowShimmer = true
            viewModel.fetchTrendingRepos(forceFetch = true)
        }
    }

    private fun observeViewModel(){
        viewModel = getViewModel(TrendingRepoViewModel::class.java, viewModelFactory)

        viewModel.getTrendingLiveData().observe(this, Observer { resource ->

            when(resource.status){
                Status.LOADING -> {
                    showLoadingView()
                }
                Status.SUCCESS -> {
                    handleRepoFetchSuccess()
                    resource.data?.let {
                        bindView(it)
                    }
                }
                Status.ERROR -> handleRepoFetchError()
            }
        })

    }

    private fun showLoadingView(){
        if(shouldShowShimmer){
            shimmer_rv.show()
            shimmer_rv.startShimmer()
        }
        trending_reporv.hide()
        shimmer_rv.startLayoutAnimation()
        main_error_lyt.hide()
    }

    private fun handleRepoFetchSuccess(){
        trending_reporv.show()
        shimmer_rv.stopShimmer()
        shouldShowShimmer = false
        swipe_rv.isRefreshing = false
        main_error_lyt.hide()
        shimmer_rv.hide()
    }

    private fun handleRepoFetchError(){
        shimmer_rv.stopShimmer()
        shimmer_rv.hide()
        swipe_rv.isRefreshing = false
        trending_reporv.hide()
        main_error_lyt.show()
    }

    private fun initializeRecyclerView(){
        val linearLayoutManager = LinearLayoutManager(activity)
        trending_reporv.layoutManager = linearLayoutManager
        mHomeAdapter = TrendingRepoAdapter(this)
        trending_reporv.adapter = mHomeAdapter
        val decorator = DividerItemDecoration( activity,LinearLayoutManager.VERTICAL)
        trending_reporv.addItemDecoration(decorator)
    }

     fun bindView(repoList : List<TrendingRepoEntity>){
        mHomeAdapter.updateData(repoList)
    }
}
