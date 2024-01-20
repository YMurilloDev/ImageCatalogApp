package com.imagetimeline.di

import android.content.Context
import com.imagetimeline.data.local.daos.ImageDao
import com.imagetimeline.data.local.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase {
        return AppDataBase.buildDataBase(context)
    }

    @Provides
    @Singleton
    fun provideTransactionDao(dataBase: AppDataBase): ImageDao {
        return dataBase.imageDao()
    }
}