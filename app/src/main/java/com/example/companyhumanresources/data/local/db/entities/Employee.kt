package com.example.companyhumanresources.data.local.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "employee_table")
data class Employee(
    @PrimaryKey
    val id: Long,
    val name: String,
    val position: String,
    val wage: Long,
    val isNew: Boolean = true,
) : Parcelable
