package com.example.jetpacks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpacks.data.model.Task
import com.example.jetpacks.data.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    val allTasks = taskRepository.getAllTasks().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun insert(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }

    fun editTask(taskId: Long, newTitle: String, newLabel: String, newPriority: Int) {
        viewModelScope.launch {
            // Récupérer la tâche existante
            val currentTasks = allTasks.value
            val taskToEdit = currentTasks.find { it.id == taskId }

            taskToEdit?.let { task ->
                val updatedTask = task.copy(
                    title = newTitle,
                    label = newLabel,
                    priority = newPriority
                )
                taskRepository.updateTask(updatedTask)
            }
        }
    }
    fun update(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}