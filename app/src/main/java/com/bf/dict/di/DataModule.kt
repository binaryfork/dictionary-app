package com.bf.dict.di

import android.content.Context
import com.bf.dict.domain.db.AppDatabase
import com.bf.dict.domain.db.entity.TranslationEntityMapper
import com.bf.dict.domain.network.yandex.TranslateService
import com.bf.dict.domain.repository.FavoritesRepository
import com.bf.dict.domain.repository.TranslateRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule {

    @Provides
    @Singleton
    internal fun provideTranslationEntityMapper(): TranslationEntityMapper {
        return TranslationEntityMapper()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.invoke(context)
    }

    @Provides
    @Singleton
    internal fun provideFavoritesRepository(appDatabase: AppDatabase, translationEntityMapper: TranslationEntityMapper): FavoritesRepository {
        return FavoritesRepository(appDatabase, translationEntityMapper)
    }

    @Provides
    @Singleton
    internal fun provideTranslateRepository(translateService: TranslateService): TranslateRepository {
        return TranslateRepository(translateService)
    }

}
