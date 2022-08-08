package com.example.newsapitest.di

import com.example.newsapitest.repository.NewsRemoteData
import com.example.newsapitest.repository.NewsRemoteDataImpl
import com.example.newsapitest.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideNewsRepository(newsRemoteDataImpl: NewsRemoteDataImpl) : NewsRepository

}