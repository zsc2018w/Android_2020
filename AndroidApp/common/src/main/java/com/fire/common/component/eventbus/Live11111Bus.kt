package com.fire.common.component.eventbus

import androidx.lifecycle.MutableLiveData

/**
 * Description: event
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/7/9
 **/
class Live11111Bus private constructor() {

    private var busMap: LinkedHashMap<String, MutableLiveData<Any>> = LinkedHashMap()

    companion object {

        private val INSTANCE: Live11111Bus = Live11111Bus()

        fun <T> getDefault(key: String, clazz: Class<T>): MutableLiveData<T> {
            return INSTANCE.with(key, clazz)
        }

    }


    private fun <T> with(key: String, clazz: Class<T>): MutableLiveData<T> {
        if (!busMap.containsKey(key)) {
            val liveData = MutableLiveData<Any>()
            busMap[key] = liveData
        }
        return busMap[key] as MutableLiveData<T>
    }
}