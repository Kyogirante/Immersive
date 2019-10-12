package com.android.immersive.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;

import com.android.immersive.interfaces.Immersive;
import com.android.immersive.utils.OSUtils;

/**
 * @author KyoWang
 * @since 2018/11/14
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class MiuiV21Immersive extends BaseV21Immersive {

  private static final long SPECIAL_VERSION = 1499875200L;

  MiuiV21Immersive(Activity activity) {
    super(activity);
  }

  @Override
  public Immersive setStatusBarFontStyle(boolean isDark) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && OSUtils.getRomTime() > SPECIAL_VERSION) {
      super.setStatusBarFontStyle(isDark);
    } else {
      Class<? extends Window> clazz = activity.getWindow().getClass();
      try {
        int darkModeFlag = 0;
        Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
        Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
        darkModeFlag = field.getInt(layoutParams);
        Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
        extraFlagField.invoke(activity.getWindow(), isDark ? darkModeFlag : 0, darkModeFlag);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return this;
  }
}
