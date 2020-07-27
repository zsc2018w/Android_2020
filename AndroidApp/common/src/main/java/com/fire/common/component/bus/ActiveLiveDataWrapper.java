package com.fire.common.component.bus;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

/**
 * Description:
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/7/17
 **/
public class ActiveLiveDataWrapper<T> implements LiveDataWrapper<T> {
    private int mSequence = 0;
    private final MutableLiveData<ValueWrapper<T>> mutableLiveData;

    public ActiveLiveDataWrapper() {
        this.mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public boolean hasObservers() {
        return false;
    }

    @Override
    public boolean hasActiveObservers() {
        return false;
    }

    @Override
    public void post(@NonNull T value) {

    }

    @Override
    public void removeObserver(@NonNull ObserverWrapper<T> observerWrapper) {

    }
}
