package com.example.chat.di

import com.example.chat.data.repository.MessageRepositoryImpl
import com.example.chat.domain.repository.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface MessageRepositoryModule {

    @Binds
    fun bindMessageRepository(impl: MessageRepositoryImpl): MessageRepository
}