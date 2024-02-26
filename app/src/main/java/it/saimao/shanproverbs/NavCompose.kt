package it.saimao.shanproverbs

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.saimao.shanproverbs.data.Jsons
import it.saimao.shanproverbs.ui.theme.screens.DetailScreen
import it.saimao.shanproverbs.ui.theme.screens.HomeScreen
import it.saimao.shanproverbs.ui.theme.screens.ShanProverbViewModel

object Destinations {
    const val Home = "home"
    const val Detail = "detail/{key}"
}

@Composable
fun NavCompose() {
    val navController = rememberNavController()
    val viewModel: ShanProverbViewModel = viewModel(factory = ShanProverbViewModel.factory)

    NavHost(navController = navController, startDestination = Destinations.Home, builder = {
        composable(Destinations.Home) {
            HomeScreen(viewModel = viewModel, onDetailItemClick = {
                navController.navigate("detail/${it}")
            })
        }

        composable(Destinations.Detail) { backStackEntry ->
            val key = backStackEntry.arguments?.getString("key")
            if (key != null) {
                DetailScreen(
                    proverbKey = key,
                    viewModel = viewModel,
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

    })
}