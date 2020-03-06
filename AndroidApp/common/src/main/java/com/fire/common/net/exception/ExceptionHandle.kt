package com.fire.common.net.exception

import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLException


/**
 * Description: 集中处理错误信息
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/3/6
 **/
object ExceptionHandle {

    fun handleException(e: Throwable): ReponseException {
        val ex: ReponseException
        if (e is HttpException) {
            ex = ReponseException(ERROR.HTTP_ERROR, e)
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException || e is MalformedJsonException
        ) {
            ex = ReponseException(ERROR.PARSE_ERROR, e)
        } else if (e is ConnectException) {
            ex = ReponseException(ERROR.NETWORD_ERROR, e)
        } else if (e is SSLException) {
            ex = ReponseException(ERROR.SSL_ERROR, e)
        } else if (e is SocketTimeoutException) {
            ex = ReponseException(ERROR.TIMEOUT_ERROR, e)
        } else if (e is UnknownHostException) {
            ex = ReponseException(ERROR.TIMEOUT_ERROR, e)
        } else {
            ex = if (!e.message.isNullOrEmpty()) ReponseException(1000, e.message!!, e)
            else ReponseException(ERROR.UNKNOWN, e)
        }
        return ex
    }
}