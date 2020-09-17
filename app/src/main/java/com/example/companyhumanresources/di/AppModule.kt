package com.example.companyhumanresources.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.companyhumanresources.data.datasouces.LocalDataSource
import com.example.companyhumanresources.data.datasouces.RemoteDataSource
import com.example.companyhumanresources.data.local.db.EmployeeDatabase
import com.example.companyhumanresources.data.local.db.RoomDataSource
import com.example.companyhumanresources.data.local.db.dao.EmployeeDao
import com.example.companyhumanresources.data.local.db.dao.SubordinateDao
import com.example.companyhumanresources.data.remote.HumanResourcesApiService
import com.example.companyhumanresources.data.remote.NetworkConnectionInterceptor
import com.example.companyhumanresources.data.remote.RetrofitDatasource
import com.example.companyhumanresources.repository.EmployeeRepository
import com.example.companyhumanresources.repository.EmployeeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): EmployeeDatabase =
        Room.databaseBuilder(app, EmployeeDatabase::class.java, "database-name").build()

    @Provides
    @Singleton
    fun providesEmployeeDao(db: EmployeeDatabase) = db.employeeDao()

    @Provides
    @Singleton
    fun providesSubordinateDao(db: EmployeeDatabase) = db.subordinateDao()

    @Provides
    @Singleton
    fun provideNetworkConnectionInterceptor(@ApplicationContext context: Context): Interceptor =
        NetworkConnectionInterceptor(context)

    @Provides
    @Singleton
    fun provideApiService(interceptor: Interceptor) = HumanResourcesApiService.invoke(interceptor)
    //    ----------------------- Repository----------------------

//    @Provides
//    @Singleton
//    fun provideLocalDataSource(employeeDao: EmployeeDao, subordinateDao: SubordinateDao): LocalDataSource = RoomDataSource(employeeDao, subordinateDao)

    @Provides
    @Singleton
    fun provideLocalDataSource(db: EmployeeDatabase): LocalDataSource = RoomDataSource(db)


    @Provides
    @Singleton
    fun provideRemoteDataSource(service: HumanResourcesApiService): RemoteDataSource =
        RetrofitDatasource(service)

    @Provides
    @Singleton
    fun provideCounterRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): EmployeeRepository = EmployeeRepositoryImpl( remoteDataSource, localDataSource)

}