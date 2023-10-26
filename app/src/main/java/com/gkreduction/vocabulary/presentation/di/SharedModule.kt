package com.gkreduction.vocabulary.presentation.di

import android.app.Application
import com.gkreduction.vocabulary.data.repository.shared.SharedRepository
import com.gkreduction.vocabulary.data.repository.shared.SharedRepositoryImpl
import com.gkreduction.vocabulary.data.repository.shared.datasource.SharedDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {
    @Provides
    @Singleton
    fun provideSharedDataSource(app: Application) = SharedDataSource(app.applicationContext)


    @Provides
    @Singleton
    fun provideSharedRepositoryImpl(dataSource: SharedDataSource) =
        SharedRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideSharedRepository(repository: SharedRepositoryImpl): SharedRepository = repository

}