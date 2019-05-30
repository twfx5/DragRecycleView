package com.wzh.dragrecycleview.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.wzh.dragrecycleview.MoveApp;

/**
 * <p/>
 * 屏幕像素转换工具类
 */
public class DisplayUtil {

    public static int screenWidth = 0;
    public static int screenHeight = 0;

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static float getDisplayDensity(Context context) {
        if (context == null) {
            return -1;
        }
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getScreenWidth() {
        if (screenWidth == 0) {
            DisplayMetrics dm = MoveApp.getInstance().getResources().getDisplayMetrics();
            screenWidth = dm.widthPixels;
        }
        return screenWidth;
    }

    public static int getScreenHeight() {
        if (screenHeight == 0) {
            DisplayMetrics dm = MoveApp.getInstance().getResources().getDisplayMetrics();
            screenHeight = dm.heightPixels;
        }
        return screenHeight;
    }

    public static float getDisplayDensity() {
        return MoveApp.getInstance().getResources().getDisplayMetrics().density;
    }

    public static int dp2px(float dipValue) {
        final float scale = getDisplayDensity();
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getAdjustHeight(int number, float scale) {
        final int paddingWidth = dp2px(10);
        return (int) ((getScreenWidth() / number - paddingWidth) / scale);
    }

    public static int getAdjustHeight(int number, float scale, int padding) {
        return (int) (((getScreenWidth() - dp2px(padding)) / number) / scale);
    }

    public static int getAdjustHeight(int number, float scale, int padding, int innerPadding) {
        return (int) (((getScreenWidth() - dp2px(padding)) / number - dp2px(innerPadding)) / scale);
    }

}
