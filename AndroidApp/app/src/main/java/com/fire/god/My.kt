package com.fire.god



import android.util.Log
import androidx.lifecycle.ViewModel
import com.fire.common.base.BaseViewModel
import com.fire.common.net.http.HttpUtils


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

        lauchOnResult({ repository.getOne() }
            , {
                Log.d("xxx2","------------$it")
            }
        )
    }
}