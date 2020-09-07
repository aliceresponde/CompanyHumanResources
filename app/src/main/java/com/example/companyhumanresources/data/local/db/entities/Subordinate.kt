package com.example.companyhumanresources.data.local.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "subordinate_table")
data class Subordinate(
    @PrimaryKey
    val id: Long,
    val name: String,
    val bossId: Long
) : Parcelable