package edu.ucne.myfinance.presentation.debts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.myfinance.domain.model.CompoundingPeriod
import edu.ucne.myfinance.domain.model.Debt
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.pow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeudaListScreen(
    onBack: () -> Unit,
    viewModel: DeudaViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var montoPago by remember { mutableStateOf("") }
    var nuevaFechaRenovar by remember { mutableStateOf("") }

    // Diálogo de PAGO (existente)
    if (state.mostrarDialogoPago != null) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(DeudaEvent.MostrarDialogoPago(null)) },
            title = { Text("Abonar a deuda") },
            text = {
                Column {
                    Text("Monto a abonar:")
                    OutlinedTextField(
                        value = montoPago,
                        onValueChange = { montoPago = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = { Text("0.00") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val monto = montoPago.toDoubleOrNull() ?: 0.0
                        if (monto > 0) {
                            viewModel.onEvent(DeudaEvent.PagarCuota(state.mostrarDialogoPago!!, monto))
                            montoPago = ""
                        }
                    }
                ) {
                    Text("Pagar")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onEvent(DeudaEvent.MostrarDialogoPago(null)) }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Modal de AGREGAR (existente)
    if (state.mostrarDialogoAgregar) {
        DeudaAddBottomSheet(
            onDismiss = { viewModel.onEvent(DeudaEvent.CerrarDialogoAgregar) },
            onConfirm = { deuda ->
                viewModel.onEvent(DeudaEvent.GuardarDeuda(deuda))
                viewModel.onEvent(DeudaEvent.CerrarDialogoAgregar)
            }
        )
    }
    // Diálogo de RENOVACIÓN - CORREGIDO
    if (state.mostrarDialogoRenovar != null) {
        val deuda = state.deudas.find { it.id == state.mostrarDialogoRenovar }
        var penalizacionAdicional by remember { mutableStateOf("0.0") }

        AlertDialog(
            onDismissRequest = { viewModel.onEvent(DeudaEvent.CerrarDialogoRenovar) },
            title = { Text("Renovar Deuda") },
            text = {
                Column {
                    deuda?.let {
                        val saldoActual = it.remainingAmount
                        val penalizacionBase = it.penaltyRate
                        val penalizacionExtra = penalizacionAdicional.toDoubleOrNull() ?: 0.0
                        val totalPenalizacion = penalizacionBase + penalizacionExtra
                        val nuevoSaldo = saldoActual * (1 + totalPenalizacion / 100.0)

                        Text("Deuda: ${it.name}", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Saldo actual: $${"%.2f".format(saldoActual)}")
                        Text("Penalización base: ${it.penaltyRate}%")

                        // Campo para penalización adicional
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Penalización adicional (%):")
                        OutlinedTextField(
                            value = penalizacionAdicional,
                            onValueChange = { penalizacionAdicional = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("0.0") }
                        )

                        Text("Total penalización: ${"%.1f".format(totalPenalizacion)}%")
                        Text("Nuevo saldo: $${"%.2f".format(nuevoSaldo)}",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Text("Nueva fecha límite:")
                    OutlinedTextField(
                        value = nuevaFechaRenovar,
                        onValueChange = { nuevaFechaRenovar = it },
                        label = { Text("yyyy-MM-dd") },
                        placeholder = { Text("2024-12-31") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nuevaFechaRenovar.isNotBlank()) {
                            val penalizacionExtra = penalizacionAdicional.toDoubleOrNull() ?: 0.0
                            viewModel.onEvent(DeudaEvent.RenovarDeuda(
                                state.mostrarDialogoRenovar!!,
                                nuevaFechaRenovar,
                                penalizacionExtra
                            ))
                            nuevaFechaRenovar = ""
                            penalizacionAdicional = "0.0"
                        }
                    }
                ) {
                    Text("Renovar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    viewModel.onEvent(DeudaEvent.CerrarDialogoRenovar)
                    nuevaFechaRenovar = ""
                    penalizacionAdicional = "0.0"
                }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Resto del Scaffold permanece igual...
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mis Deudas", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(DeudaEvent.MostrarDialogoAgregar) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Deuda")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (state.cargando) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.deudas.isEmpty()) {
                EmptyDebtsSection { viewModel.onEvent(DeudaEvent.MostrarDialogoAgregar) }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.deudas, key = { it.id }) { deuda ->
                        DeudaCard(
                            deuda = deuda,
                            onAbonar = { id -> viewModel.onEvent(DeudaEvent.MostrarDialogoPago(id)) },
                            onEliminar = { id -> viewModel.onEvent(DeudaEvent.EliminarDeuda(id)) },
                            onRenovar = { id ->
                                viewModel.onEvent(DeudaEvent.MostrarDialogoRenovar(id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyDebtsSection(onAdd: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("No hay deudas", style = MaterialTheme.typography.bodyLarge)
            Text(
                "Presiona + para agregar una nueva deuda",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
@Composable
fun DeudaCard(
    deuda: Debt,
    onAbonar: (Int) -> Unit,
    onEliminar: (Int) -> Unit,
    onRenovar: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showConfirm by remember { mutableStateOf(false) }

    // Cálculos simples sin interés automático
    val restanteReal = deuda.remainingAmount
    val progreso = if (deuda.principalAmount > 0) {
        ((deuda.principalAmount - restanteReal) / deuda.principalAmount).toFloat().coerceIn(0f, 1f)
    } else {
        0f
    }

    val vencido = remember(deuda.dueDate) { diasEntre(hoy(), deuda.dueDate) < 0 }

    // Diálogo de confirmación de eliminación...
    if (showConfirm) {
        AlertDialog(
            onDismissRequest = { showConfirm = false },
            title = { Text("¿Eliminar deuda?") },
            text = {
                Text(
                    if (deuda.remainingAmount > 0)
                        "Aún queda saldo pendiente. ¿Seguro que deseas eliminarla?"
                    else "La deuda ya está saldada. ¿Deseas eliminarla?"
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onEliminar(deuda.id)
                        showConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirm = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header con nombre y botón eliminar...
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = deuda.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Acreedor: ${deuda.creditor}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = { showConfirm = true }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Información de montos y fecha...
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Restante hoy",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$${"%.2f".format(restanteReal)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (restanteReal > 0) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.primary
                    )
                    deuda.interestRate?.let { tasa ->
                        Text(
                            text = "Interés: ${tasa}% ${deuda.interestType.name.lowercase()}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Fecha límite",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = deuda.dueDate,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (vencido) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Barra de progreso...
            LinearProgressIndicator(
                progress = { progreso },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = when {
                    progreso >= 1f -> MaterialTheme.colorScheme.primary
                    vencido -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.tertiary
                },
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Text(
                text = "${(progreso * 100).toInt()}% completado",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(Modifier.height(8.dp))

            // BOTÓN CORREGIDO - Solo dice "Renovar" cuando está vencido
            if (vencido) {
                Button(
                    onClick = { onRenovar(deuda.id) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Renovar") // Solo dice "Renovar"
                }
            } else {
                Button(
                    onClick = { onAbonar(deuda.id) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = restanteReal > 0
                ) {
                    Icon(Icons.Default.Payment, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Abonar cuota")
                }
            }
        }
    }


private fun saldoHoy(deuda: Debt): Pair<Double, Float> {
    val dias = diasEntre(hoy(), deuda.dueDate)
    val periodos = when (deuda.compoundingPeriod) {
        CompoundingPeriod.DAILY   -> dias.toDouble()
        CompoundingPeriod.WEEKLY  -> dias / 7.0
        CompoundingPeriod.MONTHLY -> dias / 30.0
        CompoundingPeriod.YEARLY  -> dias / 365.0
    }.coerceAtLeast(0.0)

    val tasaPorPeriodo = (deuda.interestRate ?: 0.0) / 100.0 /
            when (deuda.compoundingPeriod) {
                CompoundingPeriod.DAILY   -> 365.0
                CompoundingPeriod.WEEKLY  -> 52.0
                CompoundingPeriod.MONTHLY -> 12.0
                CompoundingPeriod.YEARLY  -> 1.0
            }

    val factor = (1 + tasaPorPeriodo).pow(periodos)
    val saldoActual = deuda.principalAmount * factor
    val progreso = ((deuda.principalAmount - saldoActual) / deuda.principalAmount).toFloat()
        .coerceIn(0f, 1f)

    return Pair(saldoActual, progreso)
}

private fun hoy(): String =
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(java.util.Date())

private fun diasEntre(start: String, end: String): Long {
    val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val ms = fmt.parse(end)!!.time - fmt.parse(start)!!.time
    return TimeUnit.DAYS.convert(ms, TimeUnit.MILLISECONDS)
}