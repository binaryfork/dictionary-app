package com.bf.dict

import android.app.Application
import android.content.Context
import com.bf.dict.di.ApplicationComponent
import com.bf.dict.di.ApplicationModule
import com.bf.dict.di.DaggerApplicationComponent
import com.bf.dict.domain.network.NetworkModule


class TranslateApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .networkModule(NetworkModule())
                .build()
    }

    companion object {

        operator fun get(context: Context): TranslateApplication {
            return context.applicationContext as TranslateApplication
        }
    }
}
