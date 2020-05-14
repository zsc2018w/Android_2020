package com.fire.common.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import com.fire.common.net.exception.ExceptionHandle
import com.fire.common.net.exception.ReponseException
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import java.lang.Exception


/**
 * Description: 基础的vm
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/3/6
 **/
open class BaseViewModel : AndroidViewModel(BaseApplication.application), LifecycleObserver {


    /**
     * 所有网络请求在 viewModelScope
     * 页面销毁 自动调用 ViewModel #onCleared 方法取消所有协程
     */
    private fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }


    /**
     * 流的方式请求
     */
    fun <T> launchFlow(block:suspend ()->T):Flow<T>{
       return flow {
         emit(block())
       }
    }


    /**
     * 简单处理请求回调结果
     */
    protected fun <T> launchOnResult(
        block: suspend CoroutineScope.() -> T,
        onSuccess:  (T) -> Unit,
        onError: CoroutineScope.(ReponseException) -> Unit={},
        onComplete: CoroutineScope.() -> Unit={}
    ) {
        launchUI {
            handlerException({
                withContext(Dispatchers.IO) {
                    block()
                }
            }, {
                onSuccess(it)
            }, {
                onError(it)
            }, {
                onComplete()
            })

        }
    }

    /**
     * 统一规范回调处理
     */
    private suspend fun <T> handlerException(
        block: suspend CoroutineScope.() -> T,
        onSuccess: suspend CoroutineScope.(T) -> Unit,
        onError: suspend CoroutineScope.(ReponseException) -> Unit,
        onComplete: CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                onSuccess(block())
            } catch (e: Exception) {
                onError(ExceptionHandle.handleException(e))
            } finally {
                onComplete()
            }
        }
    }


}