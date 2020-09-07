package com.example.companyhumanresources.data.remote

import com.google.gson.annotations.SerializedName


data class GetEmployeesResponse(
    @SerializedName( "company_name")
    val companyName: String,
    val address: String,
    val employees: List<EmployeeDTO>
)

data class EmployeeDTO(
    val id: Long,
    val name: String,
    val position: String,
    val wage: Long,
    @SerializedName("employees")
    val subordinates: List<SubordinateDTO>
)

data class SubordinateDTO(
    val id: Long,
    val name: String
)
