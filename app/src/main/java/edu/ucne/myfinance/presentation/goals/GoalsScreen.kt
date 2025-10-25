package edu.ucne.myfinance.presentation.goals

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.myfinance.ui.theme.MyFinanceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(
    onBack: () -> Unit,
    viewModel: GoalsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    if (showAddDialog) {
        AddGoalDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, target, desc, deadline ->
                viewModel.onEvent(
                    GoalsEvent.AddGoal(
                        name = name,
                        targetAmount = target,
                        description = desc,
                        deadline = deadline
                    )
                )
                showAddDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Metas de Ahorro",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Meta")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.goals.isEmpty()) {
                EmptyGoalsSection { showAddDialog = true }
            } else {
                GoalListScreen(
                    goals = state.goals,
                    onDeleteGoal = { id ->
                        viewModel.onEvent(GoalsEvent.DeleteGoal(id))
                    },
                    onAddToGoal = { id, amount ->
                        viewModel.onEvent(GoalsEvent.AddToGoal(id, amount))
                    }
                )
            }
        }
    }
}

@Composable
fun EmptyGoalsSection(onAddGoal: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "No hay metas",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                "Crea tu primera meta de ahorro",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoalsScreenPreview() {
    MyFinanceTheme {
        GoalsScreen(onBack = {})
    }
}