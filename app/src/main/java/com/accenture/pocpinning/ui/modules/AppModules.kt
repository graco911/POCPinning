package com.accenture.pocpinning.ui.modules

import android.content.Context
import com.accenture.pocpinning.ConnectionMode
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
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun createAppModules(connectionMode: ConnectionMode, pins: String): Module = module {
    single {
        createWebService<TodoAPI>(
            okHttpClient = createHttpClient(get(), connectionMode, pins),
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

fun createHttpClient(context: Context, mode: ConnectionMode, pinsConfig: String = ""): OkHttpClient {
    val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val builder = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(interceptor)

    when (mode) {
        ConnectionMode.NORMAL -> {
            // Sin configuración extra
        }

        ConnectionMode.INSECURE -> {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            builder.sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
        }

        ConnectionMode.PINNING -> {
            val pinnerBuilder = CertificatePinner.Builder()

            pinsConfig.split(",")
                .mapNotNull { entry ->
                    val parts = entry.split(":")
                    if (parts.size == 2) Pair(parts[0].trim(), parts[1].trim()) else null
                }
                .forEach { (domain, hash) ->
                    pinnerBuilder.add(domain, "sha256/$hash")
                }

            builder.certificatePinner(pinnerBuilder.build())
        }
    }

    return builder.build()
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