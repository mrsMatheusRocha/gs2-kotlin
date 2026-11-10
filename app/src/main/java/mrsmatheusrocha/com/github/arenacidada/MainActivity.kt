package br.com.fiap.arenacidada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.arenacidada.data.AppDatabase
import br.com.fiap.arenacidada.ui.ArenaCidadaApp
import br.com.fiap.arenacidada.viewmodel.ArenaViewModel
import br.com.fiap.arenacidada.viewmodel.ArenaViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getDatabase(this)
        val factory = ArenaViewModelFactory(database.courtDemandDao())

        setContent {
            MaterialTheme {
                val viewModel: ArenaViewModel = viewModel(factory = factory)
                ArenaCidadaApp(viewModel)
            }
        }
    }
}