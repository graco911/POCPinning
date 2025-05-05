package com.accenture.pocpinning.data.model.response

abstract class ResponseBase(
    var statusCode: Int = Codes.NotImplemented.code,
    var codeName: Codes = Codes.NotImplemented,
    var message: String? = null,
    var errorNumber: Int? = null,
    var isSuccess: Boolean = false
) {
    val serverName: String
        get() = System.getProperty("os.name") ?: "Unknown"

    init {
        codeName = Codes.values().firstOrNull { it.code == statusCode } ?: Codes.NotImplemented
    }
}

enum class Codes(val code: Int) {
    NotImplemented(0),
    Success(200),
    Error(500)
}