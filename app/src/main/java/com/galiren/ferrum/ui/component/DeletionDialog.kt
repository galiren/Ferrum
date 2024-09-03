package com.galiren.ferrum.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DeletionDialog(
  onDismissRequest: () -> Unit,
  onConfirmation: () -> Unit,
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Card(
      modifier = Modifier.fillMaxWidth()
        .height(200.dp)
        .padding(16.dp),
    ) {
      Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
          .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
      ) {
        Text(
          modifier = Modifier.padding(horizontal = 10.dp),
          text = "Delete this item?",
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
        )
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Absolute.Right,
        ) {
          Button(
            onClick = {
              onConfirmation()
            },
          ) {
            Text(text = "Yes")
          }
          Spacer(modifier = Modifier.padding(horizontal = 10.dp))
          Button(
            onClick = onDismissRequest,
          ) {
            Text(text = "No")
          }
        }
      }
    }
  }
}
