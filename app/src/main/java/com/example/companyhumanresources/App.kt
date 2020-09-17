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
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers

@HiltAndroidApp
class App : Application()