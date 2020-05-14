package com.fire.god



import android.util.Log
import androidx.lifecycle.ViewModel
import com.fire.common.base.BaseViewModel
import com.fire.common.net.http.HttpUtils
import kotlinx.coroutines.flow.onStart


/**
 * Description:
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/3/6
 **/
class My : BaseViewModel() {

    private val repository: TestService by lazy {
        HttpUtils.intance.createService(TestService::class.java)
    }

     fun getHomeData() {

        launchOnResult({ repository.getOne() }
            , {
                Log.d("xxx2","------------$it")
            }
        )

    }


/*    *//**
     * 简单处理请求回调结果
     *//*
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
    }*/


    fun test(block: (success:Boolean)->Unit){

        //sd//

        block(true)
    }


    private fun cc(){
        test {


        }
    }


    /**
     * 计数统计
     */
    fun justCount():() -> Unit{
        var count = 0
        return {
            println(count++)
        }
    }


    fun main(args: Array<String>) {

        val count = justCount()
        count()  // 输出结果：0
        count()  // 输出结果：1
        count()  // 输出结果：2
    }

}