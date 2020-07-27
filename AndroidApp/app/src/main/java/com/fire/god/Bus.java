package com.fire.god;

import cody.bus.annotation.Event;
import cody.bus.annotation.EventGroup;

/**
 * Description:
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/7/18 
 **/

@EventGroup(name = "app_type1",active = true)
public class Bus {


    @Event(value = "test1")
   String testOne;

}
