package com.example.dayup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dayup.ui.theme.DayUpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DayUpTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CounterApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CounterApp(modifier: Modifier = Modifier) {
    var count by remember { mutableIntStateOf(0) }
    var input by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(true) }

    Column(modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        OutlinedTextField(
            value = input,
            onValueChange = { newValue ->
                if(isEditing) {
                    input = newValue
                    count = newValue.toIntOrNull() ?: count
                }
            },
            label = { Text("Digite o título da atividade", color = if (isEditing) Color.Black else Color.Gray) },
            readOnly = !isEditing,
            trailingIcon = {
                if(isEditing) {
                    IconButton(onClick = { isEditing = !isEditing}) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Confirmar")
                    }
                }
                else {
                    IconButton(onClick = { isEditing = true }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")

                    }
                }
            }
        )

        Text(text = "$count",
            fontSize = 32.sp)
        Button(onClick = { count++ }) {
            Text(text = "Concluído")
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