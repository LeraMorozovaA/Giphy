package com.giphy.di

import com.giphy.network.ApiService
import com.giphy.network.ApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "https://api.giphy.com/v1/"
    private const val API_KEY = "1yIMTW5O73I9UZc3hh49SbNltvellX2x"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

       return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            addInterceptor(logging)
            addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val requestBuilder = original.newBuilder().url(url)

                val request = requestBuilder.build()
                chain.proceed(request)
            }
        }.build()
    }

//    @Singleton
//    @Provides
//    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
//        val retrofit = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BASE_URL)
//            .client(okHttpClient)
//            .build()
//
//        return retrofit.create(ApiService::class.java)
//    }

    @Singleton
    @Provides
    fun provideApiServiceImpl(okHttpClient: OkHttpClient): ApiService = ApiServiceImpl()

}