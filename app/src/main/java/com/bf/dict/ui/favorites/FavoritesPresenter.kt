package com.bf.dict.ui.favorites

import com.bf.dict.domain.interactor.FavoritesInteractor
import com.bf.dict.domain.repository.Translation
import com.bf.dict.ui.base.BasePresenter
import java.util.*

class FavoritesPresenter(private var historyInteractor: FavoritesInteractor) : BasePresenter<FavoritesView>() {

    private val screenModel = FavoritesScreenModel()

    fun loadTranslations() {
        loadFavorites()
    }

    fun searhFavorite(query: String) {
        if (query.trim().isEmpty()) {
            loadFavorites()
        } else {
            subscribe(historyInteractor.searchFavorite(query),
                    this::handleSuccessLoadTranslations,
                    this::handleErrorLoadFavorites)
        }
    }

    fun deleteTranslation(translation: Translation) {
        subscribe(historyInteractor.delete(translation),
                { loadFavorites() },
                this::handleErrorLoadFavorites)
    }

    fun clickOnTranslation(translation: Translation) {
        view.openTranslation(translation)
    }

    private fun loadFavorites() {
        subscribe(historyInteractor.getFavorites(),
                this::handleSuccessLoadTranslations,
                this::handleErrorLoadFavorites)
    }

    private fun handleSuccessLoadTranslations(translations: ArrayList<Translation>) {
        screenModel.translations = translations
        view.renderView(screenModel)
    }

    private fun handleErrorLoadFavorites(throwable: Throwable) {
        view.showError(throwable.localizedMessage)
    }
}
