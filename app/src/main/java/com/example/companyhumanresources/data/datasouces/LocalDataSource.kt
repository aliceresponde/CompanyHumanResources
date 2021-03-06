package com.example.companyhumanresources.data.datasouces

import com.example.companyhumanresources.data.local.db.entities.Employee
import com.example.companyhumanresources.data.local.db.entities.Subordinate
import com.example.companyhumanresources.data.local.db.relations.EmployeeWithSubordinates
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    // Employee
    suspend fun insertEmployee(employee: Employee)
    suspend fun insertAllEmployees(employees: List<Employee>)
    suspend fun getAllEmployees() : List<Employee>
    suspend fun updateEmployee(employee: Employee)
    suspend fun deleteAllEmployees()

    suspend fun getEmployeeById(employeeId: Long) : Employee
    suspend fun getNewEmployees() : List<Employee>
    suspend fun getEmployeesByWage() : List<Employee>
    suspend fun getNewEmployeesByName(name: String) : List<Employee>

    // subordinates
    suspend fun insertSubordinate(subordinate: Subordinate)
    suspend fun insertAllSubordinates(subordinates: List<Subordinate>)
    suspend fun getAllSubordinates(): List<Subordinate>
    suspend fun getAllSubordinatesByBossId(bossId: Long): List<Subordinate>
    suspend fun deleteAllSubordinates()
    suspend fun deleteSubordinatedByBossId(bossId: Long)

    suspend fun deleteAllData()
    suspend fun getEmployeesBySalary(): List<Employee>
}