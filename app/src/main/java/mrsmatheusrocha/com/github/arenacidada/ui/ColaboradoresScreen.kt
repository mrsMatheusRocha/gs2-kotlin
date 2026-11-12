package br.com.fiap.arenacidada.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.outlined.EventBusy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.fiap.arenacidada.data.CourtDemandEntity
import br.com.fiap.arenacidada.data.ColaboradorEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColaboradoresScreen(
    demands: List<CourtDemandEntity>,
    colaboradores: List<ColaboradorEntity>,
    onNavigateToNewColaborador: () -> Unit,
    onAllocate: (CourtDemandEntity, ColaboradorEntity) -> Unit,
    onBack: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedDemand by remember { mutableStateOf<CourtDemandEntity?>(null) }
    var selectedColaborador by remember { mutableStateOf<ColaboradorEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestão de Colaboradores") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToNewColaborador) {
                Icon(Icons.Default.PersonAdd, contentDescription = "Novo Colaborador")
            }
        }
    ) { padding ->
        val pendingDemands = demands.filter { !it.isResolved }

        if (pendingDemands.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.EventBusy,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Nenhuma Ocorrência Pendente",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Não há ocorrências em aberto para alocar colaboradores no momento.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
                item {
                    Text(
                        text = "Ocorrências Pendentes",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                items(pendingDemands) { demand ->
                    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = demand.courtName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${demand.neighborhood} • Categoria: ${demand.category}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            if (demand.allocatedTo != null) {
                                Text(
                                    text = "Alocado para: ${demand.allocatedTo}",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    selectedDemand = demand
                                    showDialog = true
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.AssignmentTurnedIn, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(if (demand.allocatedTo != null) "Re-alocar Colaborador" else "Alocar Colaborador")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog && selectedDemand != null) {
        AlertDialog(
            onDismissRequest = { 
                showDialog = false 
                selectedColaborador = null
            },
            title = { Text("Selecione um Colaborador") },
            text = {
                if (colaboradores.isEmpty()) {
                    Text("Você ainda não possui nenhum colaborador cadastrado.")
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        colaboradores.forEach { colab ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                RadioButton(
                                    selected = selectedColaborador == colab,
                                    onClick = { selectedColaborador = colab }
                                )
                                Column {
                                    Text(text = colab.nome, fontWeight = FontWeight.Bold)
                                    Text(text = colab.especialidade, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                if (colaboradores.isEmpty()) {
                    TextButton(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                } else {
                    TextButton(
                        onClick = { 
                            selectedColaborador?.let { onAllocate(selectedDemand!!, it) }
                            showDialog = false 
                            selectedColaborador = null
                        },
                        enabled = selectedColaborador != null
                    ) {
                        Text("Confirmar")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { 
                    showDialog = false 
                    selectedColaborador = null
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}