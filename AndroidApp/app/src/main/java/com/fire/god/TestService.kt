package com.fire.god


import retrofit2.http.GET


/**
 * Description:
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/3/6
 **/
interface TestService {

    @GET("http://baobab.kaiyanapp.com/api/v4/categories/all")
    suspend fun getOne():String
}