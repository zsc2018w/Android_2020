package com.fire.common.component.bus;

import androidx.annotation.NonNull;

/**
 * Description:
 * Email: zhoushengchen@nxin.com
 *
 * @author: 周昇辰
 * Date: 2020/7/17
 **/
public final class ValueWrapper<T> {
    /**
     * 每个被观察的事件数据都有一个序号，只有产生的事件数据在观察者加入之后才通知到观察者
     * 即事件数据序号要大于观察者序号
     */
    final int sequence;
    @NonNull
    final T value;

    public ValueWrapper(@NonNull T value, int sequence) {
        this.value = value;
        this.sequence = sequence;

    }
}
