package com.fire.common.component.bus;

import androidx.lifecycle.Observer;

/**
 * Description:
 * Email: zhoushengchen@nxin.com
 *
 * @author: 周昇辰
 * Date: 2020/7/17
 */
public abstract class ObserverWrapper<T> {
    /**
     * 每个观察者都记录自己序号，只有在进入观察状态之后产生的数据才通知到观察者
     */
    int sequence;

    /**
     * 观察者
     */
    Observer<ValueWrapper<T>> observer;

    /**
     * 默认不是粘性事件，不会收到监听之前发送的事件
     */
    boolean sticky = false;
    /**
     * 默认在主线程监听
     */
    boolean uiThread = true;
}
