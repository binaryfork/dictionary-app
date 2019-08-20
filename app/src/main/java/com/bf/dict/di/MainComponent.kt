package com.bf.dict.di

import com.bf.dict.ui.favorites.FavoritesFragment
import com.bf.dict.ui.translate.TranslateFragment

import dagger.Subcomponent


@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    fun inject(mainFragment: TranslateFragment)

    fun inject(favoritesFragment: FavoritesFragment)

}
