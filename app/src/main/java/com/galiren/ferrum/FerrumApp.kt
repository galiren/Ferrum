package com.galiren.ferrum

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.galiren.ferrum.ui.screen.main.ExpenseList
import com.galiren.ferrum.ui.screen.summary.SummaryScreen
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FerrumApp() {
  val navController = rememberNavController()
  val appState = rememberAppState()
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    // todo navigation
    bottomBar = {
      FerrumAppBottomBar(appState.currentRoute, navController)
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
//          state.eventSink(MainScreen.Event.OpenDialog(null))
        },
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "Add",
        )
      }
    },
  ) { paddingValues ->
    NavHost(navController = navController, startDestination = Route.MainRoute) {
      composable<Route.MainRoute> {
        ExpenseList(
          modifier = Modifier.padding(paddingValues),
        )
      }
      composable<Route.SummaryRoute> {
        SummaryScreen(
          modifier = Modifier.padding(paddingValues),
        )
      }
    }
  }
}

// todo design navigation bar state!!
@Composable
private fun FerrumAppBottomBar(route: Route, navController: NavController) {
  val selects = mutableListOf(false, false, false)
  when (route) {
    is Route.MainRoute -> {
      selects[0] = true
    }
    is Route.SummaryRoute -> {
      selects[1] = true
    }
    is Route.ConfigurationRoute -> {
      selects[2] = true
    }
  }
  NavigationBar {
    NavigationBarItem(
      selected = selects[0],
      onClick = {
        navController.navigate(Route.MainRoute)
      },
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
      selected = selects[1],
      onClick = {
        navController.navigate(Route.SummaryRoute)
      },
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
      selected = selects[2],
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
}

//interface Route {
//
//}
//@Serializable
//object MainRoute
//
//@Serializable
//object SummaryRoute
//
//@Serializable
//object ConfigurationRoute

@Serializable
sealed interface Route {
  @Serializable
  data object MainRoute : Route

  @Serializable
  data object SummaryRoute : Route

  @Serializable
  data object ConfigurationRoute : Route
}

data class AppState internal constructor(
  val currentRoute: Route = Route.MainRoute
)

@Composable
fun rememberAppState(): AppState = remember {
  AppState(
    currentRoute = Route.MainRoute,
  )
}
