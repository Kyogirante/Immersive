package com.android.immersive.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;

import com.android.immersive.R;
import com.android.immersive.interfaces.Immersive;

/**
 * @author KyoWang
 * @since 2018/11/14
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class CommonV21Immersive extends BaseV21Immersive {

  CommonV21Immersive(Activity activity) {
    super(activity);
  }

  @Override
  public Immersive setStatusBarFontStyle(boolean isDark) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      View decorView = activity.getWindow().getDecorView();
      if (decorView != null) {
        int uiOptions = decorView.getSystemUiVisibility();
        if (isDark) {
          uiOptions |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
          uiOptions &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        if (decorView.getSystemUiVisibility() != uiOptions) {
          decorView.setSystemUiVisibility(uiOptions);
        }
      }
    } else if (isDark) {
      setStatusBarColor(R.color.immersive_gray);
    }
    return this;
  }
}
