package com.android.immersive.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Window;

import com.android.immersive.interfaces.Immersive;

/**
 * @author KyoWang
 * @since 2018/11/14
 */
class MiuiV19Immersive extends BaseV19Immersive {

  MiuiV19Immersive(Activity activity) {
    super(activity);
  }

  @Override
  @SuppressLint("PrivateApi")
  public Immersive setStatusBarFontStyle(boolean isDark) {
    try {
      Window window = activity.getWindow();
      Class<?> clazz = activity.getWindow().getClass();
      Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
      Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
      int darkModeFlag = field.getInt(layoutParams);
      Method extraFlagField = clazz.getDeclaredMethod("setExtraFlags", int.class, int.class);
      extraFlagField.setAccessible(true);
      if (isDark) { // 状态栏亮色且黑色字体
        extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
      } else {
        extraFlagField.invoke(window, 0, darkModeFlag);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return this;
  }
}
