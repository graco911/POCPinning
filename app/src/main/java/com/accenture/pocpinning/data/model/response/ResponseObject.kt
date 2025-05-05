package com.accenture.pocpinning.data.model.response

data class ResponseObject<T>(
    var data: T? = null
) : ResponseBase()