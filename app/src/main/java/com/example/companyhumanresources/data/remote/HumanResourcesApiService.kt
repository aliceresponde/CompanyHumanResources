package com.example.companyhumanresources.data.remote

import com.example.companyhumanresources.BuildConfig.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface HumanResourcesApiService {
    companion object {
        open fun invoke(interceptor: Interceptor): HumanResourcesApiService {
            val logInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(logInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HumanResourcesApiService::class.java)
        }
    }

    @GET("sapardo10/content/master/RH.json")
    suspend fun getEmployees(): GetEmployeesResponse
}