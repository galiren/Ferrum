package com.galiren.ferrum.util

fun String.isLegalCost(): Boolean {
  var dotCounts = 0
  for (elem in this) {
    if (!elem.isDigit()) {
      if (elem != '.') {
        return false
      } else {
        dotCounts++
        if (dotCounts >= 2) return false
      }
    }
  }
  return true
}
