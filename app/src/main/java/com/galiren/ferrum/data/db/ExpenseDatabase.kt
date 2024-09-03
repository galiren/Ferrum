package com.galiren.ferrum.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.galiren.ferrum.data.ExpenseEntity

@Database(entities = [ExpenseEntity::class], version = 1)
abstract class ExpenseDatabase : RoomDatabase() {
  abstract fun expenseDao(): ExpenseDao
}
