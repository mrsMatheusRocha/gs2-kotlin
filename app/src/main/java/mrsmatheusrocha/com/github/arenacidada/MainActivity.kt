package br.com.fiap.arenacidada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.arenacidada.data.AppDatabase
import br.com.fiap.arenacidada.data.CourtDemandEntity
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    totalCount: Int,
    resolvedCount: Int,
    onNavigateToList: () -> Unit,
    onNavigateToForm: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Arena Cidadã • CitizenTech") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Esporte, Zeladoria e Comunidade",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Democratizando o acesso e a manutenção de quadras e espaços esportivos públicos. " +
                                "Voltado para moradores, atletas amadores e gestores comunitários.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Registros Totais", style = MaterialTheme.typography.labelLarge)
                        Text("$totalCount", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    }
                }
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Atendidos", style = MaterialTheme.typography.labelLarge)
                        Text("$resolvedCount", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onNavigateToList,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Acompanhar Quadras e Demandas")
            }

            OutlinedButton(
                onClick = onNavigateToForm,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Nova Ocorrência / Solicitação")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemandListScreen(
    demands: List<CourtDemandEntity>,
    onToggleStatus: (CourtDemandEntity) -> Unit,
    onDelete: (CourtDemandEntity) -> Unit,
    onNavigateToForm: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Demandas das Quadras") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToForm) {
                Icon(Icons.Default.Add, contentDescription = "Novo Registro")
            }
        }
    ) { padding ->
        if (demands.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhuma demanda registrada ainda.\nToque no botão '+' para cadastrar uma ocorrência na quadra do seu bairro.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(demands, key = { it.id }) { item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.courtName,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                AssistChip(
                                    onClick = { onToggleStatus(item) },
                                    label = { Text(if (item.isResolved) "Resolvido" else "Pendente") },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = if (item.isResolved) Icons.Default.CheckCircle else Icons.Default.Pending,
                                            contentDescription = null
                                        )
                                    }
                                )
                            }
                            Text(
                                text = "${item.neighborhood} • Categoria: ${item.category}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = item.description, style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = { onToggleStatus(item) }) {
                                    Text(if (item.isResolved) "Reabrir" else "Concluir")
                                }
                                IconButton(onClick = { onDelete(item) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Excluir")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemandFormScreen(
    onSave: (String, String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var courtName by remember { mutableStateOf("") }
    var neighborhood by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Iluminação / Estrutura") }
    var description by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Registro na Quadra") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = courtName,
                onValueChange = {
                    courtName = it
                    showError = false
                },
                label = { Text("Nome da Quadra ou Espaço Público *") },
                isError = showError && courtName.isBlank(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = neighborhood,
                onValueChange = {
                    neighborhood = it
                    showError = false
                },
                label = { Text("Bairro / Região *") },
                isError = showError && neighborhood.isBlank(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Categoria (Iluminação, Alambrado, Limpeza, Uso)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    showError = false
                },
                label = { Text("Descrição detalhada da ocorrência *") },
                isError = showError && description.isBlank(),
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            if (showError) {
                Text(
                    text = "Preencha todos os campos obrigatórios (*) antes de salvar.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (courtName.isBlank() || neighborhood.isBlank() || description.isBlank()) {
                        showError = true
                    } else {
                        onSave(courtName, neighborhood, category, description)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Registro")
            }
        }
    }
}