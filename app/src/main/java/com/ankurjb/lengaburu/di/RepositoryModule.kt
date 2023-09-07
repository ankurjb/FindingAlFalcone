package com.ankurjb.lengaburu.di

import com.ankurjb.lengaburu.repo.MainRepository
import com.ankurjb.lengaburu.repo.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMainRepository(repository: MainRepositoryImpl): MainRepository
}
