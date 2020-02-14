package com.wework.zombiedev.di.component

import android.content.Context
import com.wework.zombiedev.TrendingReposApp
import com.wework.zombiedev.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Singleton



@Singleton
@Component(modules = [AndroidInjectionModule::class, ActivityModule::class, ViewModelModule::class , DatabaseModule::class , NetModule::class , AppModule::class])

interface AppComponent : AndroidInjector<TrendingReposApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(context: Context): Builder

        fun build(): AppComponent
    }
}