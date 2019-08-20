package com.bf.dict.domain.interactor


import com.bf.dict.domain.exception.NoInternetConnectionException
import com.bf.dict.domain.repository.FavoritesRepository
import com.bf.dict.domain.repository.Language
import com.bf.dict.domain.repository.TranslateRepository
import com.bf.dict.domain.repository.Translation
import com.bf.dict.utils.ConnectivityUtils
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*


class TranslateInteractor(private val translationRepository: TranslateRepository,
                          private val historyRepository: FavoritesRepository,
                          private val connectivityUtils: ConnectivityUtils) {

    fun loadLanguages(): Single<ArrayList<Language>> {
        return translationRepository
                .getLanguages(Locale.getDefault().language)
                .map(this::sortLanguages)
    }

    fun translateText(text: String, fromLanguage: String, toLanguage: String): Single<Translation> {
        return isOnline().flatMap { translationRepository.getTranslation(text, fromLanguage, toLanguage) }
    }

    fun saveTranslation(translation: Translation): Completable {
        return historyRepository.add(translation)
    }

    private fun isOnline(): Single<Boolean> {
        return Single.fromCallable {
            if (connectivityUtils.isOnline)
                true
            else
                throw NoInternetConnectionException()
        }
    }

    private fun sortLanguages(languages: MutableList<Language>): ArrayList<Language> {
        languages.sortWith(Comparator { language1, language2 -> language1.title.compareTo(language2.title) })
        return languages as ArrayList<Language>
    }

}
