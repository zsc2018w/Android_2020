package com.fire.god;


import android.content.pm.ApplicationInfo;
import android.text.TextPaint;
import android.util.TypedValue;


/**
 * 设备信息工具类
 */
public class DeviceUtil {


    /**
     * 是否是开发模式
     */
    public static boolean isApkDebugable() {
        try {
            ApplicationInfo info = GlobalApplication.globalContext.getApplicationInfo();
            if (info != null) {
                return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    //获取文字需要显示的宽度
    public static float getTextWidth(String text, float Size) {
        TextPaint tp = new TextPaint();
        tp.setTextSize(sp2px(Size));
        return tp.measureText(text);
    }


    //设置字体大小如20sp
    public static float applyDimension(int unit, float value) {
        return TypedValue.applyDimension(unit, value, GlobalApplication.globalContext.getResources().getDisplayMetrics());
    }

    //根据手机的分辨率从 dp 的单位 转成为 px(像素)
    public static int dp2px(float dpValue) {
        float scale = GlobalApplication.globalContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //根据手机的分辨率从 px(像素) 的单位 转成为 dp
    public static int px2dp(float pxValue) {
        float scale = GlobalApplication.globalContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //将sp值转换为px值
    public static float sp2px(float spValue) {
        float fontScale = GlobalApplication.globalContext.getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale;
    }

    //将px值转换为sp值
    public static float px2sp(float pxValue) {
        float fontScale = GlobalApplication.globalContext.getResources().getDisplayMetrics().scaledDensity;
        return pxValue / fontScale;
    }




}
