package edu.ucne.myfinance.Worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import edu.ucne.myfinance.common.NotificationHelper
import edu.ucne.myfinance.domain.repository.BudgetRepository
import edu.ucne.myfinance.domain.repository.DebtRepository
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@HiltWorker
class FinanceCheckWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val budgetRepository: BudgetRepository,
    private val debtRepository: DebtRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        checkBudgetAlerts()
        checkDebtAlerts()
        return Result.success()
    }

    private suspend fun checkBudgetAlerts() {
        budgetRepository.getAllBudgets().collect { budgets ->
            budgets.forEach { budget ->
                val used = budget.spent / budget.limit
                if (used >= budget.alertThreshold / 100.0) {
                    showNotification(
                        title = "Presupuesto al límite",
                        message = "Tu presupuesto en ${budget.category.name} ha alcanzado el ${(used * 100).toInt()}%."
                    )
                }
            }
        }
    }

    private suspend fun checkDebtAlerts() {
        debtRepository.getAllDebts().collect { debts ->
            val today = LocalDate.now()
            debts.forEach { debt ->
                val dueDate = LocalDate.parse(debt.dueDate)
                val daysLeft = ChronoUnit.DAYS.between(today, dueDate)
                if (daysLeft in 1..3 && debt.remainingAmount > 0) {
                    showNotification(
                        title = "Deuda próxima a vencer",
                        message = "Tu deuda '${debt.name}' vence en $daysLeft días."
                    )
                }
            }
        }
    }

    private fun showNotification(title: String, message: String) {
        val notificationId = (System.currentTimeMillis() % 10000).toInt()
        val builder = NotificationCompat.Builder(appContext, NotificationHelper.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(appContext)) {
            if (ActivityCompat.checkSelfPermission(
                    appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notify(notificationId, builder.build())
            }
        }
    }
}