package com.dante.macrovia.di

import android.content.Context
import com.dante.macrovia.repository.SharedPrefsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPrefsRepository(
        @ApplicationContext context: Context
    ): SharedPrefsRepository {
        return SharedPrefsRepository(context)
    }
}