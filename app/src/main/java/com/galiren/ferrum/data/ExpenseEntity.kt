package com.galiren.ferrum.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @ColumnInfo(name = "year") val year: String = "",
  @ColumnInfo(name = "month") val month: Int = -1,
  @ColumnInfo(name = "day") val day: Int = -1,
  @ColumnInfo(name = "title") val title: String = "",
  @ColumnInfo(name = "cost") val cost: Double,
)

fun ExpenseEntity.toExpense() =
  Expense(
    id = id,
    year = year,
    month = month.toString(),
    day = day.toString(),
    title = title,
    cost = cost.toString(),
  )

fun List<ExpenseEntity>.toExpenseList() =
  map { it.toExpense() }
