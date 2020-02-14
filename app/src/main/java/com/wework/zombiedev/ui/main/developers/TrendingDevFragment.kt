package com.wework.zombiedev.ui.main.developers






import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wework.zombiedev.R
import com.wework.zombiedev.common.utils.getViewModel

import com.wework.zombiedev.common.utils.hide
import com.wework.zombiedev.common.utils.show
import com.wework.zombiedev.common.values.Constants
import com.wework.zombiedev.data.Status
import com.wework.zombiedev.data.local.entity.TrendingDevEntity
import com.wework.zombiedev.data.local.entity.TrendingRepoEntity

import com.wework.zombiedev.di.factory.DaggerViewModelFactory

import com.wework.zombiedev.ui.main.TrendingRepoViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.error_lyt.*
import kotlinx.android.synthetic.main.trending_dev_frag_lyt.*
import kotlinx.android.synthetic.main.trending_repo_frag_lyt.main_error_lyt
import kotlinx.android.synthetic.main.trending_repo_frag_lyt.shimmer_rv
import kotlinx.android.synthetic.main.trending_repo_frag_lyt.swipe_rv
import javax.inject.Inject



class TrendingDevFragment: Fragment() {


    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory
    private lateinit var viewModel: TrendingRepoViewModel
    private lateinit var mHomeAdapter: TrendingDevAdapter
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
        return inflater.inflate(R.layout.trending_dev_frag_lyt, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initializeRecyclerView()
        setRetryListener()
        setPullToRefreshListener()

        viewModel.fetchTrendingDevs()
    }

    private fun setPullToRefreshListener(){
        swipe_rv.setOnRefreshListener {

            viewModel.fetchTrendingDevs(forceFetch = true)
        }
    }

    private fun setRetryListener(){
        retry_tv.setOnClickListener {
            shouldShowShimmer = true
            viewModel.fetchTrendingDevs(forceFetch = true)
        }
    }

    private fun observeViewModel(){
        viewModel = getViewModel(TrendingRepoViewModel::class.java, viewModelFactory)

        viewModel.getTrendingDevsLiveData().observe(this, Observer { resource ->

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
        trending_devrv.hide()
        shimmer_rv.startLayoutAnimation()
        main_error_lyt.hide()
    }

    private fun handleRepoFetchSuccess(){
        trending_devrv.show()
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
        trending_devrv.hide()
        main_error_lyt.show()
    }

    private fun initializeRecyclerView(){
        val linearLayoutManager = LinearLayoutManager(activity)
        trending_devrv.layoutManager = linearLayoutManager
        mHomeAdapter = TrendingDevAdapter()
        trending_devrv.adapter = mHomeAdapter
        val decorator = DividerItemDecoration( activity,LinearLayoutManager.VERTICAL)
        trending_devrv.addItemDecoration(decorator)
    }

    private fun bindView(repoList : List<TrendingDevEntity>){
        mHomeAdapter.updateData(repoList)
    }
}
