package com.example.companyhumanresources.repository

import com.example.companyhumanresources.data.local.db.entities.Employee
import com.example.companyhumanresources.data.local.db.relations.EmployeeWithSubordinates
import com.example.companyhumanresources.ui.employeesList.EmployeeItem
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    suspend fun syncData()
    suspend fun getEmployeeById(employeeId: Long): EmployeeItem
    suspend fun getAllEmployees(): List<EmployeeItem>
    suspend fun getNewEmployees(): List<EmployeeItem>
    suspend fun getEmployeesByWage(): List<EmployeeItem>
    suspend fun getNewEmployeesByName(name: String): List<EmployeeItem>
    suspend fun updateEmployee(employee: Employee)

    suspend fun getSubordinatesOf(bossId:Long) : List<String>

}