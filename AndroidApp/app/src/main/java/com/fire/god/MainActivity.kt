package com.fire.god

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.fire.common.base.BaseApplication


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mViewModel =     ViewModelProvider.NewInstanceFactory().create(My::class.java)

            //ViewModelProvider.AndroidViewModelFactory(BaseApplication.application).create(My::class.java)

        mViewModel.getHomeData()


    }
}
