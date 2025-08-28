package com.example.dayup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dayup.ui.theme.DayUpTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkThemeEnabled by remember { mutableStateOf(false) }

            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            DayUpTheme(darkTheme = darkThemeEnabled) {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Configurações", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(onClick = { darkThemeEnabled = !darkThemeEnabled }) {
                                Icon(
                                    imageVector = if(darkThemeEnabled) Icons.Default.Brightness7 else Icons.Default.Brightness4,
                                    contentDescription = "Alterar tema",
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(if(darkThemeEnabled) "Tema claro" else "Tema escuro")
                            }
                        }
                    }
                ) {
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = true,
                                    onClick = { },
                                    icon = { Icon(Icons.Default.Home, contentDescription = "Início") }
                                )
                                NavigationBarItem(
                                    selected = false,
                                    onClick = { },
                                    icon = { Icon(Icons.Default.Check, contentDescription = "Progresso") }
                                )
                                NavigationBarItem(
                                    selected = false,
                                    onClick = {
                                        scope.launch { drawerState.open() }
                                    },
                                    icon = { Icon(Icons.Default.Menu, contentDescription = "Menu") }
                                )
                            }
                        }
                    ) { innerPadding ->
                        CounterApp(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CounterApp(modifier: Modifier = Modifier) {
    var count by remember { mutableIntStateOf(0) }
    var taskTitle by remember { mutableStateOf("Estudar") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = taskTitle,
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 70.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$count",
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 150.sp)
                )
                Text(
                    text = "Dias",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 22.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { count++ },
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Adicionar", fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DayUpTheme {
        CounterApp()
    }
}