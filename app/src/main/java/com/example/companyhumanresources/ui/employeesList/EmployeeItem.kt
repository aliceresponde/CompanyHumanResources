package com.example.companyhumanresources.ui.employeesList

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EmployeeItem (
    val id: Long,
    val name: String,
    val position:String,
    val wage: Long,
    val isNew : Boolean
): Parcelable