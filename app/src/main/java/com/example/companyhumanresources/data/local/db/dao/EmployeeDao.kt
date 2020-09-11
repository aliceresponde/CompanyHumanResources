package com.example.companyhumanresources.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.companyhumanresources.data.local.db.entities.Employee
import com.example.companyhumanresources.data.local.db.relations.EmployeeWithSubordinates
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee_table")
    fun getAllEmployees():List<Employee>

    @Query("SELECT * FROM employee_table WHERE id = :employeeId")
    fun getEmById(employeeId: Long) : Employee

    @Query("SELECT * FROM employee_table ORDER BY wage")
    suspend fun getEmployeeByName(): List<Employee>

    @Query("SELECT * FROM employee_table WHERE name LIKE '%' || :name || '%'")
    suspend fun getEmployeeByName(name:String): List<Employee>

    @Query("SELECT * FROM employee_table WHERE isNew = 1")
    suspend fun getNewEmployees(): List<Employee>

    @Query("SELECT * FROM employee_table WHERE id = :bossId")
    suspend fun getEmployeeWithSubordinateById(bossId: Long): EmployeeWithSubordinates

    @Insert(onConflict = REPLACE)
    suspend fun insertEmployee(employee: Employee)

    @Update(onConflict = REPLACE)
    suspend fun updateEmployee(employee: Employee)

    @Insert(onConflict = IGNORE)
    suspend fun insertAllEmployees(employees: List<Employee>)

    @Query("DELETE FROM employee_table")
    suspend fun deleteAll()
}
