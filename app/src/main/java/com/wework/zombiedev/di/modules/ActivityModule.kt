package com.wework.zombiedev.di.modules

import com.wework.zombiedev.ui.main.TrendingRepoActivity
import com.wework.zombiedev.ui.main.developers.TrendingDevFragment
import com.wework.zombiedev.ui.main.repo.RepoDetailsActivity
import com.wework.zombiedev.ui.main.repo.TrendingRepoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeProductListActivity(): TrendingRepoActivity

    @ContributesAndroidInjector
    internal abstract fun contributeRepoDetailsActivity(): RepoDetailsActivity

    @ContributesAndroidInjector
    internal abstract fun contributeTrendingRepoFragment(): TrendingRepoFragment

    @ContributesAndroidInjector
    internal abstract fun contributeTrendingDevFragment(): TrendingDevFragment

}