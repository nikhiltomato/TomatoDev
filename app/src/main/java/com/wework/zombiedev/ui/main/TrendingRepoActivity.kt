package com.wework.zombiedev.ui.main


import android.content.res.Resources
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wework.zombiedev.R
import com.wework.zombiedev.common.utils.getViewModel
import com.wework.zombiedev.common.utils.hide
import com.wework.zombiedev.common.utils.show
import com.wework.zombiedev.data.Status
import com.wework.zombiedev.di.factory.DaggerViewModelFactory

import com.wework.zombiedev.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import androidx.viewpager.widget.ViewPager

import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.fragment.app.Fragment
import androidx.lifecycle.ReportFragment
import com.wework.zombiedev.ui.main.repo.TrendingRepoFragment


class TrendingRepoActivity: BaseActivity() {

    private lateinit var pagerAdaper: TrendingViewPagerAdapter

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory

    private lateinit var viewModel: TrendingRepoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
        setUpActionBar()
        setUpViewPager()
        setUpClickListeners()
    }

    private fun setUpClickListeners(){
        search_iv.setOnClickListener {
            if(search_et.isVisible && search_et.text.toString().isNotEmpty()){
                viewModel.searchRepoAndDevUi(search_et.text.toString())
                title_tv.show()
                search_et.hide()
                hideKeyboard()
            }else {
                title_tv.hide()
                search_et.show()
            }
        }
    }



    private fun setUpActionBar() {
        supportActionBar?.apply {
            title = ""
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(false)
            elevation = 2.0f * Resources.getSystem().displayMetrics.density
        }
    }


    private  fun setUpViewModel(){
        viewModel = getViewModel(TrendingRepoViewModel::class.java, viewModelFactory)

        viewModel.getTrendingLiveData().observe(this, Observer { resource ->

            when(resource.status){
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    resource.data?.let {
                        if(it.isNotEmpty()) {
                            val fragList: List<Fragment> = supportFragmentManager.fragments
                            val frag : TrendingRepoFragment ? = fragList[0] as TrendingRepoFragment
                            frag?.bindView(resource.data)
                        }
                    }
                }
                Status.ERROR -> {}
            }
        })
    }

    private fun setUpViewPager() {
        tab_layout.setupWithViewPager(pager)
        pagerAdaper = TrendingViewPagerAdapter(supportFragmentManager)
        pager.adapter = pagerAdaper

//        TabLayoutMediator(tab_layout, pager) { tab, position ->
//            when(position){
//                0 -> {
//                    tab.text = getString(R.string.repo_tab_heading)
//                }
//                1->{
//                    tab.text = getString(R.string.dev_tab_heading)
//                }
//            }
//
//        }.attach()
    }
}
