package com.example.auth.di

import com.example.auth.repository.AuthRepository
import com.example.auth.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindRepository(impl: AuthRepositoryImpl): AuthRepository
}