package com.example.companyhumanresources.di

import com.example.companyhumanresources.repository.EmployeeRepository
import com.example.companyhumanresources.ui.emplogeeDetail.EmployeeDetailViewModel
import com.example.companyhumanresources.ui.employeesList.viewmodel.EmployeeListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object FragmentsModule {
    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun providesEmployeeDetailViewModel(repository: EmployeeRepository) = EmployeeDetailViewModel(repository)

    @Provides
    fun providesEmployeeListViewModel(repository: EmployeeRepository, coroutineDispatcher: CoroutineDispatcher) = EmployeeListViewModel(repository, coroutineDispatcher)
}