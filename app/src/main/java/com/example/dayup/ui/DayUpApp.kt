package com.example.dayup.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dayup.ui.theme.DayUpTheme
import com.example.dayup.viewmodel.DayUpViewModel
import kotlinx.coroutines.launch

@Composable
fun DayUpApp(viewModel: DayUpViewModel = viewModel()) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val darkTheme by viewModel.darkThemeEnabled.collectAsState()
    val count by viewModel.count.collectAsState()
    val scope = rememberCoroutineScope()

    DayUpTheme(darkTheme = darkTheme) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    onToggleTheme = { viewModel.toggleTheme() },
                    darkThemeEnabled = darkTheme
                )
            }
        ) {
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        onMenuClick = { scope.launch { drawerState.open() } }
                    )
                }
            ) { innerPadding ->
                CounterScreen(
                    modifier = Modifier.padding(innerPadding),
                    taskTitle = viewModel.taskTitle.value,
                    count = count,
                    onAddClick = { viewModel.incrementCounter() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCounter() {
    val darkTheme = remember { mutableStateOf(false) }
    val count = remember { mutableIntStateOf(0) }
    val taskTitle = remember { mutableStateOf("Estudar") }

    DayUpTheme(darkTheme = darkTheme.value) {
        CounterScreen(
            modifier = Modifier.padding(16.dp),
            taskTitle = taskTitle.value,
            count = count.intValue,
            onAddClick = { count.intValue++ }
        )
    }
}