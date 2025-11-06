package com.example.jetpacks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import com.example.jetpacks.data.entity.TaskEntity
import com.example.jetpacks.data.repository.TaskRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _allTasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val allTasks: StateFlow<List<TaskEntity>> = _allTasks.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allTasks.collect { tasks ->
                _allTasks.value = tasks
            }
        }
    }

    fun insert(task: TaskEntity) = viewModelScope.launch {
        repository.insert(task)
    }

    fun update(task: TaskEntity) = viewModelScope.launch {
        repository.update(task)
    }

    fun delete(task: TaskEntity) = viewModelScope.launch {
        repository.delete(task)
    }
}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}