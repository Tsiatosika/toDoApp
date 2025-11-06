package com.example.jetpacks.data.repository

import com.example.jetpacks.data.dao.TaskDao
import com.example.jetpacks.data.entity.TaskEntity
import com.example.jetpacks.data.entity.toEntity
import com.example.jetpacks.data.entity.toTask
import com.example.jetpacks.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> =
        taskDao.getAllTasks().map { entities ->
            entities.map { it.toTask() }
        }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntity())
    }
}