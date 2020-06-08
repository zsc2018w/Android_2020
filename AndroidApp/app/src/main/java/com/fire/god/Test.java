package com.fire.god;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/**
 * Description:
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/5/14
 **/
public class Test  {

    private Test() {
    }

    public static Test getInstance(){

     return new Test();
    }


    public void tx(){

       HttpUtils.Companion.get1().request(new Function1<String, Unit>() {
           @Override
           public Unit invoke(String s) {
               return null;
           }
       }, new Function2<String, String, Unit>() {
           @Override
           public Unit invoke(String s, String s2) {
               return null;
           }
       });


    }

    public void cc(String str){

    }



}
