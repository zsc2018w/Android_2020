package com.fire.common.utils

import androidx.lifecycle.ViewModelProvider
import com.fire.common.base.BaseApplication
import com.fire.common.base.BaseViewModel

/**
 * Description:
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/3/9
 **/
object  Common {

    /**
     * 获取VM
     */
    fun <T : BaseViewModel> getVM(cls: Class<T>): T? {
        return ViewModelProvider.AndroidViewModelFactory(BaseApplication.application).create(cls)
    }
}