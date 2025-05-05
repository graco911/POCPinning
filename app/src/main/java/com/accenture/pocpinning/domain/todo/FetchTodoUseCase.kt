package com.accenture.pocpinning.domain.todo

import com.accenture.pocpinning.data.enums.DataSource
import com.accenture.pocpinning.data.model.todo.TodoResponse
import com.accenture.pocpinning.data.repository.TodoRepository
import com.accenture.pocpinning.data.usecaseresult.UseCaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchTodoUseCase(
    private val todoRepository: TodoRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(
        dataSource: DataSource
    ) : UseCaseResult<List<TodoResponse>> {
        return withContext(defaultDispatcher) {
            when (dataSource) {
                DataSource.API -> todoRepository.fetchTodoList()
                DataSource.MOCK -> todoRepository.fetchTodoListMock()
            }
        }
    }
}