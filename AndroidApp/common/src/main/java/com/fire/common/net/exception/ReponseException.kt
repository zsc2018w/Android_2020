package com.fire.common.net.exception


/**
 * Description: 错误信息model
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/3/7
 **/
class ReponseException : Exception {

    var code: Int
    var errMsg: String

    constructor(error: ERROR, e: Throwable? = null) : super(e) {
        code = error.getKey()
        errMsg = error.getValue()
    }

    constructor(code: Int, msg: String, e: Throwable? = null) : super(e) {
        this.code = code
        this.errMsg = msg
    }
}