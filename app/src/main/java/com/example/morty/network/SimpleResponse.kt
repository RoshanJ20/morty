package com.example.morty.network

import retrofit2.Response
import java.lang.Exception

data class SimpleResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val exception: Exception?
){

    companion object{
        fun <T> success(response: Response<T>): SimpleResponse<T> {
            return SimpleResponse(
                status = Status.Success,
                data = response,
                exception = null
            )
        }

        fun <T> failure(exception: Exception): SimpleResponse<T> {
            return SimpleResponse(
                status = Status.Error,
                data = null,
                exception = exception
            )
        }


    }
    sealed class Status{
        object Success: Status()
        object Error: Status()
    }


    val bodyNullable: T?
        get() = this.data?.body()

    val failed: Boolean
        get() = this.status is Status.Error

    val isSuccessful: Boolean
        get() = !failed && this.status is Status.Success

    val body: T
        get() = data?.body()!!
}
