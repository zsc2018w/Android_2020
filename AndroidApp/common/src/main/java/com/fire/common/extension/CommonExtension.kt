package com.fire.common.extension

/**
 * Description: 基础的扩展方法
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/3/6
 **/


/**
 * 判断当前引用是不是null
 */
fun Any?.isNull(): Boolean {
    return this == null
}