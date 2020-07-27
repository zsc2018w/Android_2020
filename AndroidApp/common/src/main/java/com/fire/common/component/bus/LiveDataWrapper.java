package com.fire.common.component.bus;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

/**
 * Description:
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/7/17
 **/
public interface LiveDataWrapper <T>{


    /**
     * @return 是否有观察者
     */
    boolean hasObservers();

    /**
     * @return 是否有激活状态的观察者
     */
    boolean hasActiveObservers();

    /**
     * 发送消息
     * @param value 值
     */
    void post(@NonNull T value);

    /**
     * 移除观察者
     */
    void removeObserver(@NonNull ObserverWrapper<T> observerWrapper);
}
