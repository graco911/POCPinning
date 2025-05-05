package com.accenture.pocpinning.data.mock.todo

import com.accenture.pocpinning.data.model.todo.TodoResponse
import com.accenture.pocpinning.data.usecaseresult.UseCaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TodoMockDataSource(
    private val iTodoAPI: TodoMockAPI,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchTodoMockList():
            UseCaseResult<List<TodoResponse>> =
        withContext(ioDispatcher){
            iTodoAPI.fetchTodoList()
        }
}