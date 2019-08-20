package com.bf.dict.domain.repository

import com.bf.dict.domain.network.yandex.TranslateService

import io.reactivex.Single

class TranslateRepository(private val translateService: TranslateService) {

     fun getTranslation(text: String, fromLanguage: String, toLanguage: String): Single<Translation> {
        return translateService.translate(text, fromLanguage, toLanguage)
    }

     fun getLanguages(userInterface: String): Single<ArrayList<Language>> {
        return translateService.getLanguages(userInterface)
    }
}
