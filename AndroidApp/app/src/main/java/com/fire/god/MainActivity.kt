package com.fire.god

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fire.common.base.BaseApplication
import com.fire.common.utils.Common
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    var vm:My?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



         vm =     ViewModelProvider.NewInstanceFactory().create(My::class.java)
            //ViewModelProvider.AndroidViewModelFactory(BaseApplication.application).create(My::class.java)
        vm?.getHomeData()

        GlobalScope.launch {

        }

        vm?.test {

        }

        HttpUtils.getInstance()
    }


    override fun onDestroy() {
        super.onDestroy()

    }
}
