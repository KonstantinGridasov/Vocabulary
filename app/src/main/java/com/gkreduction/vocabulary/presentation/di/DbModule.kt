package com.gkreduction.vocabulary.presentation.di

import android.app.Application
import com.gkreduction.vocabulary.data.db.AppDataBase
import com.gkreduction.vocabulary.data.db.dao.IdiomDao
import com.gkreduction.vocabulary.data.db.dao.IrVerbDao
import com.gkreduction.vocabulary.data.db.dao.WordDao
import com.gkreduction.vocabulary.data.repository.db.DbRepository
import com.gkreduction.vocabulary.data.repository.db.DbRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provideAppDatabase(app: Application) = AppDataBase.getInstance(app.applicationContext)

    @Provides
    @Singleton
    fun provideIdiomDao(appDatabase: AppDataBase): IdiomDao = appDatabase.idiomDao()

    @Provides
    @Singleton
    fun provideWordDao(appDatabase: AppDataBase): WordDao = appDatabase.wordDao()

    @Provides
    @Singleton
    fun provideIrVerbDao(appDatabase: AppDataBase): IrVerbDao = appDatabase.irVerDao()

    @Provides
    @Singleton
    fun provideDbRepository(repository: DbRepositoryImpl): DbRepository = repository


    @Provides
    @Singleton
    fun provideDbRepositoryImpl(
        idiomDao: IdiomDao,
        wordDao: WordDao,
        irVerbDao: IrVerbDao

    ) = DbRepositoryImpl(idiomDao, wordDao, irVerbDao)


}