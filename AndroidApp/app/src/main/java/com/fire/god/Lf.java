package com.fire.god;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Description:
 * Email: zhoushengchen@nxin.com
 * Author: 周昇辰
 * Date: 2020/6/18
 **/
public class Lf implements LifecycleEventObserver {
    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        boolean no = source.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED);
        Log.d("life_", "回调-----》" + event.name());
    }


}
