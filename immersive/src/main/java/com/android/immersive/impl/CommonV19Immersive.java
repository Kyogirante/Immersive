package com.android.immersive.impl;

import android.app.Activity;

import com.android.immersive.interfaces.Immersive;

/**
 * @author KyoWang
 * @since 2018/11/14
 */
class CommonV19Immersive extends BaseV19Immersive {

  CommonV19Immersive(Activity activity) {
    super(activity);
  }

  @Override
  public Immersive setStatusBarFontStyle(boolean isDark) {
    return this;
  }
}
