package com.example.companyhumanresources.repository

import com.example.companyhumanresources.data.OperationResult.Error
import com.example.companyhumanresources.data.OperationResult.Success
import com.example.companyhumanresources.data.datasouces.LocalDataSource
import com.example.companyhumanresources.data.datasouces.RemoteDataSource
import com.example.companyhumanresources.data.local.db.entities.Employee
import com.example.companyhumanresources.data.local.db.entities.Subordinate
import com.example.companyhumanresources.data.local.db.relations.EmployeeWithSubordinates
import com.example.companyhumanresources.data.remote.EmployeeDTO
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class EmployeeRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : EmployeeRepository {

    override suspend fun getAllEmployeesFlow(): Flow<List<Employee>> {
        return withContext(IO) {
            val response = remoteDataSource.getEmployees()
            when (response) {
                is Success -> {
                    val data = response.data.employees
                    val nEmployees = data.map { it.toEmployee() }
                    localDataSource.insertAllEmployees(nEmployees)
                    data.forEach { employeeDTO -> insertSubordinatesOf(employeeDTO) }
                    localDataSource.getAllEmployeesFlow()
                }
                is Error -> localDataSource.getAllEmployeesFlow()
            }
        }
    }

    override suspend fun getAllEmployeesWithSubordinatesFlow(): Flow<List<EmployeeWithSubordinates>> {
        return localDataSource.getAllEmployeeWithSubordinateFlow()
    }

    private suspend fun insertSubordinatesOf(employeeDTO: EmployeeDTO) {
        employeeDTO.subordinates.forEach { subordinateDTO ->
            localDataSource.insertSubordinate(
                Subordinate(
                    id = subordinateDTO.id,
                    name = subordinateDTO.name,
                    bossId = employeeDTO.id
                )
            )
        }
    }

    override suspend fun updateEmployee(employee: Employee) {
        localDataSource.updateEmployee(employee)
    }

    private fun EmployeeDTO.toEmployee() = Employee(
        id = id,
        name = name,
        position = position,
        wage = wage,
        isNew = true
    )
}