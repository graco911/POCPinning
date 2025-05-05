package com.accenture.pocpinning.data.mock.todo

import com.accenture.pocpinning.data.model.todo.TodoResponse
import com.accenture.pocpinning.data.remote.todo.ITodoAPI
import com.accenture.pocpinning.data.usecaseresult.UseCaseResult

class TodoMockAPI() : ITodoAPI {
    override suspend fun fetchTodoList(): UseCaseResult<List<TodoResponse>> {
        return UseCaseResult.Success(
            listOf(
                TodoResponse(
                    id = 1,
                    userId = 1,
                    title = "Todo 1",
                    completed = false
                ),
                TodoResponse(
                    id = 2,
                    userId = 1,
                    title = "Todo 2",
                    completed = true
                ),
                TodoResponse(
                    id = 3,
                    userId = 1,
                    title = "Todo 2",
                    completed = true
                ),
                TodoResponse(
                    id = 4,
                    userId = 1,
                    title = "Todo 2",
                    completed = true
                ),
                TodoResponse(
                    id = 5,
                    userId = 1,
                    title = "Todo 2",
                    completed = true
                )
            )
        )
    }
}