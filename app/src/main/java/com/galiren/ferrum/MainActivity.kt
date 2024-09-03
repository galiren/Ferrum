package com.galiren.ferrum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.galiren.ferrum.ui.FerrumFactory
import com.galiren.ferrum.ui.screen.ExpenseList
import com.galiren.ferrum.ui.screen.MainScreen
import com.galiren.ferrum.ui.theme.FerrumTheme
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent

class MainActivity : ComponentActivity() {

  lateinit var circuit: Circuit

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    circuit = Circuit.Builder()
      .addPresenterFactory(FerrumFactory(application as FerrumApplication, this.lifecycleScope))
      .addUi<MainScreen, MainScreen.State> { state, modifier ->
        ExpenseList(
          modifier = modifier.fillMaxSize(),
          state = state,
        )
      }
      .build()
    enableEdgeToEdge()
    setContent {
      CircuitCompositionLocals(circuit) {
        FerrumTheme {
          CircuitContent(MainScreen, modifier = Modifier)
        }
      }
    }
  }
}
