package com.bf.dict.di

import com.bf.dict.domain.interactor.FavoritesInteractor
import com.bf.dict.domain.interactor.TranslateInteractor
import com.bf.dict.domain.repository.FavoritesRepository
import com.bf.dict.domain.repository.TranslateRepository
import com.bf.dict.ui.favorites.FavoritesPresenter
import com.bf.dict.ui.translate.TranslatePresenter
import com.bf.dict.utils.ConnectivityUtils
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideTranslateInteractor(translationRepository: TranslateRepository,
                               iHistoryRepository: FavoritesRepository,
                               connectivityUtils: ConnectivityUtils): TranslateInteractor {
        return TranslateInteractor(translationRepository, iHistoryRepository, connectivityUtils)
    }

    @Provides
    fun provideTranslatePresenter(mainInteractor: TranslateInteractor, connectivityUtils: ConnectivityUtils): TranslatePresenter {
        return TranslatePresenter(mainInteractor, connectivityUtils)
    }

    @Provides
    fun provideFavoritesInteractor(historyRepository: FavoritesRepository): FavoritesInteractor {
        return FavoritesInteractor(historyRepository)
    }

    @Provides
    fun provideFavoritesPresenter(historyInteractor: FavoritesInteractor): FavoritesPresenter {
        return FavoritesPresenter(historyInteractor)
    }
}
