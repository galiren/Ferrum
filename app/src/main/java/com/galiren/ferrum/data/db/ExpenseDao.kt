package com.galiren.ferrum.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.galiren.ferrum.data.ExpenseEntity
import kotlinx.coroutines.flow.Flow

// Why to use flow
@Dao
interface ExpenseDao {
  @Query("SELECT * FROM expenses")
  fun getAll(): Flow<List<ExpenseEntity>>

  @Query("SELECT * FROM expenses WHERE id = :id")
  fun getById(id: Int): Flow<ExpenseEntity>

  @Upsert
  suspend fun upsert(expense: ExpenseEntity)

  @Query("DELETE FROM expenses WHERE id = :id")
  suspend fun delete(id: Int)

  @Query("DELETE FROM expenses")
  suspend fun deleteAll()
}
