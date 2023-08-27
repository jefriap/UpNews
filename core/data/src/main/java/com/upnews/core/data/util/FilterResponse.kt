package com.upnews.core.data.util

import com.upnews.core.network.model.BaseResponse
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> getResponse(
    from: suspend () -> BaseResponse<T>,
): Response<T> {
    return try {
        val response = from()
        if (response.data != null)
            Response.Success(
                data = response.data!!,
                message = response.message ?: ""
            )
        else {
            Response.SuccessButEmpty(message = response.message ?: "")
        }
    } catch (e: IOException) {
        Response.ConnectionError("Koneksi buruk")
    } catch (e: HttpException) {
        e.generateErrorServer()
    }
}

private fun <T> HttpException.generateErrorServer(): Response.ServerError<T> {
    return try {
        val message = response()?.errorBody()?.string()?.let {
            JSONObject(it).getJSONObject("message").getString("id")
        }
        Response.ServerError(
            message = message ?: "Server Error",
            errorCode = code()
        )
    } catch (e: Throwable) {
        Response.ServerError(
            message = response()?.errorBody()?.string() ?: "Server Error",
            errorCode = code()
        )
    }
}


/**
 * Representative of Response State
 */
sealed class Response<out T> {
    data class Success<T>(val data: T, val message: String) : Response<T>()
    data class SuccessButEmpty<T>(val message: String) : Response<T>()
    data class ConnectionError<T>(val message: String) : Response<T>()
    data class ServerError<T>(val message: String, val errorCode: Int) : Response<T>()
}