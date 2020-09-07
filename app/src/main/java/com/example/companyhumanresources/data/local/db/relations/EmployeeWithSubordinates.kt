package com.example.companyhumanresources.data.local.db.relations

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.example.companyhumanresources.data.local.db.entities.Employee
import com.example.companyhumanresources.data.local.db.entities.Subordinate
import kotlinx.android.parcel.Parcelize

/**
 * Relation: An  employee has many subordinates
 */

@Parcelize
data class EmployeeWithSubordinates(
    @Embedded
    val boss: Employee,
    @Relation(
        parentColumn = "id",
        entity = Subordinate::class,
        entityColumn = "bossId"
    )
    val subordinate: List<Subordinate>
): Parcelable
