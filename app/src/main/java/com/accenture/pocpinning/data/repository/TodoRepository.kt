package com.accenture.pocpinning.data.repository

import com.accenture.pocpinning.data.mock.todo.TodoMockDataSource
import com.accenture.pocpinning.data.model.todo.TodoResponse
import com.accenture.pocpinning.data.remote.todo.TodoAPIDataSource
import com.accenture.pocpinning.data.usecaseresult.UseCaseResult

class TodoRepository(
    private val todoAPIDataSource: TodoAPIDataSource,
    private val todoMockDataSource: TodoMockDataSource
) {

    suspend fun fetchTodoList(): UseCaseResult<List<TodoResponse>> {
        return todoAPIDataSource.fetchTodoList()
    }

    suspend fun fetchTodoListMock(): UseCaseResult<List<TodoResponse>> {
        return todoMockDataSource.fetchTodoMockList()
    }
}