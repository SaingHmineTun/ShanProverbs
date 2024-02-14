package it.saimao.shanproverbs

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.saimao.shanproverbs.data.Jsons
import it.saimao.shanproverbs.ui.theme.screens.DetailScreen
import it.saimao.shanproverbs.ui.theme.screens.HomeScreen

object Destinations {
    const val Home = "home"
    const val Detail = "detail/{key}"
}

@Composable
fun NavCompose() {
    val navController = rememberNavController()
    val allProverbs = Jsons.getJsonData(LocalContext.current)
    NavHost(navController = navController, startDestination = Destinations.Home, builder = {
        composable(Destinations.Home) {
            HomeScreen(allProverbs.allProverbs.map { it.key }, onDetailItemClick = {
                navController.navigate("detail/${it}")
            })
        }

        composable(Destinations.Detail) { backStackEntry ->
            val key = backStackEntry.arguments?.getString("key")
            val proverbs = allProverbs.allProverbs.first { it.key == key }
            DetailScreen(proverbs = proverbs)
        }

    })
}