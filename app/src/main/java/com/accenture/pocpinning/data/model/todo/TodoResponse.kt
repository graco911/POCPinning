package com.accenture.pocpinning.data.model.todo

data class TodoResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)