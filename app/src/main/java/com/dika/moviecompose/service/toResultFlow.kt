package com.dika.moviecompose.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.dika.moviecompose.cons.Constants.API_FAILED_CODE
import com.dika.moviecompose.cons.Constants.API_INTERNET_CODE
import com.dika.moviecompose.cons.Constants.API_INTERNET_MESSAGE
import com.dika.moviecompose.di.NetWorkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.net.SocketTimeoutException
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties


inline fun <reified T> toResultFlow(
    context: Context,
    crossinline call: suspend () -> Response<T>?
): Flow<NetWorkResult<T>> {
    return flow {
        if (!hasInternetConnection(context)) {
            val model = setResponseStatus<T>(
                T::class.java.getDeclaredConstructor().newInstance(),
                API_INTERNET_CODE,
                API_INTERNET_MESSAGE
            )
            emit(NetWorkResult.Error(model, API_INTERNET_MESSAGE))
            return@flow
        }

        emit(NetWorkResult.Loading(true))

        try {
            val response = call()
            if (response != null && response.isSuccessful && response.body() != null) {
                emit(NetWorkResult.Success(response.body()!!))
            } else {
                val model = setResponseStatus(
                    T::class.java.getDeclaredConstructor().newInstance(),
                    response?.code().toString(),
                    response?.message()
                )
                emit(NetWorkResult.Error(model, response?.message().toString()))
            }
        } catch (e: SocketTimeoutException) {
            val model = setResponseStatus<T>(
                T::class.java.getDeclaredConstructor().newInstance(),
                API_FAILED_CODE,
                e.message
            )
            emit(NetWorkResult.Error(model, e.toString()))
        } catch (e: Exception) {
            val model = setResponseStatus<T>(
                T::class.java.getDeclaredConstructor().newInstance(),
                API_FAILED_CODE,
                e.message
            )
            emit(NetWorkResult.Error(model, e.toString()))
        }
    }.flowOn(Dispatchers.IO)
}

inline fun <reified T> setResponseStatus(instance: T?, errorCode: String?, message: String?):T? {
    return try {
        instance?.let {
            val properties = it::class.memberProperties
            for (property in properties) {
                if (property is KMutableProperty<*>) {
                    when (property.name) {
                        "ErrorCode" -> (property as KMutableProperty<*>).setter.call(instance, errorCode)
                        "Message" -> (property as KMutableProperty<*>).setter.call(instance, message)
                    }
                }
            }
        }
        instance
    } catch (e: Exception) {
        null
    }
}


fun hasInternetConnection(context: Context?): Boolean {
    try {
        if (context == null)
            return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } catch (e: Exception) {
        return false
    }
}
