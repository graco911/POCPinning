package com.accenture.pocpinning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.accenture.pocpinning.data.enums.DataSource
import com.accenture.pocpinning.data.model.todo.TodoResponse
import com.accenture.pocpinning.data.usecaseresult.UseCaseResult
import com.accenture.pocpinning.domain.todo.FetchTodoUseCase
import com.accenture.pocpinning.ui.theme.POCPinningTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val fetchTodoUseCase: FetchTodoUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var todoList by mutableStateOf<List<TodoResponse>>(emptyList())

        lifecycleScope.launch {
            val result = fetchTodoUseCase(DataSource.API)

            when (result) {
                is UseCaseResult.Success -> {
                    todoList = result.data
                }

                is UseCaseResult.Error -> {
                    println("Error: ${result.exception}")
                }
            }

            setContent {
                POCPinningTheme {
                    setContent {
                        TodoListScreen(todoList)
                    }
                }
            }

        }
    }
}

@Composable
fun TodoListScreen(todoList: List<TodoResponse>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(todoList) { todo ->
            TodoItemView(todo)
        }
    }
}

@Composable
fun TodoItemView(todo: TodoResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = todo.title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            Text(
                text = todo.completed.toString(),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            )
        }
    }
}