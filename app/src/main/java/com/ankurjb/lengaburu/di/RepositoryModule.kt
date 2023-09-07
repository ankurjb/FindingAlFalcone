package com.ankurjb.lengaburu.di

import com.ankurjb.lengaburu.repo.VehiclesRepository
import com.ankurjb.lengaburu.repo.VehiclesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindVehicleRepository(repository: VehiclesRepositoryImpl): VehiclesRepository
}
