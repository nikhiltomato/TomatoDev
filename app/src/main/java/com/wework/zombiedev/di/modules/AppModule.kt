package com.wework.zombiedev.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.wework.zombiedev.TrendingReposApp
import com.wework.zombiedev.common.values.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton @Provides
    fun provideSharedPref(context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
}