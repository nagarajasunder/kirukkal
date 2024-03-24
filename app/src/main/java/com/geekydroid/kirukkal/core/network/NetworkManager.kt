package com.geekydroid.kirukkal.core.network

import kotlinx.coroutines.delay
import okhttp3.ResponseBody
import retrofit2.Response

sealed class NetworkResult<T> {
    data class Success<T>(val data: T, val code: Int) : NetworkResult<T>()
    data class Error<T>(val data: T?, val errorBody: ResponseBody?, val errorMessage: String, val code: Int) : NetworkResult<T>()
    data class Exception<T>(val throwable: Throwable, val errorMessage:String) : NetworkResult<T>()
}

suspend fun <T> makeNetworkCall(
    request : suspend () -> Response<T>,
) : NetworkResult<T> {
    return try {
        val result = retryWithTimeout(request = request)
        if (result.isSuccessful && result.body() != null) {
            NetworkResult.Success(data = result.body()!!, code = result.code())
        } else {
            NetworkResult.Error(
                data = result.body(),
                errorBody = result.errorBody(),
                errorMessage = "",
                code = result.code()
            )
        }
    } catch (e : Exception) {
        NetworkResult.Exception(throwable = e, errorMessage = "")
    }
}

private suspend fun <T>retryWithTimeout(
    request: suspend () -> Response<T>,
    retryCounts:Int = 3,
    delayMillis:Long = 2_000,
    increaseFactor: Float = 2.0f,
    maxDelayMillis:Long = 5_000
) : Response<T> {
    var initialDelay = delayMillis
    repeat(retryCounts-1) {
        try {
            val result = request()
            if (result.isSuccessful && result.body() != null) {
                return result
            } else if (result.code() !in 500..599) {
                return result
            }
        } catch (e:Exception) {
            return request()
        }
        delay(initialDelay)
        initialDelay = (initialDelay*increaseFactor).coerceAtMost(maxDelayMillis.toFloat()).toLong()
    }
    return request()
}