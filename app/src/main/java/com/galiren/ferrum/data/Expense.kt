package com.galiren.ferrum.data

data class Expense(
  val id: Int = -1,
  val year: String = "",
  val month: String = "",
  val day: String = "",
  val title: String = "",
  val cost: String,
)

fun List<Expense>.totalCost() = sumOf { it.cost.toDouble() }
