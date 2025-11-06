package com.example.jetpacks.data.model

data class Task(
    val id: Long = 0,
    val title: String,
    val label: String,
    val priority: Int, // 0: LOW, 1: MEDIUM, 2: HIGH
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)