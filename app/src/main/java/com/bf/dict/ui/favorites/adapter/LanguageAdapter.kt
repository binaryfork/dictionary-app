package com.bf.dict.ui.favorites.adapter

import android.content.Context
import android.widget.ArrayAdapter

import com.bf.dict.domain.repository.Language


class LanguageAdapter(context: Context?, resource: Int, private val languages: List<Language>) : ArrayAdapter<Language>(context, resource, languages) {

    fun getPosition(language: String): Int {
        val notFound = -1

        for (index in languages.indices) {
            if (languages[index].language == language)
                return index
        }

        return notFound
    }
}
