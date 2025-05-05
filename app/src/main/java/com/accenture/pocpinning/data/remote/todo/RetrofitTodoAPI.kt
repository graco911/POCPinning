package com.accenture.pocpinning.data.remote.todo

import com.accenture.pocpinning.data.model.todo.TodoResponse
import com.accenture.pocpinning.data.remote.baseapi.BaseApi
import com.accenture.pocpinning.data.usecaseresult.UseCaseResult

class RetrofitTodoAPI(private val todoAPI: TodoAPI) : BaseApi(), ITodoAPI {

    override suspend fun fetchTodoList(
    ): UseCaseResult<List<TodoResponse>> {
        return executeRequest {
            todoAPI.fetchTodoList()
        }
    }
}