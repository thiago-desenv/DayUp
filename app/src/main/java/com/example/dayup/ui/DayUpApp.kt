package com.example.dayup.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dayup.ui.theme.DayUpTheme
import com.example.dayup.viewmodel.DayUpViewModel
import kotlinx.coroutines.launch

@Composable
fun DayUpApp(viewModel: DayUpViewModel = viewModel()) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    DayUpTheme(darkTheme = viewModel.darkThemeEnabled.value) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    onToggleTheme = { viewModel.toggleTheme() },
                    darkThemeEnabled = viewModel.darkThemeEnabled.value
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
                    count = viewModel.count.intValue,
                    onAddClick = { viewModel.incrementCounter() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCounter() {
    val fakeVm = DayUpViewModel()
    DayUpApp(viewModel = fakeVm)
}