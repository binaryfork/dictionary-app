package com.bf.dict.di

import com.bf.dict.domain.network.NetworkModule
import dagger.Component
import javax.inject.Singleton


@Component(modules = [ApplicationModule::class, NetworkModule::class, DataModule::class])
@Singleton
interface ApplicationComponent {

    fun mainComponent(mainModule: MainModule): MainComponent
}
