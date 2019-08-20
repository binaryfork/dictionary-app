package com.bf.dict.domain.db

import androidx.room.*
import com.bf.dict.domain.db.entity.TranslationEntity
import io.reactivex.Single

@Dao
interface TranslationDao {

    @Query("SELECT * FROM translationentity")
    fun getAll(): Single<List<TranslationEntity>>

    @Query("SELECT * FROM translationentity WHERE id = :id")
    fun findById(id: Long): TranslationEntity

    @Query("SELECT * FROM translationentity WHERE text LIKE :text || '%' OR translated LIKE :text || '%'" )
    fun findByTitle(text: String): Single<List<TranslationEntity>>

    @Insert
    fun insert(data: TranslationEntity)

    @Delete
    fun delete(data: TranslationEntity): Int

    @Update
    fun updateAlarm(data: TranslationEntity)
}