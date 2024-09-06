package com.dika.moviecompose.network

import android.util.Log
import androidx.compose.runtime.Composable
import com.dika.moviecompose.cons.Constants.API_FAILURE_CODE
import com.dika.moviecompose.cons.Constants.API_INTERNET_CODE
import com.dika.moviecompose.cons.Constants.API_INTERNET_MESSAGE
import com.dika.moviecompose.cons.Constants.API_SOMETHING_WENT_WRONG_MESSAGE
import com.dika.moviecompose.di.ApiStatus
import com.dika.moviecompose.di.NetWorkResult
import kotlin.reflect.full.memberProperties



@Composable
fun <T> ApiResultHandler(
    result: NetWorkResult<T?>,
    onLoading: @Composable () -> Unit,
    onSuccess: @Composable (T?) -> Unit,
    onFailure: @Composable (T?) -> Unit
) {
    // Menangani hasil API berdasarkan status
    when (result.status) {
        ApiStatus.LOADING -> {
            onLoading()
        }
        ApiStatus.SUCCESS -> {
            onSuccess(result.data)
        }
        ApiStatus.ERROR -> {
            onFailure(result.data)
            when (result.data?.getField<String>("ErrorCode") ?: "0") {
                API_FAILURE_CODE -> {
                    Log.e("TAG", "handleApiResult Code: ${result.message}")
                    // Bisa tambahkan tampilan dialog atau snack bar disini
                }
                API_INTERNET_CODE -> {
                    Log.e("TAG", "handleApiResult Internet: $API_INTERNET_MESSAGE")
                    // Bisa tambahkan tampilan dialog atau snack bar disini
                }
                else -> {
                    Log.e("TAG", "handleApiResult Else: $API_SOMETHING_WENT_WRONG_MESSAGE")
                    // Bisa tambahkan tampilan dialog atau snack bar disini
                }
            }
        }
    }
}

@Throws(IllegalAccessException::class, ClassCastException::class)
inline fun <reified T> Any.getField(fieldName: String): T? {
    this::class.memberProperties.forEach { kCallable ->
        if (fieldName == kCallable.name) {
            return kCallable.getter.call(this) as T?
        }
    }
    return null
}