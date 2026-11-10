package br.com.fiap.arenacidada.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemandFormScreen(
    onSave: (String, String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var courtName by remember { mutableStateOf("") }
    var neighborhood by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Iluminação / Estrutura") }
    var expanded by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    val categoriesList = listOf(
        "Iluminação / Estrutura",
        "Alambrado / Grades",
        "Limpeza / Lixo",
        "Uso / Conflitos",
        "Outros"
    )

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

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categoriesList.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                category = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }

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