package com.bf.dict.domain.repository

import com.bf.dict.domain.db.AppDatabase
import com.bf.dict.domain.db.entity.TranslationEntity
import com.bf.dict.domain.db.entity.TranslationEntityMapper
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

class FavoritesRepository(private val appDatabase: AppDatabase, private val translationEntityMapper: TranslationEntityMapper) {

    fun getFavorites(): Single<ArrayList<Translation>> {
        return appDatabase.wordsDao().getAll()
                .map(this::mapToTranslation)
    }

    fun add(translation: Translation): Completable {
        return Completable
                .fromAction { appDatabase.wordsDao().insert(translation.mapToEntity()) }
    }

    fun delete(translation: Translation): Single<Int> {
        return Single.fromCallable { appDatabase.wordsDao().delete(translation.mapToEntity()) }
    }

    fun searchFavorite(query: String): Single<ArrayList<Translation>> {
        return appDatabase.wordsDao().findByTitle(query)
                .map(this::mapToTranslation)
    }

    @Throws(Exception::class)
    private fun mapToTranslation(translationEntities: List<TranslationEntity>): ArrayList<Translation> {
        val translations = ArrayList<Translation>()
        for (entity in translationEntities) {
            translations.add(translationEntityMapper.apply(entity))
        }
        return translations
    }
}
