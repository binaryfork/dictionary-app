package com.bf.dict.domain.network.yandex.mapper

import com.bf.dict.domain.network.yandex.response.TranslationResponse
import com.bf.dict.domain.repository.Translation

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function


class TranslationResponseMapper : Function<TranslationResponse, Translation> {

    @Throws(Exception::class)
    override fun apply(@NonNull translationResponse: TranslationResponse): Translation? {

        return null
    }
}
