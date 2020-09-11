package com.example.companyhumanresources.repository

import com.example.companyhumanresources.data.OperationResult.Error
import com.example.companyhumanresources.data.OperationResult.Success
import com.example.companyhumanresources.data.datasouces.LocalDataSource
import com.example.companyhumanresources.data.datasouces.RemoteDataSource
import com.example.companyhumanresources.data.local.db.entities.Employee
import com.example.companyhumanresources.data.local.db.entities.Subordinate
import com.example.companyhumanresources.data.local.db.relations.EmployeeWithSubordinates
import com.example.companyhumanresources.data.remote.EmployeeDTO
import com.example.companyhumanresources.ui.employeesList.EmployeeItem
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class EmployeeRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : EmployeeRepository {
    override suspend fun syncData() {
        val response = withContext(IO) {
            when (val remoteData = remoteDataSource.getEmployees()) {
                is Success -> {
                    val data = remoteData.data.employees
                    val entities = data.map { dto -> dto.toEmployee() }
                    localDataSource.insertAllEmployees(entities)
                    data.forEach { employeeDTO ->
                        insertSubordinatesOf(employeeDTO)
                    }
                }
                is Error -> {
                }
            }
        }
    }

    override suspend fun getEmployeeById(employeeId: Long): EmployeeItem {
        return localDataSource.getEmployeeById(employeeId).toItem()
    }

    override suspend fun getAllEmployees(): List<EmployeeItem> {
        return localDataSource.getAllEmployees().map { it.toItem() }
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

    override suspend fun getSubordinatesOf(bossId: Long): List<String> {
        return localDataSource.getAllSubordinatesByBossId(bossId).map { it.name }
    }

    override suspend fun getNewEmployees(): List<EmployeeItem> {
        return localDataSource.getNewEmployees().map { it.toItem() }
    }

    override suspend fun getEmployeesByWage(): List<EmployeeItem> {
        return localDataSource.getEmployeesByWage().map { it.toItem() }
    }

    override suspend fun getNewEmployeesByName(name: String): List<EmployeeItem> {
        return localDataSource.getNewEmployeesByName(name).map { it.toItem() }
    }

    private fun EmployeeDTO.toEmployee() = Employee(
        id = id,
        name = name,
        position = position,
        wage = wage,
        isNew = true
    )

    private fun Employee.toItem() = EmployeeItem(
        id = id,
        name = name,
        position = position,
        wage = wage,
        isNew = isNew
    )
}