package com.example.companyhumanresources.data.remote

import com.example.companyhumanresources.data.OperationResult
import com.example.companyhumanresources.data.OperationResult.Error
import com.example.companyhumanresources.data.OperationResult.Success
import com.example.companyhumanresources.data.datasouces.RemoteDataSource
import javax.inject.Inject

class RetrofitDatasource @Inject constructor(private val service: HumanResourcesApiService) : RemoteDataSource {
    override suspend fun getEmployees(): OperationResult<GetEmployeesResponse> {
        return try {
            Success(service.getEmployees())
        } catch (error: Throwable) {
            Error(error)
        }
    }
}