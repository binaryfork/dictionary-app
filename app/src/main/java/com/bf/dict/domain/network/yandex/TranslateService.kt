package com.bf.dict.domain.network.yandex

import android.text.TextUtils

import com.bf.dict.domain.network.yandex.mapper.LanguageResponseMapper
import com.bf.dict.domain.network.yandex.mapper.SupportedLanguagesResponseMapper
import com.bf.dict.domain.repository.Language
import com.bf.dict.domain.repository.Translation

import io.reactivex.Single

class TranslateService(private val apiInterface: YandexTranslateApi,
                       private val languageResponseMapper: LanguageResponseMapper,
                       private val supportedLanguagesResponseMapper: SupportedLanguagesResponseMapper) {

    fun getLanguages(ui: String): Single<ArrayList<Language>> {
        return apiInterface
                .getLanguages(ui)
                .map(supportedLanguagesResponseMapper)
    }

    fun detectLanguage(text: String): Single<Language> {
        return apiInterface
                .detectLanguage(text)
                .map(languageResponseMapper)
    }

    fun translate(text: String, fromLanguage: String, toLanguage: String): Single<Translation> {
        val delimiter = "\n"
        val direction = "$fromLanguage-$toLanguage"
        return apiInterface.translate(text, direction)
                .map { translationResponse ->
                    val translatedText = TextUtils.join(delimiter, translationResponse.text)
                    Translation(0, text, translatedText, toLanguage, fromLanguage)
                }
    }

}
