package com.example.companyhumanresources.repository

import com.example.companyhumanresources.data.local.db.entities.Employee
import com.example.companyhumanresources.data.local.db.relations.EmployeeWithSubordinates
import com.example.companyhumanresources.ui.employeesList.EmployeeItem
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    fun getAllEmployeesFlow(): Flow<List<EmployeeItem>>
    fun getAllEmployeesWithSubordinatesFlow(): Flow<List<EmployeeWithSubordinates>>
    suspend fun syncData()
    suspend fun getAllEmployees(): List<EmployeeItem>
    suspend fun getNewEmployees(): List<EmployeeItem>
    suspend fun getEmployeesByWage(): List<EmployeeItem>
    suspend fun getNewEmployeesByName(name: String): List<EmployeeItem>
    suspend fun getEmployeesBySalary(): List<EmployeeItem>
    suspend fun updateEmployee(employee: Employee)
}