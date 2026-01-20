package edu.ucne.myfinance.presentation.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import edu.ucne.myfinance.R

@Composable
fun WelcomeScreen(
    onStartWithoutAccount: () -> Unit,
    onGoToLogin: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),          // outer margin
        contentAlignment = Alignment.Center
    ) {

        // 1️⃣  Smaller background (no longer full-screen)
        Image(
            painter = painterResource(R.drawable.intro),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth(0.95f)   // 65 % of screen width
                .align(Alignment.TopCenter)
                .padding(top = 60.dp)  // push it down a bit
        )

        // 2️⃣  Buttons side-by-side, higher up
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp), // raise them
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onStartWithoutAccount) {
                Text("Empezar")
            }
            OutlinedButton(onClick = onGoToLogin) {
                Text("Iniciar sesión")
            }
        }
    }
}