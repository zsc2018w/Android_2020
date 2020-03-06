package com.fire.common.net.http

import com.fire.common.extension.isNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Description:
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/3/6
 **/
class HttpUtils private constructor() {
    //retrofit
    private var mRetrofit: Retrofit? = null
    //okHttpClient
    private var okHttpClient: OkHttpClient? = null

    companion object {
        private val TIMEOUT: Long = 10
        val intance: HttpUtils = HttpUtils()
    }

    private fun initOkHttp() {
        if (okHttpClient.isNull()) {
            okHttpClient = OkHttpClient.Builder()
                //  .addInterceptor(logInterceptor)
                //    .addInterceptor(addQueryParameterInterceptor())
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        }
    }

    private fun initRetrofit() {
        initOkHttp()
       if(mRetrofit.isNull()){
           mRetrofit = Retrofit.Builder()
                //因为baseurl 不能为 空 随便传入一个
               .baseUrl("https://blog.csdn.net/")
               .client(okHttpClient!!)
                //转换字符串返回这个必须要放在上面否则解析异常 估计没走到它   nb  nb
               .addConverterFactory(ScalarsConverterFactory.create())
               .addConverterFactory(GsonConverterFactory.create())
               //  .addConverterFactory(LenientGsonConverterFactory.create())
               .build()
       }
    }


     fun <T> createService(cls:Class<T>):T{
         //检查初始化
         initRetrofit()
        return mRetrofit!!.create(cls)
    }

}