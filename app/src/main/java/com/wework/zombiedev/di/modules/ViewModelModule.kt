package com.wework.zombiedev.di.modules


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wework.zombiedev.di.factory.DaggerViewModelFactory
import com.wework.zombiedev.di.scopes.ViewModelKey
import com.wework.zombiedev.ui.main.TrendingRepoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TrendingRepoViewModel::class)
    internal abstract fun trendingRepoViewModel(viewModel: TrendingRepoViewModel): ViewModel

}