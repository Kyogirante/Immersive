package com.android.immersive.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewConfiguration;

/**
 * @author KyoWang
 * @since 2018/11/14
 */
public final class SystemDimenUtil {

  private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
  private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
  private static final String NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape";
  private static final String NAV_BAR_WIDTH_RES_NAME = "navigation_bar_width";

  private SystemDimenUtil() {
    // no instance
  }
  public static int getStatusBarHeight(Context context) {
    return getInternalDimensionSize(context.getResources(), STATUS_BAR_HEIGHT_RES_NAME);
  }

  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
  public static int getActionBarHeight(Context context) {
    int result = 0;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
      TypedValue tv = new TypedValue();
      context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
      result = context.getResources().getDimensionPixelSize(tv.resourceId);
    }
    return result;
  }

  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
  public static int getNavigationBarHeight(Context context, boolean inPortrait) {
    Resources res = context.getResources();
    int result = 0;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
      if (!ViewConfiguration.get(context).hasPermanentMenuKey()) {
        String key;
        if (inPortrait) {
          key = NAV_BAR_HEIGHT_RES_NAME;
        } else {
          key = NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME;
        }
        return getInternalDimensionSize(res, key);
      }
    }
    return result;
  }

  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
  public static int getNavigationBarWidth(Context context) {
    Resources res = context.getResources();
    int result = 0;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
      if (!ViewConfiguration.get(context).hasPermanentMenuKey()) {
        return getInternalDimensionSize(res, NAV_BAR_WIDTH_RES_NAME);
      }
    }
    return result;
  }

  public static int getInternalDimensionSize(Resources res, String key) {
    int result = 0;
    int resourceId = res.getIdentifier(key, "dimen", "android");
    if (resourceId > 0) {
      result = res.getDimensionPixelSize(resourceId);
    }
    return result;
  }

  @SuppressLint("NewApi")
  public static float getSmallestWidthDp(Activity activity) {
    DisplayMetrics metrics = new DisplayMetrics();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
    } else {
      // TODO this is not correct, but we don't really care pre-kitkat
      activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }
    float widthDp = metrics.widthPixels / metrics.density;
    float heightDp = metrics.heightPixels / metrics.density;
    return Math.min(widthDp, heightDp);
  }

}
