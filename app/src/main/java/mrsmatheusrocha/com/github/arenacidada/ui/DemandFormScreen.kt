package br.com.fiap.arenacidada.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
                title = { Text("Novo Registro") },
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
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Preencha os dados abaixo para registrar um problema ou ocorrência na sua comunidade.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = courtName,
                onValueChange = {
                    courtName = it
                    showError = false
                },
                label = { Text("Nome da Quadra ou Espaço Público *") },
                placeholder = { Text("Ex: Quadra da Praça Central") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Place, contentDescription = null)
                },
                isError = showError && courtName.isBlank(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = neighborhood,
                onValueChange = {
                    neighborhood = it
                    showError = false
                },
                label = { Text("Bairro / Região *") },
                placeholder = { Text("Ex: Vila Mariana") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Map, contentDescription = null)
                },
                isError = showError && neighborhood.isBlank(),
                singleLine = true,
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
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Category, contentDescription = null)
                    },
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
                label = { Text("Descrição detalhada *") },
                placeholder = { Text("Descreva o que está quebrado, faltando, etc.") },
                leadingIcon = {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Notes, contentDescription = null)
                },
                isError = showError && description.isBlank(),
                minLines = 4,
                maxLines = 6, // Permite que o campo cresça, mas não infinitamente
                modifier = Modifier.fillMaxWidth()
            )

            if (showError) {
                Text(
                    text = "Preencha todos os campos obrigatórios (*) antes de salvar.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (courtName.isBlank() || neighborhood.isBlank() || description.isBlank()) {
                        showError = true
                    } else {
                        onSave(courtName, neighborhood, category, description)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Salvar Registro", fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}