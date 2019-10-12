package com.acfun.immersive.impl;

import java.lang.reflect.Field;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import com.acfun.immersive.interfaces.Immersive;

/**
 * @author KyoWang
 * @since 2018/11/14
 */
class FlyMeV19Immersive extends BaseV19Immersive {

  FlyMeV19Immersive(Activity activity) {
    super(activity);
  }

  @Override
  public Immersive setStatusBarFontStyle(boolean isDark) {
    try {
      Window window = activity.getWindow();
      WindowManager.LayoutParams lp = window.getAttributes();
      Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
      Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
      darkFlag.setAccessible(true);
      meizuFlags.setAccessible(true);
      int bit = darkFlag.getInt(null);
      int value = meizuFlags.getInt(lp);
      if (isDark) {
        value |= bit;
      } else {
        value &= ~bit;
      }
      meizuFlags.setInt(lp, value);
      window.setAttributes(lp);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return this;
  }
}
