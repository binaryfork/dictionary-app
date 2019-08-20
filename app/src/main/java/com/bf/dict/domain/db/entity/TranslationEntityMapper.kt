package com.bf.dict.domain.db.entity

import com.bf.dict.domain.repository.Translation

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

class TranslationEntityMapper : Function<TranslationEntity, Translation> {
    @Throws(Exception::class)
    override fun apply(@NonNull translationEntity: TranslationEntity): Translation {
        return Translation(
                translationEntity.id,
                translationEntity.text,
                translationEntity.translated,
                translationEntity.fromLanguage,
                translationEntity.toLanguage)
    }
}
