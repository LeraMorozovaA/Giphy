package com.giphy.di

import com.giphy.network.ApiService
import com.giphy.repository.GiphyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService): GiphyRepository = GiphyRepository(apiService)
}