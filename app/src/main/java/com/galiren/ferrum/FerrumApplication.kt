package com.galiren.ferrum

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.galiren.ferrum.data.db.ExpenseDao
import com.galiren.ferrum.data.db.ExpenseDatabase

// todo may contains possible errors, going to refactor
class FerrumApplication : Application() {
  companion object {
    @Volatile
    private var instance: ExpenseDatabase? = null

    private fun getInstance(context: Context): ExpenseDatabase {
      return instance ?: synchronized(this) {
        instance ?: buildDatabase(context).also { instance = it }
      }
    }

    private fun buildDatabase(context: Context) =
      Room.databaseBuilder(context, ExpenseDatabase::class.java, "expenses.db").build()
  }
  val dao: ExpenseDao
    get() = getInstance(this).expenseDao()
}
