package com.android.immersive.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

import com.android.immersive.interfaces.Immersive;
import com.android.immersive.utils.OSUtils;

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

}
