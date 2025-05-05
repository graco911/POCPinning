package com.accenture.pocpinning.data.remote.baseapi

import com.accenture.pocpinning.data.usecaseresult.UseCaseResult
import retrofit2.Response

abstract class BaseApi {

    // Implementación de request genérico
    protected suspend fun <T : Any> executeRequest(apiCall: suspend () -> Response<T>): UseCaseResult<T> {
        return try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    UseCaseResult.Success(body)
                } else {
                    UseCaseResult.Error(Exception("Response body is null"))
                }
            } else {
                UseCaseResult.Error(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }
}