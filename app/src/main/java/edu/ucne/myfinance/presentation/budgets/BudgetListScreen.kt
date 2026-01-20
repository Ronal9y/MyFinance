package edu.ucne.myfinance.presentation.budgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.myfinance.domain.model.Budget
import edu.ucne.myfinance.ui.theme.MyFinanceTheme

@Composable
fun BudgetListScreen(
    budgets: List<Budget>,
    onDeleteBudget: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(budgets, key = { it.id }) { budget ->
            BudgetListItem(
                budget = budget,
                onDelete = { onDeleteBudget(budget.id) }
            )
        }
    }
}

@Composable
fun BudgetListItem(
    budget: Budget,
    onDelete: () -> Unit
) {
    var showConfirm by remember { mutableStateOf(false) }

    if (showConfirm) {
        AlertDialog(
            onDismissRequest = { showConfirm = false },
            title = { Text("¿Eliminar presupuesto?") },
            text = {
                Text("Esta acción no se puede deshacer. ¿Estás seguro de eliminar este presupuesto?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showConfirm = false
                    }
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirm = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    budget.category.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { showConfirm = true }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val progress = (budget.spent / budget.limit).coerceIn(0.0, 1.0)
            val percentage = (progress * 100).toInt()

            Text(
                "${"%.2f".format(budget.spent)} $ / ${"%.2f".format(budget.limit)} S",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            LinearProgressIndicator(
                progress = progress.toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = when {
                    percentage >= budget.alertThreshold -> MaterialTheme.colorScheme.error
                    percentage >= 80 -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.primary
                }
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "$percentage% utilizado",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    "Restante: ${"%.2f".format(budget.limit - budget.spent)} $",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}