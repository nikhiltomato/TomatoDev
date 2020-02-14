package com.wework.zombiedev

import android.app.Application
import com.wework.zombiedev.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class TrendingReposApp  : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out TrendingReposApp> {
        return DaggerAppComponent.builder().application(this).build()
    }


    override fun onCreate() { super.onCreate() }
}