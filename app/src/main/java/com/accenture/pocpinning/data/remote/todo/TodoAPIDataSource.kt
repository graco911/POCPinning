package com.accenture.pocpinning.data.remote.todo

import com.accenture.pocpinning.data.model.todo.TodoResponse
import com.accenture.pocpinning.data.usecaseresult.UseCaseResult
import kotlinx.coroutines.CoroutineDispatcher

class TodoAPIDataSource(
    private val iTodoAPI: ITodoAPI,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchTodoList():
            UseCaseResult<List<TodoResponse>> {
        return iTodoAPI.fetchTodoList()
    }
}