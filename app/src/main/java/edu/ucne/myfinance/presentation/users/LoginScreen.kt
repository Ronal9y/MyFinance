package edu.ucne.myfinance.presentation.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.myfinance.R

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var isRegister by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            if (event is AuthEvent.NavigateToDashboard && event.success) {
                onLoginSuccess()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painterResource(R.drawable.desarrollopersonal), contentDescription = null, modifier = Modifier.size(120.dp))
        Spacer(Modifier.height(24.dp))

        if (isRegister) {
            OutlinedTextField(username, { username = it }, label = { Text("Nombre de usuario") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
        }

        OutlinedTextField(email, { email = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(password, { password = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())

        if (showError) {
            Text("Completa todos los campos", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank() || (isRegister && username.isBlank())) {
                    showError = true
                } else {
                    showError = false
                    viewModel.onEvent(AuthEvent.Submit(email, password, username, isRegister))
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isRegister) "Crear cuenta" else "Entrar")
        }

        TextButton(onClick = { isRegister = !isRegister }) {
            Text(if (isRegister) "¿Ya tienes cuenta? Inicia sesión" else "¿No tienes cuenta? Regístrate")
        }
    }
}