package com.giphy.di

import com.giphy.api.ApiService
import com.giphy.data.AppDatabase
import com.giphy.data.local.LocalStorageService
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
    fun provideRepository(apiService: ApiService, db: AppDatabase, localStorageService: LocalStorageService)
    : GiphyRepository = GiphyRepository(apiService, db, localStorageService)
}