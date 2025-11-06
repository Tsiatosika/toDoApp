package com.example.jetpacks.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpacks.data.model.Task
import com.example.jetpacks.ui.components.AddTaskDialog
import com.example.jetpacks.ui.components.EditTaskDialog
import com.example.jetpacks.ui.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    tasks: List<Task>,
    onAddTask: (String, String, Int) -> Unit,
    onEditTask: (Long, String, String, Int) -> Unit,
    onTaskUpdated: (Task, Boolean) -> Unit,
    onTaskDeleted: (Task) -> Unit
) {
    var showAddTaskScreen by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Todos",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        )
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddTaskScreen = true },
                containerColor = Color(0xFF2196F3),
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(16.dp)
                    .size(64.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Task",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    ) { paddingValues ->
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Todos you add will appear here",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onTaskUpdated = onTaskUpdated,
                        onTaskDeleted = onTaskDeleted,
                        onTaskEdit = { taskToEdit = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }

    if (showAddTaskScreen) {
        AddTaskDialog(
            onDismiss = { showAddTaskScreen = false },
            onAddTask = { title, label, priority ->
                onAddTask(title, label, priority)
                showAddTaskScreen = false
            }
        )
    }

    if (taskToEdit != null) {
        EditTaskDialog(
            task = taskToEdit!!,
            onDismiss = { taskToEdit = null },
            onEditTask = { taskId, title, label, priority ->
                onEditTask(taskId, title, label, priority)
                taskToEdit = null
            }
        )
    }
}