package com.bf.dict.domain.network.yandex.mapper

import com.bf.dict.domain.network.yandex.response.LanguageResponse
import com.bf.dict.domain.repository.Language

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function


class LanguageResponseMapper : Function<LanguageResponse, Language> {

    @Throws(Exception::class)
    override fun apply(@NonNull response: LanguageResponse): Language {
        return Language(EMPTY_STRING, response.lang ?: "en")
    }

    companion object {

        private val EMPTY_STRING = ""
    }
}
