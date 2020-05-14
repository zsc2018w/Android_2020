package com.fire.god

import android.app.Application
import com.fire.common.base.BaseApplication

/**
 * Description:
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/3/25
 **/
class GlobalApplication :BaseApplication() {

    companion object{
       lateinit var  globalContext:Application
    }
    override fun onCreate() {
        super.onCreate()
        globalContext=this
    }
}