package com.fire.god

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Route

/**
 * Description:
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/5/14
 **/
class HttpUtils private constructor() {

    companion object {
        private var httpUtils: HttpUtils? = null


        fun get1(): HttpUtils {
            if(httpUtils==null){
                httpUtils=HttpUtils()
            }
            return httpUtils!!
        }

    }



    fun t1():HttpUtils{

        return this
    }

    fun request(callBack:(result:String)->Unit,callback2:(result:String,result2:String)->Unit){

        Log.d("xxx0","_0当前线程----"+Thread.currentThread().name)
        val ts=GlobalScope.launch {
            Log.d("xxx0","_1当前线程----"+Thread.currentThread().name)
            withContext(Dispatchers.IO){

                Log.d("xxx0","0当前线程----"+Thread.currentThread().name)
                withContext(Dispatchers.Main){
                            callBack("测试数据")
                    Log.d("xxx0","1当前线程----"+Thread.currentThread().name)

                }
            }
        }


        ts.cancel()


    }


}