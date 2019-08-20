package com.bf.dict.domain.repository

import com.bf.dict.domain.db.entity.TranslationEntity

class Translation(var id: Long, var text: String, var translated: String, var fromLanguage: String, var toLanguage: String) {

    val direction: String = fromLanguage + "\n" + toLanguage

    fun mapToEntity(): TranslationEntity {
        return TranslationEntity(id, text, translated, fromLanguage, toLanguage)
    }

}
