package com.example.companyhumanresources.data.remote

import com.example.companyhumanresources.data.OperationResult
import com.example.companyhumanresources.data.datasouces.RemoteDataSource

class RetrofitDatasource(private val service: HumanResourcesApiService) : RemoteDataSource {
    override suspend fun getEmployees(): OperationResult<GetEmployeesResponse> {
        return try {
            OperationResult.Success(service.getEmployees())
        } catch (error: Throwable) {
            OperationResult.Error(error)
        }
    }
}