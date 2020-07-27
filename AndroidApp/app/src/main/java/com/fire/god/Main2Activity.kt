package com.fire.god

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import androidx.lifecycle.Observer
import cody.bus.ElegantBus
import cody.bus.ObserverWrapper
import com.fire.common.component.eventbus.Live11111Bus


class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        lifecycle.addObserver(Lf())

        val file=getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        log("图片-->$file")


        val cache=getExternalCacheDir()
        log("缓存-->$cache")


        val storage=Environment.getExternalStorageDirectory()
        log("外部-->$storage")


        val sd=Environment.getDownloadCacheDirectory()
        log("sd-->$sd")



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q&&!Environment.isExternalStorageLegacy()) {

        }

        Live11111Bus.getDefault("cs",String::class.java).postValue("测试数据数据数据")
        Live11111Bus.getDefault("cs",String::class.java).observe(this,
            Observer<String> { t -> log("sd-->$t") })
         // Handler.createAsync()


        ElegantBus.getDefault("ss").post("ss")


    }



    fun log(str:String){
        Log.d("scope_","test--->$str")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
