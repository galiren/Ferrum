package com.galiren.ferrum.ui.screen.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galiren.ferrum.data.Expense
import com.galiren.ferrum.data.ExpenseEntity
import com.galiren.ferrum.ui.screen.main.MainScreen.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
  private val uiStateFlow = MutableStateFlow(emptyMainUiState())
  val uiState: MainUiState = uiStateFlow.value
  private val uiEventFlow: MutableStateFlow<MainUiIntent> = MutableStateFlow(MainUiIntent.Init)

  init {
    viewModelScope.launch {
      // uiEvent
      uiEventFlow.collect { intent ->
        Log.d("ferrum-test", intent.toString())
        handleIntent(intent)
      }
    }
  }

  private suspend fun handleIntent(intent: MainUiIntent) {
    when (intent) {
      is MainUiIntent.Init -> {
        delay(500)
        uiStateFlow.value = uiStateFlow.value.copy(isLoading = false)
        uiEventFlow.value = MainUiIntent.Idle
      }
      is MainUiIntent.Idle -> {
        // empty
      }
      is MainUiIntent.OpenDialog -> {
      }
      is MainUiIntent.ExpenseDialogConfirm -> {
      }
      is MainUiIntent.OpenDeletionDialog -> {
      }
      is MainUiIntent.ItemDeletion -> {
      }
      is MainUiIntent.CloseExpenseDialog -> {
      }
      is MainUiIntent.CloseDeletionDialog -> {
      }
    }
  }
}

sealed interface MainUiIntent {
  data object Init : MainUiIntent
  data object Idle : MainUiIntent
  data class OpenDialog(val expense: Expense? = null) : MainUiIntent
  data class ExpenseDialogConfirm(val expenseEntity: ExpenseEntity) : MainUiIntent
  data class OpenDeletionDialog(val id: Int) : MainUiIntent
  data class ItemDeletion(val id: Int) : MainUiIntent
  data object CloseExpenseDialog : MainUiIntent
  data object CloseDeletionDialog : MainUiIntent
}

data class MainUiState(
  val isLoading: Boolean,
  val expenses: List<Expense> = emptyList(),
  val isShowExpenseDialog: Boolean = false,
  val isShowDeletionDialog: Boolean = false,
  val dialogData: Expense? = null,
  val deleteItemId: Int? = null,
  val eventSink: (Event) -> Unit = {},
)

private fun emptyMainUiState() =
  MainUiState(
    isLoading = false,
    expenses = emptyList(),
    isShowExpenseDialog = false,
    isShowDeletionDialog = false,
    dialogData = null,
    deleteItemId = null,
    eventSink = {},
  )
