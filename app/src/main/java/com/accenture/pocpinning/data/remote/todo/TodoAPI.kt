package com.accenture.pocpinning.data.remote.todo

import com.accenture.pocpinning.data.model.todo.TodoResponse
import retrofit2.Response
import retrofit2.http.GET

interface TodoAPI {

    /**
     * Fetches a list of todo items from the API.
     *
     * @return A list of [TodoResponse] objects representing the todo items.
     */
    @GET("todos")
    suspend fun fetchTodoList(): Response<List<TodoResponse>>
}