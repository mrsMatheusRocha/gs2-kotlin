package br.com.fiap.arenacidada.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColaboradorFormScreen(
    onSave: (String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var especialidade by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Colaborador") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Cadastre um novo colaborador para atuar nas demandas e ocorrências.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = nome,
                onValueChange = {
                    nome = it
                    showError = false
                },
                label = { Text("Nome Completo *") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                isError = showError && nome.isBlank(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = especialidade,
                onValueChange = {
                    especialidade = it
                    showError = false
                },
                label = { Text("Especialidade / Cargo *") },
                placeholder = { Text("Ex: Eletricista, Limpeza, Pedreiro") },
                leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null) },
                isError = showError && especialidade.isBlank(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text("Telefone / Contato") },
                placeholder = { Text("(11) 99999-9999") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (showError) {
                Text(
                    text = "Preencha os campos obrigatórios (*).",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (nome.isBlank() || especialidade.isBlank()) {
                        showError = true
                    } else {
                        onSave(nome, especialidade, telefone)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cadastrar Colaborador", fontWeight = FontWeight.Bold)
            }
        }
    }
}