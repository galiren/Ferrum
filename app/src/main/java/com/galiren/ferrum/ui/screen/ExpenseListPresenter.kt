package com.galiren.ferrum.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.galiren.ferrum.data.Expense
import com.galiren.ferrum.data.db.ExpenseDao
import com.galiren.ferrum.data.toExpenseList
import com.slack.circuit.runtime.presenter.Presenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExpenseListPresenter(private val expenseDao: ExpenseDao, private val scope: CoroutineScope) : Presenter<MainScreen.State> {
  private var expenses = expenseDao.getAll()
    .flowOn(Dispatchers.IO)
    .stateIn(
      scope,
      SharingStarted.WhileSubscribed(5000),
      emptyList(),
    )

  @Composable
  override fun present(): MainScreen.State {
    var isLoading by remember { mutableStateOf(true) }
    val expenses = expenses
      .map {
        it.toExpenseList()
      }
      .collectAsStateWithLifecycle(
        initialValue = emptyList(),
      )
    var isShowExpenseDialog by remember { mutableStateOf(false) }
    var isShowDeletionDialog by remember { mutableStateOf(false) }
    var dialogData by remember { mutableStateOf<Expense?>(null) }
    var deleteItemId by remember { mutableStateOf<Int?>(null) }
    return MainScreen.State(
      isLoading = isLoading,
      expenses = expenses.value,
      isShowExpenseDialog = isShowExpenseDialog,
      isShowDeletionDialog = isShowDeletionDialog,
      dialogData = dialogData,
      deleteItemId = deleteItemId,
    ) { event ->
      when (event) {
        is MainScreen.Event.Init -> {
          scope.launch(Dispatchers.IO) {
            // wait for 2 seconds
            delay(2000)
            isLoading = false
          }
        }
        is MainScreen.Event.OpenDialog -> {
          // todo open item details
          dialogData = event.expense
          isShowExpenseDialog = true
        }

        is MainScreen.Event.OpenDeletionDialog -> {
          deleteItemId = event.id
          isShowDeletionDialog = true
        }

        is MainScreen.Event.ItemDeletion -> {
          scope.launch(Dispatchers.IO) {
            expenseDao.delete(event.id)
            isShowDeletionDialog = false
          }
        }
        is MainScreen.Event.ExpenseDialogConfirm -> {
          isShowExpenseDialog = false
          isLoading = true
          scope.launch(Dispatchers.IO) {
            delay(1000)
            expenseDao.upsert(event.expenseEntity)
            isLoading = false
          }
        }
        is MainScreen.Event.CloseExpenseDialog -> {
          isShowExpenseDialog = false
          dialogData = null
        }
        is MainScreen.Event.CloseDeletionDialog -> {
          isShowDeletionDialog = false
          deleteItemId = null
        }
      }
    }
  }
}
