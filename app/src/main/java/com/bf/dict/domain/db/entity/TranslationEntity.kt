package com.bf.dict.domain.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class TranslationEntity(@PrimaryKey(autoGenerate = true) val id: Long,
                        val text: String,
                        val translated: String,
                        val fromLanguage: String,
                        val toLanguage: String)