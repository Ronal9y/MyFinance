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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.myfinance.Worker.FinanceCheckWorker
import edu.ucne.myfinance.common.NotificationHelper
import edu.ucne.myfinance.presentation.navigation.BottomBar
import edu.ucne.myfinance.presentation.navigation.FinanceNavHost
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
        val workRequest = PeriodicWorkRequestBuilder<FinanceCheckWorker>(24, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "finance_check",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

        // Solicitar permiso en Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
            }
        }
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { BottomBar(navController) }
            ) { innerPadding ->
                FinanceNavHost(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}