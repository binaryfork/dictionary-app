package com.bf.dict.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val appContext: Context) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return appContext
    }

}
