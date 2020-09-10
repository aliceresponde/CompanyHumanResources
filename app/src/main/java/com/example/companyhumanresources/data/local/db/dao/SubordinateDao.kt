package com.example.companyhumanresources.data.local.db.dao

import androidx.room.*
import com.example.companyhumanresources.data.local.db.entities.Subordinate

@Dao
interface SubordinateDao {
    @Query("SELECT * FROM subordinate_table")
    suspend fun getAllSubordinates(): List<Subordinate>

    @Query("SELECT * FROM subordinate_table WHERE id  = :bossId")
    suspend fun getSubordinatesByBoss(bossId: Long): List<Subordinate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSubordinate(subordinate: Subordinate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSubordinates(subordinates: List<Subordinate>)

    @Delete
    suspend fun removeSubordinate(subordinate: Subordinate)

    @Query("DELETE FROM subordinate_table WHERE bossId = :bossId")
    suspend fun deleteByBossId(bossId: Long)

    @Query("DELETE FROM subordinate_table")
    suspend fun deleteAll()
}