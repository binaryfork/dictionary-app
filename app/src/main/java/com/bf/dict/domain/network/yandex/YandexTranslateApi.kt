package com.bf.dict.domain.network.yandex


import com.bf.dict.domain.network.yandex.response.LanguageResponse
import com.bf.dict.domain.network.yandex.response.SupportedLanguagesResponse
import com.bf.dict.domain.network.yandex.response.TranslationResponse

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface YandexTranslateApi {

    @GET("getLangs")
    fun getLanguages(@Query("ui") ui: String): Single<SupportedLanguagesResponse>

    @POST("detect")
    fun detectLanguage(@Body text: String): Single<LanguageResponse>

    @GET("translate")
    fun translate(@Query("text") text: String,
                  @Query("lang") translateDirection: String): Single<TranslationResponse>

}
