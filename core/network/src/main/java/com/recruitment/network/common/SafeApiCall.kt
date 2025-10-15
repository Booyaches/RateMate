package com.recruitment.network.common

import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.EOFException
import java.io.IOException
import java.net.SocketTimeoutException

suspend inline fun <T> safeApiCall(crossinline block: suspend () -> T): NetworkResult<T> {
    return try {
        NetworkResult.Success(block())
    } catch (ce: CancellationException) {
        throw ce
    } catch (he: HttpException) {
        val code = he.code()
        val msg = he.message()
        val body = try { he.response()?.errorBody()?.string() } catch (_: Throwable) { null }
        NetworkResult.Error(NetworkError.Http(code, msg, body))
    } catch (te: SocketTimeoutException) {
        NetworkResult.Error(NetworkError.Timeout(te))
    } catch (io: IOException) {
        NetworkResult.Error(NetworkError.NetworkUnavailable(io))
    } catch (se: EOFException) {
        NetworkResult.Error(NetworkError.Serialization(se))
    } catch (e: Exception) {
        NetworkResult.Error(NetworkError.Unknown(e))
    }
}