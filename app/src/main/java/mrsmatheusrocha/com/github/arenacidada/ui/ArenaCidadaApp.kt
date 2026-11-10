package br.com.fiap.arenacidada.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.arenacidada.viewmodel.ArenaViewModel

@Composable
fun ArenaCidadaApp(viewModel: ArenaViewModel) {
    val navController = rememberNavController()
    val demands by viewModel.demands.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                totalCount = demands.size,
                resolvedCount = demands.count { it.isResolved },
                onNavigateToList = { navController.navigate("list") },
                onNavigateToForm = { navController.navigate("form") }
            )
        }
        composable("list") {
            DemandListScreen(
                demands = demands,
                onToggleStatus = { viewModel.toggleStatus(it) },
                onDelete = { viewModel.deleteDemand(it) },
                onNavigateToForm = { navController.navigate("form") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("form") {
            DemandFormScreen(
                onSave = { court, neighborhood, category, desc ->
                    viewModel.addDemand(court, neighborhood, category, desc)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}