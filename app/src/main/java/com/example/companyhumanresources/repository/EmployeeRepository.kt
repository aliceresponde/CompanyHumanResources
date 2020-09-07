package com.example.companyhumanresources.repository

import com.example.companyhumanresources.data.local.db.entities.Employee
import com.example.companyhumanresources.data.local.db.relations.EmployeeWithSubordinates
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    suspend fun getAllEmployeesFlow(): Flow<List<Employee>>
    suspend fun getAllEmployeesWithSubordinatesFlow(): Flow<List<EmployeeWithSubordinates>>
    suspend fun updateEmployee(employee: Employee)
}