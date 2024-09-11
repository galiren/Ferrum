package com.galiren.ferrum.ui

import com.galiren.ferrum.FerrumApplication
import com.galiren.ferrum.ui.screen.main.ExpenseListPresenter
import com.galiren.ferrum.ui.screen.main.MainScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.CoroutineScope

class FerrumFactory(private val application: FerrumApplication, private val scope: CoroutineScope) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is MainScreen -> ExpenseListPresenter(application.dao)
      else -> null
    }
  }
}
