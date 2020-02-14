package com.wework.zombiedev.ui.main


import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.wework.zombiedev.R
import com.wework.zombiedev.common.values.Constants.DEV_STRING
import com.wework.zombiedev.common.values.Constants.REPO_STRING

import com.wework.zombiedev.ui.main.developers.TrendingDevFragment
import com.wework.zombiedev.ui.main.repo.TrendingRepoFragment

class TrendingViewPagerAdapter(val fmgr : FragmentManager) : FragmentStatePagerAdapter(fmgr) {

    override fun getItem(position: Int): Fragment {

            var fragment : Fragment? = null
            when(position){
                0 -> {
                    fragment = TrendingRepoFragment()

                }
                1 -> {
                    fragment = TrendingDevFragment()
                }
            }
            return fragment!!

    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> {
                return REPO_STRING

            }
            1 -> {
                return DEV_STRING
            }
        }
        return super.getPageTitle(position)
    }


}
