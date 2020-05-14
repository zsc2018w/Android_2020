package com.fire.god

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

}