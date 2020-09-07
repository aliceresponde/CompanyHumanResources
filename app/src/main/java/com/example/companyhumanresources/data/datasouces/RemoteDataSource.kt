package com.example.companyhumanresources.data.datasouces

import com.example.companyhumanresources.data.OperationResult
import com.example.companyhumanresources.data.remote.GetEmployeesResponse

interface RemoteDataSource {
    suspend fun getEmployees(): OperationResult<GetEmployeesResponse>
}