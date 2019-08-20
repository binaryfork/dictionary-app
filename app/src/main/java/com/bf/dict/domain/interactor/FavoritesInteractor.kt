package com.bf.dict.domain.interactor

import com.bf.dict.domain.repository.FavoritesRepository
import com.bf.dict.domain.repository.Translation
import io.reactivex.Single
import java.util.*

class FavoritesInteractor(private val favoritesRepository: FavoritesRepository) {

    fun getFavorites(): Single<ArrayList<Translation>> {
        return favoritesRepository.getFavorites().map {
            it.reverse()
            return@map it
        }
    }

    fun delete(translation: Translation): Single<Int> {
        return favoritesRepository.delete(translation)
    }

    fun searchFavorite(query: String): Single<ArrayList<Translation>> {
        return favoritesRepository.searchFavorite(query)
    }

}
