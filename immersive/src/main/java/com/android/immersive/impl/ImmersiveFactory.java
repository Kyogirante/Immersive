package com.acfun.immersive.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

import com.acfun.immersive.interfaces.Immersive;
import com.acfun.immersive.utils.OSUtils;

/**
 * @author KyoWang
 * @since 2018/11/14
 */
public final class ImmersiveFactory {

  private ImmersiveFactory() {
    // no instance
  }

  public static Immersive createImmersiveAssist(Activity activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      return createV21ImmersiveAssist(activity);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      return createV19ImmersiveAssist(activity);
    } else {
      return Immersive.EMPTY;
    }
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private static Immersive createV21ImmersiveAssist(Activity activity) {
    if (OSUtils.isMiui()) {
      return new MiuiV21Immersive(activity);
    } else if (OSUtils.isFlyme()) {
      return new FlyMeV21Immersive(activity);
    } else {
      return new CommonV21Immersive(activity);
    }
  }

  private static Immersive createV19ImmersiveAssist(Activity activity) {
    if (OSUtils.isMiui()) {
      return new MiuiV19Immersive(activity);
    } else if (OSUtils.isFlyme()) {
      return new FlyMeV19Immersive(activity);
    } else {
      return new CommonV19Immersive(activity);
    }
  }

}
