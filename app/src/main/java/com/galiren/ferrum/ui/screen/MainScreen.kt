package com.galiren.ferrum.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galiren.ferrum.data.Expense
import com.galiren.ferrum.data.ExpenseEntity
import com.galiren.ferrum.data.totalCost
import com.galiren.ferrum.ui.component.DeletionDialog
import com.galiren.ferrum.ui.component.ExpenseDialog
import com.galiren.ferrum.ui.component.ExpenseItem
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object MainScreen : Screen {
  data class State(
    val isLoading: Boolean = false,
    val expenses: List<Expense> = emptyList(),
    val isShowExpenseDialog: Boolean = false,
    val isShowDeletionDialog: Boolean = false,
    val dialogData: Expense? = null,
    val deleteItemId: Int? = null,
    val eventSink: (Event) -> Unit = {},
  ) : CircuitUiState

  sealed interface Event : CircuitUiEvent {
    data class OpenDialog(val expense: Expense? = null) : Event
    data class DialogConfirm(val expenseEntity: ExpenseEntity) : Event
    data class OpenDeletionDialog(val id: Int) : Event
    data class ItemDeletion(val id: Int) : Event
    data object CloseExpenseDialog : Event
    data object CloseDeletionDialog : Event
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseList(
  modifier: Modifier = Modifier,
  state: MainScreen.State,
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    // todo navigation
    bottomBar = {
      NavigationBar {
        NavigationBarItem(
          selected = true,
          onClick = { /*TODO*/ },
          icon = {
            Icon(Icons.Default.Home, contentDescription = "Home")
          },
          label = {
            Text(
              text = "Home",
              fontWeight = FontWeight.Bold,
            )
          },
        )
        NavigationBarItem(
          selected = false,
          onClick = { /*TODO*/ },
          icon = {
            Icon(Icons.Default.Star, contentDescription = "Home")
          },
          label = {
            Text(
              text = "Summary",
              fontWeight = FontWeight.Bold,
            )
          },
        )
        NavigationBarItem(
          selected = false,
          onClick = { /*TODO*/ },
          icon = {
            Icon(Icons.Default.Settings, contentDescription = "Home")
          },
          label = {
            Text(
              text = "Configuration",
              fontWeight = FontWeight.Bold,
            )
          },
        )
      }
    },
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Text(
            text = "Ferrum",
            fontWeight = FontWeight.Bold,
          )
        },
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          state.eventSink(MainScreen.Event.OpenDialog(null))
        },
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "Add",
        )
      }
    },
  ) { paddingValues ->
    when (state.isLoading) {
      true -> {
        Column(
          modifier = modifier.fillMaxSize()
            .padding(paddingValues),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
        ) {
          CircularProgressIndicator()
        }
      }
      else -> {
        ExpenseList(
          modifier = modifier.padding(paddingValues),
          expenses = state.expenses,
          itemClick = {
            state.eventSink(MainScreen.Event.OpenDialog(it))
          },
          itemLongPress = {
            state.eventSink(MainScreen.Event.OpenDeletionDialog(it))
          },
          onExpenseDialogDismissRequest = {
            state.eventSink(MainScreen.Event.CloseExpenseDialog)
          },
          onDeletionDialogDismissRequest = {
            state.eventSink(MainScreen.Event.CloseDeletionDialog)
          },
          onConfirmation = {
            state.eventSink(MainScreen.Event.DialogConfirm(it))
          },
          itemDeleteAction = {
            state.eventSink(MainScreen.Event.ItemDeletion(it))
          },
          showExpenseDialog = state.isShowExpenseDialog,
          showDeletionDialog = state.isShowDeletionDialog,
          dialogData = state.dialogData,
          deleteItemId = state.deleteItemId,
        )
      }
    }
  }
}

@Composable
private fun ExpenseList(
  modifier: Modifier = Modifier,
  expenses: List<Expense> = emptyList(),
  itemClick: (Expense?) -> Unit,
  itemLongPress: (Int) -> Unit = {},
  itemDeleteAction: (Int) -> Unit,
  onExpenseDialogDismissRequest: () -> Unit,
  onDeletionDialogDismissRequest: () -> Unit,
  onConfirmation: (expense: ExpenseEntity) -> Unit,
  showExpenseDialog: Boolean,
  showDeletionDialog: Boolean,
  dialogData: Expense?,
  deleteItemId: Int?,
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Top,
  ) {
    if (showExpenseDialog) {
      ExpenseDialog(
        onDismissRequest = onExpenseDialogDismissRequest,
        onConfirmation = onConfirmation,
        data = dialogData,
      )
    } else if (showDeletionDialog) {
      DeletionDialog(
        onDismissRequest = onDeletionDialogDismissRequest,
        onConfirmation = {
          itemDeleteAction(deleteItemId!!)
        },
      )
    } else {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 2.dp, horizontal = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Text(
          text = "Total expenses: ",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
        )
        Text(
          // todo change it later!!!
          text = expenses.totalCost().toString(),
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
        )
      }
      Spacer(Modifier.padding(vertical = 10.dp))
      LazyColumn {
        items(expenses) { expense ->
          ExpenseItem(
            expense = expense,
            onClick = {
              itemClick(expense)
            },
            onItemLongPress = {
              itemLongPress(expense.id)
            },
          )
        }
      }
    }
  }
}
