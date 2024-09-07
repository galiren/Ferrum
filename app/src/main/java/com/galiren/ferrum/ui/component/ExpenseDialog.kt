package com.galiren.ferrum.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.galiren.ferrum.data.Expense
import com.galiren.ferrum.data.ExpenseEntity
import com.galiren.ferrum.util.isLegalCost

@Composable
fun ExpenseDialog(
  onDismissRequest: () -> Unit,
  onConfirmation: (expenseEntity: ExpenseEntity) -> Unit,
  data: Expense? = null,
) {
  Dialog(
    onDismissRequest = onDismissRequest,
  ) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .height(375.dp)
        .padding(16.dp),
    ) {
      var title by remember { mutableStateOf(data?.title ?: "") }
      var cost by remember { mutableStateOf(data?.cost ?: "") }
      Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
      ) {
        Text(
          text = if (data == null) "New Item" else "Edit Item",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
        )
      }
      Column {
        NewItem(
          label = "Title",
          value = title,
          onValueChange = {
            title = it
          },
        )
        NewItem(
          label = "Cost",
          value = cost,
          onValueChange = {
            cost = it
          },
        )
      }
      Spacer(Modifier.padding(vertical = 20.dp))
      Row(
        modifier = Modifier
          .padding(10.dp)
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
      ) {
        Button(
          onClick = {
            if (!cost.isLegalCost()) {
              return@Button
            }
            onConfirmation(
              if (data != null) {
                ExpenseEntity(
                  id = data.id,
                  title = title,
                  cost = cost.toDouble(),
                )
              } else {
                ExpenseEntity(
                  title = title,
                  cost = cost.toDouble(),
                )
              }
            )
          },
        ) {
          if (data !=  null) {
            Text(
              text = "Done",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
            )
          } else {
            Text(
              text = "Add",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
            )
          }

        }
        Spacer(modifier = Modifier.padding(horizontal = 10.dp))
        Button(
          onClick = {
            onDismissRequest()
          },
        ) {
          Text(
            text = "Cancel",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
          )
        }
      }
    }
  }
}

@Composable
private fun NewItem(
  label: String,
  paddingValues: PaddingValues = PaddingValues(16.dp),
  value: String,
  onValueChange: (String) -> Unit,
) {
  Row(
    modifier = Modifier.padding(paddingValues).fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      label = { Text(text = label) },
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun NewItemPreview() {
  NewItem(
    label = "title",
    paddingValues = PaddingValues(16.dp),
    value = "value",
    onValueChange = {},
  )
}

@Preview(showBackground = true)
@Composable
private fun ExpenseDialogPreview() {
  ExpenseDialog(
    onDismissRequest = {},
    onConfirmation = {},
  )
}
