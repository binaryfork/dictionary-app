package com.bf.dict.domain.network

import android.content.Context
import com.bf.dict.domain.network.yandex.TranslateService
import com.bf.dict.domain.network.yandex.YandexTranslateApi
import com.bf.dict.domain.network.yandex.mapper.LanguageResponseMapper
import com.bf.dict.domain.network.yandex.mapper.SupportedLanguagesResponseMapper
import com.bf.dict.domain.network.yandex.mapper.TranslationResponseMapper
import com.bf.dict.utils.ConnectivityUtils
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import javax.inject.Singleton


@Module
class NetworkModule {

    val BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/"

    @Provides
    @Singleton
    fun provideConnectivityUtil(context: Context): ConnectivityUtils {
        return ConnectivityUtils(context)
    }

    @Provides
    @Singleton
    fun provideSupportedLanguagesResponseMapper(): SupportedLanguagesResponseMapper {
        return SupportedLanguagesResponseMapper()
    }

    @Provides
    @Singleton
    fun provideTranslationResponseMapper(): TranslationResponseMapper {
        return TranslationResponseMapper()
    }

    @Provides
    @Singleton
    fun provideLanguageResponseMapper(): LanguageResponseMapper {
        return LanguageResponseMapper()
    }

    @Provides
    @Singleton
    fun provideTranslateService(apiInterface: YandexTranslateApi,
                                 languageResponseMapper: LanguageResponseMapper,
                                 supportedLanguagesResponseMapper: SupportedLanguagesResponseMapper): TranslateService {

        return TranslateService(apiInterface, languageResponseMapper, supportedLanguagesResponseMapper)
    }

    @Provides
    @Singleton
    fun provideYandexTranslateApi(context: Context): YandexTranslateApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val httpClient = OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor())
                .addInterceptor(interceptor)
                .build()

        val builder = Retrofit.Builder().baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        return builder.build()
                .create(YandexTranslateApi::class.java)
    }

    class ApiKeyInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url()
            val urlBuilder = originalUrl.newBuilder()
                    .addQueryParameter(
                            "key", "trnsl.1.1.20190716T120936Z.7ec0911932179dc8.d20647b90ea71b08767ffc6a9a72551a619d154a"
                    )
            val request = originalRequest.newBuilder()
                    .url(urlBuilder.build())
                    .build()

            return chain.proceed(request)
        }

    }

}
