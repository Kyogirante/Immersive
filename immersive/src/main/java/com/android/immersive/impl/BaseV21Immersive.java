package com.android.immersive.impl;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.immersive.interfaces.Immersive;


/**
 * @author KyoWang
 * @since 2018/11/14
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
abstract class BaseV21Immersive extends AbstractImmersive {

  BaseV21Immersive(Activity activity) {
    super(activity);
  }

  @Override
  public Immersive setStatusBarColor(int colorRes) {
    Window window = activity.getWindow();
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.setStatusBarColor(activity.getResources().getColor(colorRes));
    return this;
  }

  @Override
  public Immersive setStatusBarTranslucent() {
    Window window = activity.getWindow();

    View decorView = window.getDecorView();
    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    // 5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
    window.setStatusBarColor(Color.TRANSPARENT);
    return this;
  }

  @Override
  public Immersive setStatusBarFontStyle(boolean isDark) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      View decorView = activity.getWindow().getDecorView();
      if (decorView != null) {
        int vis = decorView.getSystemUiVisibility();
        if (isDark) {
          vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
          vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        if (decorView.getSystemUiVisibility() != vis) {
          decorView.setSystemUiVisibility(vis);
        }
      }
    }
    return this;
  }
}
