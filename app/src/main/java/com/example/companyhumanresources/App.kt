package com.example.companyhumanresources

import android.app.Application
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.companyhumanresources.data.local.db.EmployeeDatabase
import com.example.companyhumanresources.data.local.db.RoomDataSource
import com.example.companyhumanresources.data.remote.HumanResourcesApiService
import com.example.companyhumanresources.data.remote.NetworkConnection
import com.example.companyhumanresources.data.remote.NetworkConnectionInterceptor
import com.example.companyhumanresources.data.remote.RetrofitDatasource
import com.example.companyhumanresources.repository.EmployeeRepositoryImpl

@RequiresApi(Build.VERSION_CODES.M)
class App : Application() {

    companion object {
        private lateinit var instance: App
        private lateinit var networkStatusChecker: NetworkConnection
        private val interceptor by lazy { NetworkConnectionInterceptor(instance) }
        private val service by lazy { HumanResourcesApiService.invoke(interceptor) }
        private val database by lazy { EmployeeDatabase.buildDatabase(instance) }
        private val localDataSource by lazy {   RoomDataSource(database)}
        private val remoteDataSource by lazy {  RetrofitDatasource(service)}
        val repository by lazy { EmployeeRepositoryImpl(remoteDataSource, localDataSource) }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        networkStatusChecker = NetworkConnection(instance, getSystemService(ConnectivityManager::class.java))
    }

    fun getNetworkStatusChecker() = networkStatusChecker
}