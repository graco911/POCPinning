package com.accenture.pocpinning.data.remote.todo

import com.accenture.pocpinning.data.model.todo.TodoResponse
import com.accenture.pocpinning.data.usecaseresult.UseCaseResult

interface ITodoAPI {
    suspend fun fetchTodoList(): UseCaseResult<List<TodoResponse>>
}