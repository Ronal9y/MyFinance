package edu.ucne.myfinance.presentation.debts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import edu.ucne.myfinance.domain.model.CompoundingPeriod
import edu.ucne.myfinance.domain.model.Debt
import edu.ucne.myfinance.domain.model.DebtStatus
import edu.ucne.myfinance.domain.model.InterestType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeudaAddBottomSheet(
    onDismiss: () -> Unit,
    onConfirm: (Debt) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        tonalElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Nueva Deuda", style = MaterialTheme.typography.headlineSmall)

            var nombre by remember { mutableStateOf("") }
            var acreedor by remember { mutableStateOf("") }
            var monto by remember { mutableStateOf("") }
            var fecha by remember { mutableStateOf("") }

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del préstamo") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = acreedor,
                onValueChange = { acreedor = it },
                label = { Text("Acreedor") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = monto,
                onValueChange = { monto = it },
                label = { Text("Monto total") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha límite (yyyy-MM-dd)") },
                placeholder = { Text("2024-12-31") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismiss) { Text("Cancelar") }
                Spacer(Modifier.width(12.dp))
                Button(
                    onClick = {
                        val principal = monto.toDoubleOrNull() ?: 0.0
                        if (principal > 0 && nombre.isNotBlank() && fecha.isNotBlank()) {
                            onConfirm(
                                Debt(
                                    name = nombre,
                                    principalAmount = principal,
                                    interestRate = null,
                                    interestType = InterestType.SIMPLE,
                                    compoundingPeriod = CompoundingPeriod.MONTHLY,
                                    dueDate = fecha,
                                    remainingAmount = principal,
                                    creditor = acreedor,
                                    status = DebtStatus.ACTIVE,
                                    penaltyRate = 0.0
                                )
                            )
                        }
                    }
                ) {
                    Text("Crear")
                }
            }
        }
    }
}

