package com.example.jetpacks.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jetpacks.data.model.Task

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val label: String,
    val priority: Int,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

// Mappers
fun TaskEntity.toTask() = Task(
    id = id,
    title = title,
    label = label,
    priority = priority,
    isCompleted = isCompleted,
    createdAt = createdAt
)

fun Task.toEntity() = TaskEntity(
    id = id,
    title = title,
    label = label,
    priority = priority,
    isCompleted = isCompleted,
    createdAt = createdAt
)