package com.example.jetpacks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.jetpacks.data.database.AppDatabase
import com.example.jetpacks.data.model.Task
import com.example.jetpacks.data.repository.TaskRepositoryImpl
import com.example.jetpacks.ui.screens.MainScreen
import com.example.jetpacks.ui.theme.JetPacksTheme
import com.example.jetpacks.viewmodel.TaskViewModel
import com.example.jetpacks.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {

    private val taskViewModel: TaskViewModel by viewModels {
        val database = AppDatabase.getDatabase(this)
        val repository = TaskRepositoryImpl(database.taskDao())
        TaskViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPacksTheme {
                val tasks by taskViewModel.allTasks.collectAsState()

                MainScreen(
                    tasks = tasks,
                    onAddTask = { title, label, priority ->
                        taskViewModel.insert(
                            Task(
                                title = title,
                                label = label,
                                priority = priority
                            )
                        )
                    },
                    onEditTask = { taskId, title, label, priority ->
                        taskViewModel.editTask(taskId, title, label, priority) // ← AJOUTER
                    },
                    onTaskUpdated = { task, isCompleted ->
                        taskViewModel.update(task.copy(isCompleted = isCompleted))
                    },
                    onTaskDeleted = { task ->
                        taskViewModel.delete(task)
                    }
                )
            }
        }
    }
}