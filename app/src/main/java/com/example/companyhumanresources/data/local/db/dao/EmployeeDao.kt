package com.example.companyhumanresources.data.local.db.dao

import androidx.room.*
import com.example.companyhumanresources.data.local.db.relations.EmployeeWithSubordinates
import com.example.companyhumanresources.data.local.db.entities.Employee
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee_table")
    fun getAllEmployeesFlow(): Flow<List<Employee>>

    @Query("SELECT * FROM employee_table")
    suspend fun getEmployeeWithSubordinates(): List<EmployeeWithSubordinates>

    @Query("SELECT * FROM employee_table")
    fun getAllEmployeeWithSubordinateFlow(): Flow<List<EmployeeWithSubordinates>>

    @Query("SELECT * FROM employee_table WHERE id = :bossId")
    suspend fun getEmployeeWithSubordinateById(bossId: Long): EmployeeWithSubordinates

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEmployees(employees: List<Employee>)

    @Delete
    suspend fun removeEmployee(employee: Employee)

    @Query("DELETE FROM employee_table")
    suspend fun deleteAll()
}