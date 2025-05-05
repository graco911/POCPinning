package com.accenture.pocpinning.ui.modules

import android.content.Context
import com.accenture.pocpinning.data.mock.todo.TodoMockAPI
import com.accenture.pocpinning.data.mock.todo.TodoMockDataSource
import com.accenture.pocpinning.data.remote.todo.ITodoAPI
import com.accenture.pocpinning.data.remote.todo.RetrofitTodoAPI
import com.accenture.pocpinning.data.remote.todo.TodoAPI
import com.accenture.pocpinning.data.remote.todo.TodoAPIDataSource
import com.accenture.pocpinning.data.repository.TodoRepository
import com.accenture.pocpinning.domain.todo.FetchTodoUseCase
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createAppModules(): Module = module {
    single {
        createWebService<TodoAPI>(
            okHttpClient = createHttpClient(get()),
            baseUrl = "https://jsonplaceholder.typicode.com/"
        )
    }

    single { Dispatchers.IO }

    single<ITodoAPI> { RetrofitTodoAPI(get()) }

    single { TodoAPIDataSource(get(), get()) }
    single { TodoMockAPI() }
    single { TodoMockDataSource(get(), get()) }
    single { TodoRepository(get(), get()) }
    single { FetchTodoUseCase(get(), get()) }
}

fun createHttpClient(context: Context): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.MINUTES)
        .retryOnConnectionFailure(true)
        .addInterceptor(interceptor)
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
        .build()
}

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}