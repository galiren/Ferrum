package com.galiren.ferrum.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galiren.ferrum.data.Expense

// todo Card Color
// todo onClick

@Composable
fun ExpenseItem(
  modifier: Modifier = Modifier,
  expense: Expense,
  onClick: () -> Unit,
  onItemLongPress: () -> Unit,
) {
  ExpenseItem(
    modifier = modifier,
    year = expense.year,
    month = expense.month,
    day = expense.day,
    title = expense.title,
    cost = expense.cost,
    onClick = onClick,
    onItemLongPress = onItemLongPress,
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ExpenseItem(
  modifier: Modifier = Modifier,
  year: String,
  month: String,
  day: String,
  title: String = "",
  cost: String,
  onClick: () -> Unit,
  onItemLongPress: () -> Unit,
) {
  Card(
    modifier = modifier.fillMaxWidth().padding(vertical = 5.dp, horizontal = 5.dp)
      .combinedClickable(
        onClick = onClick,
        onLongClick = onItemLongPress,
      ),
    colors = CardDefaults.cardColors(
//      containerColor = Color(0xFFEBEBF6),
    ),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp, horizontal = 10.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
      )
      Text(
        text = cost,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
      )
    }
  }
}
