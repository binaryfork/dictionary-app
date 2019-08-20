package com.bf.dict.domain.network.yandex.mapper

import com.bf.dict.domain.network.yandex.response.SupportedLanguagesResponse
import com.bf.dict.domain.repository.Language
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import java.util.*

class SupportedLanguagesResponseMapper : Function<SupportedLanguagesResponse, ArrayList<Language>> {
    @Throws(Exception::class)
    override fun apply(@NonNull response: SupportedLanguagesResponse): ArrayList<Language> {
        val result = ArrayList<Language>()

        for ((key, value) in response.langs) {
            result.add(Language(value, key))
        }

        return result
    }
}
