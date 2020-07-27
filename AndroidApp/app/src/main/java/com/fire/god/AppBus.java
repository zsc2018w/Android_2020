package com.fire.god;

import cody.bus.annotation.Event;
import cody.bus.annotation.EventGroup;

/**
 * Description:
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/7/18
 **/

@EventGroup(name = "App", active = true)
public class AppBus {

    @Event(value = "登录状态变化")
    Object loginChange;

    @Event(value = "网络变化")
    String netChange;


    @Event(value = "测试")
    String test;
}
