package com.example.companyhumanresources.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.companyhumanresources.data.local.db.dao.EmployeeDao
import com.example.companyhumanresources.data.local.db.dao.SubordinateDao
import com.example.companyhumanresources.data.local.db.entities.Employee
import com.example.companyhumanresources.data.local.db.entities.Subordinate

const val DATABASE_VERSION = 1

@Database(entities = [Employee::class, Subordinate::class], version = DATABASE_VERSION)
abstract class EmployeeDatabase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "Employees-db"
        fun buildDatabase(context: Context): EmployeeDatabase {
            return Room.databaseBuilder(
                context,
                EmployeeDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }

    abstract fun employeeDao(): EmployeeDao
    abstract fun subordinateDao(): SubordinateDao
}