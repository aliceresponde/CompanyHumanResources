package com.example.companyhumanresources.data.local.db

import com.example.companyhumanresources.data.datasouces.LocalDataSource
import com.example.companyhumanresources.data.local.db.dao.EmployeeDao
import com.example.companyhumanresources.data.local.db.dao.SubordinateDao
import com.example.companyhumanresources.data.local.db.entities.Employee
import com.example.companyhumanresources.data.local.db.entities.Subordinate
import com.example.companyhumanresources.data.local.db.relations.EmployeeWithSubordinates
import kotlinx.coroutines.flow.Flow

class RoomDataSource(private val database: EmployeeDatabase) : LocalDataSource {
    private val employeeDao: EmployeeDao = database.employeeDao()
    private val subordinateDao: SubordinateDao = database.subordinateDao()

    override suspend fun insertEmployee(employee: Employee) {
        employeeDao.insertEmployee(employee)
    }

    override suspend fun insertAllEmployees(employees: List<Employee>) {
        employeeDao.insertAllEmployees(employees)
    }

    override suspend fun getAllEmployees(): List<Employee> {
        return employeeDao.getAllEmployees()
    }

    override suspend fun updateEmployee(employee: Employee) {
        employeeDao.insertEmployee(employee)
    }

    override suspend fun deleteAllEmployees() {
        employeeDao.deleteAll()
    }

    override suspend fun getEmployeeById(employeeId: Long): Employee {
        return employeeDao.getEmById(employeeId)
    }

    override suspend fun getNewEmployees(): List<Employee> {
        return employeeDao.getNewEmployees()
    }

    override suspend fun getEmployeesByWage(): List<Employee> {
        return  employeeDao.getEmployeeByName()
    }

    override suspend fun getNewEmployeesByName(name: String): List<Employee> {
        return employeeDao.getEmployeeByName(name)
    }

    override suspend fun getEmployeesBySalary(): List<Employee> {
        return employeeDao.getEmployeeByName()
    }

//    =================== Subordinates ===================

    override suspend fun insertSubordinate(subordinate: Subordinate) {
        subordinateDao.addSubordinate(subordinate)
    }

    override suspend fun insertAllSubordinates(subordinates: List<Subordinate>) {
        subordinateDao.insertAllSubordinates(subordinates)
    }

    override suspend fun getAllSubordinates(): List<Subordinate> {
        return subordinateDao.getAllSubordinates()
    }

    override suspend fun getAllSubordinatesByBossId(bossId: Long): List<Subordinate> {
        return subordinateDao.getSubordinatesByBoss(bossId)
    }

    override suspend fun deleteAllSubordinates() {
        subordinateDao.deleteAll()
    }

    override suspend fun deleteSubordinatedByBossId(bossId: Long) {
        subordinateDao.deleteByBossId(bossId)
    }

    override suspend fun deleteAllData() {
        subordinateDao.deleteAll()
    }
}