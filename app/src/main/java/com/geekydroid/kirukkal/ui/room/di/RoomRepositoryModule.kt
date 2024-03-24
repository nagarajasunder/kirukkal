package com.geekydroid.kirukkal.ui.room.di

import com.geekydroid.kirukkal.ui.room.repository.RoomRepository
import com.geekydroid.kirukkal.ui.room.repository.RoomRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RoomRepositoryModule {

    @Binds
    abstract fun provideRoomRepository(roomRepositoryImpl: RoomRepositoryImpl) : RoomRepository
}