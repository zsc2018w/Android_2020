package com.fire.common.base

import android.app.Application
import android.content.Context

/**
 * Description: 基础的 application
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/3/6
 **/
open class BaseApplication : Application() {
    companion object{
        lateinit var application: Application
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        application = this

    }
}