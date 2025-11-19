package edu.ucne.myfinance

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.myfinance.Worker.FinanceCheckWorker
import edu.ucne.myfinance.common.NotificationHelper
import edu.ucne.myfinance.presentation.navigation.BottomBar
import edu.ucne.myfinance.presentation.navigation.FinanceNavHost
import edu.ucne.myfinance.presentation.users.AuthViewModel
import edu.ucne.myfinance.ui.theme.MyFinanceTheme
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Crear canal de notificaciones
        NotificationHelper.createNotificationChannel(this)

        // Programar Worker
        val workRequest = PeriodicWorkRequestBuilder<FinanceCheckWorker>(30, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .setRequiresBatteryNotLow(false) // ⬅ permitir batería baja
                    .setRequiresCharging(false)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "finance_check",
            ExistingPeriodicWorkPolicy.REPLACE, // ⬅ fuerza re-programación
            workRequest
        )

        val instantRequest = OneTimeWorkRequestBuilder<FinanceCheckWorker>().build()
        WorkManager.getInstance(this).enqueue(instantRequest)
        // Solicitar permiso en Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
            }
        }
        setContent {
            val navController = rememberNavController()
            val authViewModel: AuthViewModel = hiltViewModel()
            val uiState by authViewModel.uiState.collectAsState()
            val isLogged = uiState.isLoggedIn
            Scaffold(
                bottomBar = { BottomBar(navController) }
            ) { innerPadding ->
                FinanceNavHost(
                    navController = navController,
                    authViewModel = authViewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}