//// presentation/debts/DeudaAddBottomSheet.kt
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DeudaAddBottomSheet(
//    onDismiss: () -> Unit,
//    onConfirm: (Debt) -> Unit
//) {
//    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
//
//    ModalBottomSheet(
//        onDismissRequest = onDismiss,
//        sheetState = sheetState,
//        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
//        tonalElevation = 8.dp
//    ) {
//        Column( // CORREGIDO: Cambié TableInfo.Column por Column
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 24.dp)
//                .padding(bottom = 24.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Text(
//                "Nueva Deuda",
//                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
//            )
//
//            var nombre by remember { mutableStateOf("") }
//            var acreedor by remember { mutableStateOf("") }
//            var monto by remember { mutableStateOf("") }
//            var interes by remember { mutableStateOf("") }
//            var fecha by remember { mutableStateOf("") }
//            var periodo by remember { mutableStateOf(CompoundingPeriod.MONTHLY) }
//            var tipoInteres by remember { mutableStateOf(InterestType.SIMPLE) }
//            var penalizacion by remember { mutableStateOf("5.0") }
//
//            OutlinedTextField(
//                value = nombre,
//                onValueChange = { nombre = it },
//                label = { Text("Nombre del préstamo") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            OutlinedTextField(
//                value = acreedor,
//                onValueChange = { acreedor = it },
//                label = { Text("Acreedor") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            OutlinedTextField(
//                value = monto,
//                onValueChange = { monto = it },
//                label = { Text("Monto total") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            // Fila: Interés + Tipo de Interés
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(12.dp),
//                verticalAlignment = Alignment.Top
//            ) {
//                // Campo de interés
//                OutlinedTextField(
//                    value = interes,
//                    onValueChange = { interes = it },
//                    label = { Text("Interés anual %") },
//                    placeholder = { Text("0.0") },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
//                    modifier = Modifier.weight(1f)
//                )
//
//                // Dropdown de tipo de interés
//                Column(modifier = Modifier.weight(1f)) {
//                    var expandedTipo by remember { mutableStateOf(false) }
//
//                    ExposedDropdownMenuBox(
//                        expanded = expandedTipo,
//                        onExpandedChange = { expandedTipo = !expandedTipo },
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        OutlinedTextField(
//                            value = when (tipoInteres) {
//                                InterestType.SIMPLE -> "Interés Simple"
//                                InterestType.COMPOUND -> "Interés Compuesto"
//                            },
//                            onValueChange = {},
//                            readOnly = true,
//                            label = { Text("Tipo interés") },
//                            trailingIcon = {
//                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipo)
//                            },
//                            modifier = Modifier
//                                .menuAnchor()
//                                .fillMaxWidth()
//                        )
//
//                        ExposedDropdownMenu(
//                            expanded = expandedTipo,
//                            onDismissRequest = { expandedTipo = false }
//                        ) {
//                            InterestType.values().forEach { tipo ->
//                                DropdownMenuItem(
//                                    text = {
//                                        Text(
//                                            when (tipo) {
//                                                InterestType.SIMPLE -> "Interés Simple"
//                                                InterestType.COMPOUND -> "Interés Compuesto"
//                                            }
//                                        )
//                                    },
//                                    onClick = {
//                                        tipoInteres = tipo
//                                        expandedTipo = false
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//
//            // Fila: Período + Penalización
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(12.dp),
//                verticalAlignment = Alignment.Top
//            ) {
//                // Dropdown de período
//                Column(modifier = Modifier.weight(1f)) {
//                    var expandedPeriodo by remember { mutableStateOf(false) }
//
//                    ExposedDropdownMenuBox(
//                        expanded = expandedPeriodo,
//                        onExpandedChange = { expandedPeriodo = !expandedPeriodo },
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        OutlinedTextField(
//                            value = when (periodo) {
//                                CompoundingPeriod.DAILY -> "Diario"
//                                CompoundingPeriod.WEEKLY -> "Semanal"
//                                CompoundingPeriod.MONTHLY -> "Mensual"
//                                CompoundingPeriod.YEARLY -> "Anual"
//                            },
//                            onValueChange = {},
//                            readOnly = true,
//                            label = { Text("Período") },
//                            trailingIcon = {
//                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPeriodo)
//                            },
//                            modifier = Modifier
//                                .menuAnchor()
//                                .fillMaxWidth()
//                        )
//
//                        ExposedDropdownMenu(
//                            expanded = expandedPeriodo,
//                            onDismissRequest = { expandedPeriodo = false }
//                        ) {
//                            CompoundingPeriod.values().forEach { periodoItem ->
//                                DropdownMenuItem(
//                                    text = {
//                                        Text(
//                                            when (periodoItem) {
//                                                CompoundingPeriod.DAILY -> "Diario"
//                                                CompoundingPeriod.WEEKLY -> "Semanal"
//                                                CompoundingPeriod.MONTHLY -> "Mensual"
//                                                CompoundingPeriod.YEARLY -> "Anual"
//                                            }
//                                        )
//                                    },
//                                    onClick = {
//                                        periodo = periodoItem
//                                        expandedPeriodo = false
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
//
//                // Campo de penalización
//                OutlinedTextField(
//                    value = penalizacion,
//                    onValueChange = { penalizacion = it },
//                    label = { Text("Penalización %") },
//                    placeholder = { Text("5.0") },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
//                    modifier = Modifier.weight(1f)
//                )
//            }
//
//            OutlinedTextField(
//                value = fecha,
//                onValueChange = { fecha = it },
//                label = { Text("Fecha límite (yyyy-MM-dd)") },
//                placeholder = { Text("2024-12-31") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End
//            ) {
//                TextButton(onClick = onDismiss) { Text("Cancelar") }
//                Spacer(Modifier.width(12.dp))
//                Button(
//                    onClick = {
//                        val principal = monto.toDoubleOrNull() ?: 0.0
//                        val rate = interes.toDoubleOrNull()
//                        val penalty = penalizacion.toDoubleOrNull() ?: 5.0
//                        if (principal > 0 && nombre.isNotBlank() && fecha.isNotBlank()) {
//                            onConfirm(
//                                Debt(
//                                    name = nombre,
//                                    principalAmount = principal,
//                                    interestRate = if (rate != null && rate > 0) rate else null,
//                                    interestType = tipoInteres,
//                                    compoundingPeriod = periodo,
//                                    dueDate = fecha,
//                                    remainingAmount = principal,
//                                    creditor = acreedor,
//                                    status = DebtStatus.ACTIVE,
//                                    penaltyRate = penalty
//                                )
//                            )
//                        }
//                    }
//                ) {
//                    Text("Crear")
//                }
//            }
//        }
//    }
//}